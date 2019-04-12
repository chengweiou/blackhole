package chengweiou.universe.blackhole.util;

public class StringUtil {
    public static String hidMid(String v) {
        if (v.length() < 25) return v;
        return v.substring(0, 10) + "******" + v.substring(v.length() -10);
    }

    public static String hidMidSecret(String v) {
        return v.substring(0, 3) + "******" + v.substring(v.length() -3);
    }
}

