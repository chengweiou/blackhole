package chengweiou.universe.blackhole.dao.test;

import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TestServiceEntity extends ServiceEntity {
    private String name;
    private String dtoKey1;
    private String dtoKey2;
    private String dtoKey3;
    private String dtoKey4;
    private String dtoKey5;
    private String dtoKey6;
    private String dtoKey7;
    private String dtoKey8;
    private String prop1;
    private String prop2;


    public void fillNotRequire() {
    }


    public static final TestServiceEntity NULL = new Null();
    public static class Null extends TestServiceEntity implements NullObj {
    }

    public Dto toDto() {
        Dto result = new Dto();
        result.setId(id);
        result.setName(name);
        result.setDtoKey1(dtoKey1);
        result.setDtoKey2(dtoKey2);
        result.setDtoKey3(dtoKey3);
        result.setDtoKey4(dtoKey4);
        result.setDtoKey5(dtoKey5);
        result.setDtoKey6(dtoKey6);
        result.setDtoKey7(dtoKey7);
        result.setDtoKey8(dtoKey8);
        result.setProp1(prop1);
        result.setProp2(prop2);
        return result;
    }
    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Dto extends DtoEntity {
        private String name;
        @DtoKey
        private String dtoKey1;
        @DtoKey(canEmpty = true)
        private String dtoKey2;
        @DtoKey(single = true)
        private String dtoKey3;
        @DtoKey(single = true, canEmpty = true)
        private String dtoKey4;
        @DtoKey(group = "1")
        private String dtoKey5;
        @DtoKey(group = "1", canEmpty = true)
        private String dtoKey6;
        @DtoKey(group = "2")
        private String dtoKey7;
        @DtoKey(group = "2")
        private String dtoKey8;
        @DtoKey(group = "3")
        private String dtoKey9;
        @DtoKey(group = "3", canEmpty = true)
        private String dtoKey10;
        @DtoKey(group = "3", canEmpty = true)
        private String dtoKey11;
        private String prop1;
        private String prop2;

        public TestServiceEntity toBean() {
            TestServiceEntity result = new TestServiceEntity();
            result.setId(id);
            result.setName(name);
            result.setDtoKey1(dtoKey1);
            result.setDtoKey2(dtoKey2);
            result.setDtoKey3(dtoKey3);
            result.setDtoKey4(dtoKey4);
            result.setDtoKey5(dtoKey5);
            result.setDtoKey6(dtoKey6);
            result.setDtoKey7(dtoKey7);
            result.setDtoKey8(dtoKey8);
            result.setProp1(prop1);
            result.setProp2(prop2);
            return result;
        }
    }
}
