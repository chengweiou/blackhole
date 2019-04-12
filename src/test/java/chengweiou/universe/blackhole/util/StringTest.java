package chengweiou.universe.blackhole.util;


import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.entity.BuilderEntity;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Test
    public void hidMid() {
        String result = StringUtil.hidMid("1234567890");
        Assertions.assertEquals("1234567890", result);

        result = StringUtil.hidMid("123456789012345678901234567890");
        Assertions.assertEquals("1234567890******1234567890", result);
    }

    @Test
    public void hidMidSecret() {
        String result = StringUtil.hidMidSecret("1234567890");
        Assertions.assertEquals("123******890", result);

        result = StringUtil.hidMidSecret("123456789012345678901234567890");
        Assertions.assertEquals("123******890", result);
    }
}
