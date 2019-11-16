package chengweiou.universe.blackhole.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
