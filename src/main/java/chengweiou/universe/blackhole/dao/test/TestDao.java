package chengweiou.universe.blackhole.dao.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import chengweiou.universe.blackhole.dao.AbstractBaseDao;
import chengweiou.universe.blackhole.dao.test.TestServiceEntity.Dto;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.Builder;

public class TestDao implements AbstractBaseDao<Dto> {
    List<Dto> fakeDbList = Arrays.asList(
        Builder.set("id", "11").set("name", "name11").to(new Dto()),
        Builder.set("id", "12").set("name", "name12").to(new Dto()),
        Builder.set("id", "13").set("name", "name13").to(new Dto())
    );

    public void reset() {
        fakeDbList = Arrays.asList(
            Builder.set("id", "11").set("name", "name11").to(new Dto()),
            Builder.set("id", "12").set("name", "name12").to(new Dto()),
            Builder.set("id", "13").set("name", "name13").to(new Dto())
        );
    }

    @Override
    public long save(Dto e) {
        fakeDbList = Stream.concat(fakeDbList.stream(), Stream.of(e)).toList();
        return 1;
    }

    @Override
    public long delete(Dto e) {
        long resultBefore = fakeDbList.size();
        fakeDbList = fakeDbList.stream().filter(indb -> indb.getId().longValue() != e.getId()).toList();
        long result = resultBefore - fakeDbList.size();
        return result;
    }

    @Override
    public long deleteByKey(Dto e) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long deleteBySample(Dto e, Dto sample) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long deleteByIdList(Dto e, List idList) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long update(Dto e) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long updateByKey(Dto e) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long updateBySample(Dto e, Dto sample) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long updateByIdList(Dto e, List idList) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Dto findById(Dto e) {
        Dto result = fakeDbList.stream().filter(fake -> fake.getId().longValue() == e.getId()).findAny().orElse(null);
        return result;
    }

    @Override
    public long countByKey(Dto e) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Dto findByKey(Dto e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long count(AbstractSearchCondition searchCondition, Dto sample, String where) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Dto> find(AbstractSearchCondition searchCondition, Dto sample, String where) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long findIdByKey(Dto sample) {
        return 11L;
    }


    @Override
    public List<String> findIdBySample(Dto sample) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<String> findId(AbstractSearchCondition searchCondition, Dto sample, String where) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Dto> findByIdList(List<String> idList, Dto sample) {
        List<Dto> result = fakeDbList.stream().filter(e -> idList.contains(e.getId().toString())).toList();
        return result;
    }


}
