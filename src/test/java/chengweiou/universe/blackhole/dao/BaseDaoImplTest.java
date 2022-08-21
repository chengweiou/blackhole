package chengweiou.universe.blackhole.dao;


import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import chengweiou.universe.blackhole.dao.test.TestServiceEntity.Dto;
import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.test.BuilderEntity;



public class BaseDaoImplTest {
    private BaseDaoImpl<Dto> baseDaoImpl = new BaseDaoImpl<>();

    private static final Dto allPropEntity = Builder.set("name", "n").set("dtoKey1", "1").set("dtoKey2", "2").set("dtoKey3", "3").set("dtoKey4", "4").set("dtoKey5", "5").set("dtoKey6", "6").set("dtoKey7", "7").set("dtoKey8", "8").set("prop1", "p1").set("prop2", "p2").set("createAt", Instant.now()).set("updateAt", Instant.now()).to(new Dto());
    private static final Dto allPropEntityWithId = Builder.set("name", "n").set("dtoKey1", "1").set("dtoKey2", "2").set("dtoKey3", "3").set("dtoKey4", "4").set("dtoKey5", "5").set("dtoKey6", "6").set("dtoKey7", "7").set("dtoKey8", "8").set("prop1", "p1").set("prop2", "p2").set("id", "11").set("createAt", Instant.now()).set("updateAt", Instant.now()).to(new Dto());
    private static final Dto withEmptyGroupEntity = Builder.set("name", "n").set("dtoKey1", "1").set("dtoKey2", "2").set("dtoKey3", "3").set("dtoKey4", "4").set("dtoKey7", "7").set("dtoKey8", "8").set("prop1", "p1").set("prop2", "p2").set("createAt", Instant.now()).set("updateAt", Instant.now()).to(new Dto());
    private static final Dto oneSingleEntity = Builder.set("name", "n").set("dtoKey3", "3").set("prop1", "p1").set("prop2", "p2").set("createAt", Instant.now()).set("updateAt", Instant.now()).to(new Dto());
    private static final Dto twoSingleEntity = Builder.set("name", "n").set("dtoKey3", "3").set("dtoKey4", "4").set("prop1", "p1").set("prop2", "p2").set("createAt", Instant.now()).set("updateAt", Instant.now()).to(new Dto());
    private static final Dto oneBasicEntity = Builder.set("name", "n").set("dtoKey1", "1").set("dtoKey2", "2").set("prop1", "p1").set("prop2", "p2").set("createAt", Instant.now()).set("updateAt", Instant.now()).to(new Dto());
    private static final Dto noSuccessKeyEntity = Builder.set("name", "n").set("dtoKey5", "5").set("createAt", Instant.now()).set("updateAt", Instant.now()).set("id", "1").to(new Dto());
    private static final Dto noKeyEntity = Builder.set("name", "n").set("createAt", Instant.now()).set("updateAt", Instant.now()).set("id", "1").to(new Dto());

    private static Stream<Arguments> saveParam() {
        return Stream.of(
            Arguments.of(allPropEntity, "insert into testServiceEntity(name, dtoKey1, dtoKey2, dtoKey3, dtoKey4, dtoKey5, dtoKey6, dtoKey7, dtoKey8, prop1, prop2, createAt, updateAt) values(#{name}, #{dtoKey1}, #{dtoKey2}, #{dtoKey3}, #{dtoKey4}, #{dtoKey5}, #{dtoKey6}, #{dtoKey7}, #{dtoKey8}, #{prop1}, #{prop2}, #{createAt}, #{updateAt})"),
            Arguments.of(allPropEntityWithId, "insert into testServiceEntity(name, dtoKey1, dtoKey2, dtoKey3, dtoKey4, dtoKey5, dtoKey6, dtoKey7, dtoKey8, prop1, prop2, id, createAt, updateAt) values(#{name}, #{dtoKey1}, #{dtoKey2}, #{dtoKey3}, #{dtoKey4}, #{dtoKey5}, #{dtoKey6}, #{dtoKey7}, #{dtoKey8}, #{prop1}, #{prop2}, #{id}, #{createAt}, #{updateAt})")
        );
    }
    @ParameterizedTest
    @MethodSource("saveParam")
    public void save(final Dto e, final String expectSql) {
        String sql = baseDaoImpl.save(e);
        Assertions.assertEquals(expectSql, sql);
    }

    @Test
    public void findByIdList() {
        Map<String, Object> map = new HashMap<>();
        map.put("sample", new BuilderEntity());
        map.put("idList", Arrays.asList("1","2","3","4","5"));
        String sql = baseDaoImpl.findByIdList(map);
        Assertions.assertEquals("select * from builderEntity where id in ('1','2','3','4','5')", sql);
    }

    private static Stream<Arguments> deleteByKeyParam() {
        return Stream.of(
            Arguments.of(allPropEntity, "delete from testServiceEntity where dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}"),
            Arguments.of(allPropEntityWithId, "delete from testServiceEntity where dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}"),
            Arguments.of(withEmptyGroupEntity, "delete from testServiceEntity where dtoKey7=#{dtoKey7} and dtoKey8=#{dtoKey8}"),
            Arguments.of(oneSingleEntity, "delete from testServiceEntity where dtoKey3=#{dtoKey3}"),
            Arguments.of(twoSingleEntity, "delete from testServiceEntity where dtoKey4=#{dtoKey4}"),
            Arguments.of(oneBasicEntity, "delete from testServiceEntity where dtoKey1=#{dtoKey1} and dtoKey2=#{dtoKey2}")
        );
    }
    @ParameterizedTest
    @MethodSource("deleteByKeyParam")
    void deleteByKey(final Dto e, final String expectSql) {
        String sql = baseDaoImpl.deleteByKey(e);
        Assertions.assertEquals(expectSql, sql);
    }

    private static Stream<Arguments> deleteByKeyFailParam() {
        return Stream.of(
            Arguments.of(noKeyEntity),
            Arguments.of(noSuccessKeyEntity)
        );
    }
    @ParameterizedTest
    @MethodSource("deleteByKeyFailParam")
    void deleteByKeyFail(final Dto e) {
        Assertions.assertThrows(NullPointerException.class, () -> baseDaoImpl.deleteByKey(e));
    }

    private static Stream<Arguments> updateParam() {
        return Stream.of(
            Arguments.of(allPropEntity, "update testServiceEntity set name=#{name}, dtoKey1=#{dtoKey1}, dtoKey2=#{dtoKey2}, dtoKey3=#{dtoKey3}, dtoKey4=#{dtoKey4}, dtoKey5=#{dtoKey5}, dtoKey6=#{dtoKey6}, dtoKey7=#{dtoKey7}, dtoKey8=#{dtoKey8}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where id=#{id}"),
            Arguments.of(allPropEntityWithId, "update testServiceEntity set name=#{name}, dtoKey1=#{dtoKey1}, dtoKey2=#{dtoKey2}, dtoKey3=#{dtoKey3}, dtoKey4=#{dtoKey4}, dtoKey5=#{dtoKey5}, dtoKey6=#{dtoKey6}, dtoKey7=#{dtoKey7}, dtoKey8=#{dtoKey8}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where id=#{id}"),
            Arguments.of(withEmptyGroupEntity, "update testServiceEntity set name=#{name}, dtoKey1=#{dtoKey1}, dtoKey2=#{dtoKey2}, dtoKey3=#{dtoKey3}, dtoKey4=#{dtoKey4}, dtoKey7=#{dtoKey7}, dtoKey8=#{dtoKey8}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where id=#{id}"),
            Arguments.of(oneSingleEntity, "update testServiceEntity set name=#{name}, dtoKey3=#{dtoKey3}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where id=#{id}"),
            Arguments.of(twoSingleEntity, "update testServiceEntity set name=#{name}, dtoKey3=#{dtoKey3}, dtoKey4=#{dtoKey4}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where id=#{id}"),
            Arguments.of(oneBasicEntity, "update testServiceEntity set name=#{name}, dtoKey1=#{dtoKey1}, dtoKey2=#{dtoKey2}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where id=#{id}"),
            Arguments.of(noSuccessKeyEntity, "update testServiceEntity set name=#{name}, dtoKey5=#{dtoKey5}, updateAt=#{updateAt} where id=#{id}"),
            Arguments.of(noKeyEntity, "update testServiceEntity set name=#{name}, updateAt=#{updateAt} where id=#{id}")
        );
    }
    @ParameterizedTest
    @MethodSource("updateParam")
    void update(final Dto e, final String expectSql) {
        String sql = baseDaoImpl.update(e);
        Assertions.assertEquals(expectSql, sql);
    }

    private static Stream<Arguments> updateByKeyParam() {
        return Stream.of(
            Arguments.of(allPropEntity, "update testServiceEntity set name=#{name}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}"),
            Arguments.of(allPropEntityWithId, "update testServiceEntity set name=#{name}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}"),
            Arguments.of(withEmptyGroupEntity, "update testServiceEntity set name=#{name}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where dtoKey7=#{dtoKey7} and dtoKey8=#{dtoKey8}"),
            Arguments.of(oneSingleEntity, "update testServiceEntity set name=#{name}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where dtoKey3=#{dtoKey3}"),
            Arguments.of(twoSingleEntity, "update testServiceEntity set name=#{name}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where dtoKey4=#{dtoKey4}"),
            Arguments.of(oneBasicEntity, "update testServiceEntity set name=#{name}, prop1=#{prop1}, prop2=#{prop2}, updateAt=#{updateAt} where dtoKey1=#{dtoKey1} and dtoKey2=#{dtoKey2}")
        );
    }
    @ParameterizedTest
    @MethodSource("updateByKeyParam")
    void updateByKey(final Dto e, final String expectSql) {
        String sql = baseDaoImpl.updateByKey(e);
        Assertions.assertEquals(expectSql, sql);
    }

    private static Stream<Arguments> updateByKeyFailParam() {
        return Stream.of(
            Arguments.of(noKeyEntity),
            Arguments.of(noSuccessKeyEntity)
        );
    }
    @ParameterizedTest
    @MethodSource("updateByKeyFailParam")
    void updateByKeyFail(final Dto e) {
        Assertions.assertThrows(NullPointerException.class, () -> baseDaoImpl.updateByKey(e));
    }

    private static Stream<Arguments> findByKeyParam() {
        return Stream.of(
            Arguments.of(allPropEntity, "select * from testServiceEntity where dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}"),
            Arguments.of(allPropEntityWithId, "select * from testServiceEntity where dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}"),
            Arguments.of(withEmptyGroupEntity, "select * from testServiceEntity where dtoKey7=#{dtoKey7} and dtoKey8=#{dtoKey8}"),
            Arguments.of(oneSingleEntity, "select * from testServiceEntity where dtoKey3=#{dtoKey3}"),
            Arguments.of(twoSingleEntity, "select * from testServiceEntity where dtoKey4=#{dtoKey4}"),
            Arguments.of(oneBasicEntity, "select * from testServiceEntity where dtoKey1=#{dtoKey1} and dtoKey2=#{dtoKey2}")
        );
    }
    @ParameterizedTest
    @MethodSource("findByKeyParam")
    void findByKey(final Dto e, final String expectSql) {
        String sql = baseDaoImpl.findByKey(e);
        Assertions.assertEquals(expectSql, sql);
    }


    private static Stream<Arguments> checkExistsParam() {
        return Stream.of(
            Arguments.of(allPropEntity, "select count(*) from testServiceEntity where ((dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}) or (dtoKey7=#{dtoKey7} and dtoKey8=#{dtoKey8}) or (dtoKey4=#{dtoKey4}) or (dtoKey3=#{dtoKey3}) or (dtoKey1=#{dtoKey1} and dtoKey2=#{dtoKey2}))"),
            Arguments.of(allPropEntityWithId, "select count(*) from testServiceEntity where ((dtoKey5=#{dtoKey5} and dtoKey6=#{dtoKey6}) or (dtoKey7=#{dtoKey7} and dtoKey8=#{dtoKey8}) or (dtoKey4=#{dtoKey4}) or (dtoKey3=#{dtoKey3}) or (dtoKey1=#{dtoKey1} and dtoKey2=#{dtoKey2})) and id!=#{id}"),
            Arguments.of(withEmptyGroupEntity, "select count(*) from testServiceEntity where ((dtoKey7=#{dtoKey7} and dtoKey8=#{dtoKey8}) or (dtoKey4=#{dtoKey4}) or (dtoKey3=#{dtoKey3}) or (dtoKey1=#{dtoKey1} and dtoKey2=#{dtoKey2}))"),
            Arguments.of(oneSingleEntity, "select count(*) from testServiceEntity where ((dtoKey3=#{dtoKey3}))"),
            Arguments.of(twoSingleEntity, "select count(*) from testServiceEntity where ((dtoKey4=#{dtoKey4}) or (dtoKey3=#{dtoKey3}))"),
            Arguments.of(oneBasicEntity, "select count(*) from testServiceEntity where ((dtoKey1=#{dtoKey1} and dtoKey2=#{dtoKey2}))"),
            Arguments.of(noSuccessKeyEntity, "select 0"),
            Arguments.of(noKeyEntity, "select 0")
        );
    }
    @ParameterizedTest
    @MethodSource("checkExistsParam")
    void countByKeyCheckExist(final Dto e, final String expectSql) {
        String sql = baseDaoImpl.countByKeyCheckExist(e);
        Assertions.assertEquals(expectSql, sql);
    }
}
