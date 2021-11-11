package chengweiou.universe.blackhole.model;


import chengweiou.universe.blackhole.model.test.BuilderEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


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
    public void dateToString() {
        BuilderEntity e = Builder.set("stringType", LocalDate.of(2019, 01, 01)).to(BuilderEntity.class);
        Assertions.assertEquals("2019-01-01", e.getStringType());
    }
    @Test
    public void DatetimeToString() {
        BuilderEntity e = Builder.set("stringType", LocalDateTime.of(2019, 01, 01, 0, 0, 0)).to(BuilderEntity.class);
        Assertions.assertEquals("2019-01-01T00:00:00", e.getStringType());
    }
    @Test
    public void InstantToString() {
        BuilderEntity e = Builder.set("stringType", LocalDateTime.of(2019, 01, 01, 0, 0, 0).toInstant(ZoneOffset.UTC)).to(BuilderEntity.class);
        Assertions.assertEquals("2019-01-01T00:00:00Z", e.getStringType());
    }

    @Test
    public void stringToDate() {
        BuilderEntity e = Builder.set("localDateType", "2017-01-01").to(BuilderEntity.class);
        Assertions.assertEquals("2017-01-01", e.getLocalDateType().toString());
    }
    @Test
    public void stringToDateTime() {
        BuilderEntity e = Builder.set("localDateTimeType", "2017-01-01T22:22:22").to(BuilderEntity.class);
        Assertions.assertEquals("2017-01-01T22:22:22", e.getLocalDateTimeType().toString());
    }
    @Test
    public void stringToInstant() {
        BuilderEntity e = Builder.set("instantType", "2017-01-01T22:22:22").to(BuilderEntity.class);
        Assertions.assertEquals("2017-01-01T22:22:22Z", e.getInstantType().toString());
    }

    @Test
    public void setIntSmallType() {
        BuilderEntity e = Builder.set("intSmallType", 8).to(BuilderEntity.class);
        Assertions.assertEquals(8, e.getIntSmallType());
    }

    @Test
    public void setWrongName() {
        Assertions.assertThrows(NullPointerException.class, () -> Builder.set("wrongname", 8).to(BuilderEntity.class));
    }

    @Test
    public void setDoubleToInt() {
        BuilderEntity e = Builder.set("intSmallType", 8.732).set("integerType", 8.732).to(BuilderEntity.class);
        Assertions.assertEquals(9, e.getIntSmallType());
        Assertions.assertEquals(9, e.getIntegerType());
    }
}
