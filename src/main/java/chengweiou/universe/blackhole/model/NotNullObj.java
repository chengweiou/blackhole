package chengweiou.universe.blackhole.model;

public interface NotNullObj {
    default boolean isNull() {
        return false;
    }
}