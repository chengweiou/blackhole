package chengweiou.universe.blackhole.valid;


import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.test.BuilderEntity;
import chengweiou.universe.blackhole.param.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ValidStringTest {

    @Test
    public void notof() throws ParamException {
        Valid.check("k", "1").is().notOf("0");
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("k", "1").is().notOf("1");
        });
    }

}