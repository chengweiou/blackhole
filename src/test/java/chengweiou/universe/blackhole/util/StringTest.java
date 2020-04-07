package chengweiou.universe.blackhole.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringTest {

    @Test
    public void ellipsisMid() {
        String result = StringUtil.ellipsisMid("1234567890", 10);
        Assertions.assertEquals("1234567890", result);

        result = StringUtil.ellipsisMid("123456789012345678901234567890", 10);
        Assertions.assertEquals("12345...67890", result);
    }

    @Test
    public void ellipsisEnd() {
        String result = StringUtil.ellipsisEnd("123456789012345678901234567890", 10);
        Assertions.assertEquals("1234567890...", result);

        result = StringUtil.ellipsisEnd("1234", 10);
        Assertions.assertEquals("1234", result);
    }

    @Test
    public void securityMid() {
        String result = StringUtil.securityMid("1234567890", 20);
        Assertions.assertEquals("12******90", result);

        result = StringUtil.securityMid("123456789012345678901234567890", 20);
        Assertions.assertEquals("12345******67890", result);
    }

    @Test
    public void isNotEmptyWord() {
        boolean result = StringUtil.isNotEmptyWord(null);
        Assertions.assertEquals(false, result);
        result = StringUtil.isNotEmptyWord("undefined");
        Assertions.assertEquals(false, result);
        result = StringUtil.isNotEmptyWord("null");
        Assertions.assertEquals(false, result);
        result = StringUtil.isNotEmptyWord(",");
        Assertions.assertEquals(false, result);
        result = StringUtil.isNotEmptyWord(",null:");
        Assertions.assertEquals(false, result);
        result = StringUtil.isNotEmptyWord(",:...undefined.//nullnullundefined");
        Assertions.assertEquals(false, result);
        result = StringUtil.isNotEmptyWord("1234567;;8901234.5678901//234567890");
        Assertions.assertEquals(true, result);
    }
}
