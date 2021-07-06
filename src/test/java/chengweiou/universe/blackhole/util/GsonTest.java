package chengweiou.universe.blackhole.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.test.BuilderEntity;

public class GsonTest {

    @Test
    public void create() {
        BuilderEntity e = Builder.set("localDateType", "2017-01-01").set("localDateTimeType", "2017-01-01T22:22:22").to(BuilderEntity.class);
        String json = GsonUtil.create().toJson(e);
        System.out.println(json);
        BuilderEntity eBack = GsonUtil.create().fromJson(json, BuilderEntity.class);
        Assertions.assertEquals("2017-01-01", eBack.getLocalDateType().toString());
        Assertions.assertEquals("2017-01-01T22:22:22", eBack.getLocalDateTimeType().toString());

    }

}
