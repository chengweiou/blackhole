package chengweiou.universe.blackhole.model;

public interface NullObj extends NotNullObj {
    default boolean isNotNull() {
        return false;
    }
}

