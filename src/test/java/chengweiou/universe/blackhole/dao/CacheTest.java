package chengweiou.universe.blackhole.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.test.BuilderEntity;


public class CacheTest {

    @Test
    public void saveAndGet() {
        BuilderEntity e = Builder
                .set("stringType", "string123")
                .set("integerType", 92)
                .set("longType", 234)
                .set("doubleType", 3.42)
                .set("booleanType", true)
                .set("objectType", Builder.set("stringType", "inner string").to(BuilderEntity.class))
                .to(BuilderEntity.class);
        BaseDbCache.save("aaa", e);
        BuilderEntity incache = (BuilderEntity) BaseDbCache.get("aaa");
        Assertions.assertEquals("string123", incache.getStringType());
        Assertions.assertEquals(Integer.valueOf(92), incache.getIntegerType());
        BaseDbCache.delete("aaa");
        incache = (BuilderEntity) BaseDbCache.get("aaa");
        Assertions.assertEquals(null, incache);
    }

    @Test
    public void saveAndGetAll() {
        List<String> idList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            BuilderEntity e = Builder.set("stringType", "string"+i).to(BuilderEntity.class);
            BaseDbCache.save(i+"", e);
            idList.add(i+"");
        }
        Map<String, Object> incacheMap = BaseDbCache.get(idList);
        Assertions.assertEquals(10, incacheMap.size());
        Assertions.assertEquals("string1", ((BuilderEntity)incacheMap.get("1")).getStringType());
        BaseDbCache.delete(Arrays.asList("1","2","3","4","5"));
        incacheMap = BaseDbCache.get(idList);
        Assertions.assertEquals(5, incacheMap.size());
        Assertions.assertEquals(null, ((BuilderEntity)incacheMap.get("1")));
        Assertions.assertEquals("string6", ((BuilderEntity)incacheMap.get("6")).getStringType());
        BaseDbCache.delete(Arrays.asList("6","7","8","9","0"));
        incacheMap = BaseDbCache.get(idList);
        Assertions.assertEquals(0, incacheMap.size());
    }

    @Test
    public void getNull() {
        BuilderEntity incache = (BuilderEntity) BaseDbCache.get("bbb");
        Assertions.assertEquals(null, incache);
        BaseDbCache.delete("null");
    }

}
