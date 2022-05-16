package chengweiou.universe.blackhole.dao.test;

import chengweiou.universe.blackhole.dao.AbstractBaseDao;
import chengweiou.universe.blackhole.dao.BaseDio;
import chengweiou.universe.blackhole.dao.test.TestServiceEntity.Dto;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;

public class TestBaseDio extends BaseDio<TestServiceEntity, TestServiceEntity.Dto> {
    private AbstractBaseDao dao;
    @Override
    protected AbstractBaseDao getDao() { return dao; }
    @Override
    protected Class getTClass() { return TestServiceEntity.class; };
    @Override
    protected String getDefaultSort() { return "updateAt"; }
    @Override
    protected String baseFind(AbstractSearchCondition searchCondition, Dto sample) {
        return " where 1==1 ";
    };

    // 给测试的时候可以 reset
    public void setDao(AbstractBaseDao dao) {
        this.dao = dao;
    }
}
