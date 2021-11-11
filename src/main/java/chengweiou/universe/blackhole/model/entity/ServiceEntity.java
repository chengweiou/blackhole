package chengweiou.universe.blackhole.model.entity;

import java.io.Serializable;
import java.time.Instant;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import lombok.Data;

@Data
public abstract class ServiceEntity implements NotNullObj, Serializable {
    protected Long id;
    protected Instant createAt;
    protected Instant updateAt;

    public void fillNotRequire() {
    }

    public void createAt() {
        createAt = Instant.now();
    }
    public void updateAt() {
        updateAt = Instant.now();
    }

    public static final ServiceEntity NULL = new Null();
    public static class Null extends ServiceEntity implements NullObj {
    }

    public DtoEntity toDto() { return null; }
}
