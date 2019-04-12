package chengweiou.universe.blackhole.param;

import java.util.Arrays;

public class Valid {
    public static ValidString.Nullable check(String k, String v) {
        return new ValidString.Nullable(k, v);
    }
    public static ValidStringList.Nullable check(String k, String... v) { return new ValidStringList.Nullable(k, Arrays.asList(v));}
    public static ValidInteger.Nullable check(String k, Integer v) {
        return new ValidInteger.Nullable(k, v);
    }
    public static ValidLong.Nullable check(String k, Long v) { return new ValidLong.Nullable(k, v); }
    public static ValidDouble.Nullable check(String k, Double v) {
        return new ValidDouble.Nullable(k, v);
    }
    public static ValidObj.Nullable check(String k, Object v) {
        return new ValidObj.Nullable(k, v);
    }
    public static ValidObjList.Nullable check(String k, Object... v) {return new ValidObjList.Nullable(k, Arrays.asList(v)); }
}
