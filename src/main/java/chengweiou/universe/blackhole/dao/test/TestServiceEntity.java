package chengweiou.universe.blackhole.dao.test;

import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestServiceEntity extends ServiceEntity {
    private String name;

    public void fillNotRequire() {
    }


    public static final TestServiceEntity NULL = new Null();
    public static class Null extends TestServiceEntity implements NullObj {
    }

    public Dto toDto() {
        Dto result = new Dto();
        result.setId(id);
        result.setName(name);
        return result;
    }
    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Dto extends DtoEntity {
        private Long id;
        private String name;

        public TestServiceEntity toBean() {
            TestServiceEntity result = new TestServiceEntity();
            result.setId(id);
            result.setName(name);
            return result;
        }
    }
}
