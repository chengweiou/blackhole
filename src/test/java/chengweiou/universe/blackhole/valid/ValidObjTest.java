package chengweiou.universe.blackhole.valid;


import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.test.TestEnum;
import chengweiou.universe.blackhole.param.Valid;


public class ValidObjTest {

    @Test
    public void ofEnum() throws ParamException {
        Valid.check("enum", TestEnum.ENUM1).is().of(Stream.of(TestEnum.values()).map(TestEnum::toString).toList());
        Assertions.assertThrows(ParamException.class, () -> {
            Valid.check("enum", TestEnum.ENUM1).is().of(Arrays.asList("ENUM-NONE", "ENUM-WRONG"));
        });
    }

}
