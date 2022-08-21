package chengweiou.universe.blackhole.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.test.BuilderEntity;

public class GsonTest {

    @Test
    public void date() {
        BuilderEntity e = Builder
            .set("localDateType", "2017-01-01")
            .set("localDateTimeType", "2017-01-01T22:22:22")
            .set("instantType", "2017-01-01T22:22:22")
            .to(BuilderEntity.class);
        String json = GsonUtil.create().toJson(e);
        System.out.println(json);
        BuilderEntity eBack = GsonUtil.create().fromJson(json, BuilderEntity.class);
        Assertions.assertEquals("2017-01-01", eBack.getLocalDateType().toString());
        Assertions.assertEquals("2017-01-01T22:22:22", eBack.getLocalDateTimeType().toString());
        Assertions.assertEquals("2017-01-01T22:22:22Z", eBack.getInstantType().toString());
    }

    @Test
    public void dateWidthTandZ() {
        BuilderEntity e = Builder
            .set("localDateType", "2021-11-05T00:41:36.717Z")
            .set("localDateTimeType", "2021-11-05T00:41:36.717Z")
            .set("instantType", "2017-01-01T22:22:22Z")
            .to(BuilderEntity.class);
        System.out.println(e.toString());
        String json = GsonUtil.create().toJson(e);
        System.out.println(json);
        BuilderEntity eBack = GsonUtil.create().fromJson(json, BuilderEntity.class);
        Assertions.assertEquals("2021-11-05", eBack.getLocalDateType().toString());
        Assertions.assertEquals("2021-11-05T00:41:36", eBack.getLocalDateTimeType().toString());
        Assertions.assertEquals("2017-01-01T22:22:22Z", eBack.getInstantType().toString());
    }
}
