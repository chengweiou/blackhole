package chengweiou.universe.blackhole.valid;


import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.test.BuilderEntity;
import chengweiou.universe.blackhole.model.test.TestEnum;
import chengweiou.universe.blackhole.param.Valid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
            Valid.check("date", "2017-01-02T23:22:00").is().date();
        });
    }

    @Test
    public void time() throws ParamException {
        Valid.check("time", "2017-01-02T23:22:00").is().time();
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("time", "2017-01-02").is().time();
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

}