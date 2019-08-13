package chengweiou.universe.blackhole.model;

public interface NotNullObj {
    default boolean notNull() {
        return true;
    }
}