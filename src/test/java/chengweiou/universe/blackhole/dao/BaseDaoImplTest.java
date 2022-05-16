package chengweiou.universe.blackhole.dao;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.model.test.BuilderEntity;


public class BaseDaoImplTest {

    @Test
    public void findByIdList() {
        Map<String, Object> map = new HashMap<>();
        map.put("sample", new BuilderEntity());
        map.put("idList", Arrays.asList("1","2","3","4","5"));
        BaseDaoImpl<BuilderEntity> baseDaoImpl = new BaseDaoImpl<>();
        String sql = baseDaoImpl.findByIdList(map);
        Assertions.assertEquals("select * from builderEntity where id in ('1','2','3','4','5')", sql);
    }

}
