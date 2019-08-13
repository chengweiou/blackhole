package chengweiou.universe.blackhole.model;

public interface NullObj extends NotNullObj {
    default boolean notNull() {
        return false;
    }
}

