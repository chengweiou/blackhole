package chengweiou.universe.blackhole.util;

public class StringUtil {
    /**
     * length <= max || max/2 + ... + max/2
     * @param v
     * @param max
     * @return
     */
    public static String ellipsisMid(String v, int max) {
        if (v.length() <= max) return v;
        int keep = max/2;
        return v.substring(0, keep) + "..." + v.substring(v.length() - keep);
    }

    /**
     * length <= max || max + ...
     * @param v
     * @param max
     * @return
     */
    public static String ellipsisEnd(String v, int max) {
        if (v.length() <= max) return v;
        return v.substring(0, max) + "...";
    }

    /**
     * length < (max || length) /4 + "*******" + (max || length) /4
     * @param v
     * @param max
     * @return
     */
    public static String securityMid(String v, int max) {
        int keep = Math.min(max, v.length()) / 4;
        return v.substring(0, keep) + "******" + v.substring(v.length() - keep);

    }
}
