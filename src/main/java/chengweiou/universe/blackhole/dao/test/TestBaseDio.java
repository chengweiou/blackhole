package chengweiou.universe.blackhole.dao.test;

import chengweiou.universe.blackhole.dao.AbstractBaseDao;
import chengweiou.universe.blackhole.dao.BaseDio;
import chengweiou.universe.blackhole.dao.DioCache;
import chengweiou.universe.blackhole.dao.DioDefaultSort;
import chengweiou.universe.blackhole.dao.DioDefaultSortAz;
import chengweiou.universe.blackhole.dao.test.TestServiceEntity.Dto;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;

@DioCache(false)
public class TestBaseDio extends BaseDio<TestServiceEntity, TestServiceEntity.Dto> {
    private AbstractBaseDao dao;
    @DioDefaultSort("aaa")
    private String defaultSort;
    @DioDefaultSortAz(true)
    private boolean defaultSortAz;
    @Override
    protected AbstractBaseDao getDao() { return dao; }

    @Override
    protected String baseFind(AbstractSearchCondition searchCondition, Dto sample) {
        return " where 1==1 ";
    };

    // 给测试的时候可以 reset
    public void setDao(AbstractBaseDao dao) {
        this.dao = dao;
    }
}
