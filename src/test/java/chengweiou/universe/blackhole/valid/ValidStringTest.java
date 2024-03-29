package chengweiou.universe.blackhole.valid;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.param.Valid;


public class ValidStringTest {

    @Test
    public void notof() throws ParamException {
        Valid.check("k", "1").is().notOf("0");
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("k", "1").is().notOf("1");
        });
    }

    @Test
    public void date() throws ParamException {
        Valid.check("date", "2017-01-02").is().date();
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("date", "2017-01-02T23:22:00sdf").is().date();
        });
    }

    @Test
    public void time() throws ParamException {
        Valid.check("time", "2017-01-02T23:22:00").is().time();
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("time", "2017-01-02ddd").is().time();
        });
    }

    @Test
    public void dateOrTime() throws ParamException {
        Valid.check("dateOrTime", "2017-01-02T23:22:00").is().dateOrTime();
        Valid.check("dateOrTime", "2017-01-02").is().dateOrTime();
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("dateOrTime", "asdf").is().dateOrTime();
        });
    }
    @Test
    public void objectId() throws ParamException {
        Valid.check("objectId", "5d44e0ee661d746770a17807").is().objectId();
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("objectId", "0044e0ee6610746770a17807").is().objectId();
            Valid.check("objectId", "0").is().objectId();
            Valid.check("objectId", "5d44e0ee661d746770a17807asdfdsaf").is().objectId();
        });
    }

    @Test
    public void nullable() throws ParamException {
        String nullable = null;
        Valid.check("nullable", nullable).nullable().lengthIs(10);
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("not nullable", nullable).is().lengthIs(10);
        });
    }

    @Test
    public void emptyWord() throws ParamException {
        String nullable = "null";
        Valid.check("nullable", nullable).is().allowEmptyWord().notEmpty();
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("not nullable", nullable).is().notEmpty();
        });
    }

    @Test
    public void include() throws ParamException {
        String include = "abcdef";
        Valid.check("include", include).is().lengthIn(3, -1).include("a", "def", "b");
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("not include", include).is().include("a", "notinclude", "b");
        });
    }
}
