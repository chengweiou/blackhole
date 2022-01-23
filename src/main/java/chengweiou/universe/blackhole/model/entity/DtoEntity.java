package chengweiou.universe.blackhole.model.entity;

import java.io.Serializable;
import java.time.Instant;

import chengweiou.universe.blackhole.model.NotNullObj;
import lombok.Data;

@Data
public class DtoEntity implements NotNullObj, Serializable {
    protected Long id;
    protected Instant createAt;
    protected Instant updateAt;

    public ServiceEntity toBean() {
        return null;
    }
}
