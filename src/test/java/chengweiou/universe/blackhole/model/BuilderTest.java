package chengweiou.universe.blackhole.model;


import chengweiou.universe.blackhole.model.entity.BuilderEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BuilderTest {

    @Test
    public void newInstance() {
        BuilderEntity e = Builder
                .set("stringType", "string123")
                .set("integerType", 92)
                .set("longType", 234)
                .set("doubleType", 3.42)
                .set("booleanType", true)
                .set("objectType", Builder.set("stringType", "inner string").to(new BuilderEntity()))
                .to(new BuilderEntity());
        Assertions.assertEquals("string123", e.getStringType());
        Assertions.assertEquals(Integer.valueOf(92), e.getIntegerType());
        Assertions.assertEquals(Long.valueOf(234), e.getLongType());
        Assertions.assertEquals(Double.valueOf(3.42), e.getDoubleType());
        Assertions.assertEquals(true, e.getBooleanType());
        Assertions.assertEquals("inner string", e.getObjectType().getStringType());
    }

    @Test
    public void classType() {
        BuilderEntity e = Builder
                .set("stringType", "string123")
                .set("integerType", 92)
                .set("longType", 234)
                .set("doubleType", 3.42)
                .set("booleanType", true)
                .set("objectType", Builder.set("stringType", "inner string").to(BuilderEntity.class))
                .to(BuilderEntity.class);
        Assertions.assertEquals("string123", e.getStringType());
        Assertions.assertEquals(Integer.valueOf(92), e.getIntegerType());
        Assertions.assertEquals(Long.valueOf(234), e.getLongType());
        Assertions.assertEquals(Double.valueOf(3.42), e.getDoubleType());
        Assertions.assertEquals(true, e.getBooleanType());
        Assertions.assertEquals("inner string", e.getObjectType().getStringType());
    }

    @Test
    public void setBooleanSmallType() {
        BuilderEntity e = Builder.set("booleanSmallType", true).to(BuilderEntity.class);
        Assertions.assertEquals(true, e.isBooleanSmallType());
    }

    @Test
    public void setIntSmallType() {
        BuilderEntity e = Builder.set("intSmallType", 8).to(BuilderEntity.class);
        Assertions.assertEquals(8, e.getIntSmallType());
    }
}