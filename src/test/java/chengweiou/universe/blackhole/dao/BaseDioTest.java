package chengweiou.universe.blackhole.dao;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.dao.test.TestDao;
import chengweiou.universe.blackhole.dao.test.TestBaseDio;
import chengweiou.universe.blackhole.dao.test.TestServiceEntity;
import chengweiou.universe.blackhole.dao.test.TestServiceEntity.Dto;
import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.Builder;


public class BaseDioTest {
    private TestBaseDio dio = new TestBaseDio();

    @Test
    public void findByIdList() throws FailException {
        List<String> idList = Arrays.asList("1","11");
        Map<String, Dto> map = dio.findMapByIdList(idList);
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals("name11", map.get("11").getName());
    }

    @Test
    public void findByIdListWithCache() throws FailException {
        TestServiceEntity e1 = Builder.set("id", "1").set("name", "name1").to(new TestServiceEntity());
        dio.save(e1);

        List<String> idList = Arrays.asList("1","11");
        Map<String, Dto> map = dio.findMapByIdList(idList);
        Assertions.assertEquals(2, map.size());
        Assertions.assertEquals("name1", map.get("1").getName());
        Assertions.assertEquals("name11", map.get("11").getName());
        map = dio.findMapByIdList(idList);
        Assertions.assertEquals(2, map.size());

        dio.delete(e1);
        map = dio.findMapByIdList(idList);
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals("name11", map.get("11").getName());
    }

    @BeforeEach
    public void reset() {
        TestDao dao = new TestDao();
        dio.setDao(dao);
        BaseDbCache.clear();
    }
}
