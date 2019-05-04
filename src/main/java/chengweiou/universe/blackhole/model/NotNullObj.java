package chengweiou.universe.blackhole.model;

public interface NotNullObj {
    default boolean isNotNull() {
        return true;
    }
}