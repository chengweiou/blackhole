package chengweiou.universe.blackhole.dao;


import java.util.List;

import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.entity.DtoEntity;

/**
 * 用于不需要导入mybatis的包
 */
public interface AbstractBaseDao<Dto extends DtoEntity> {
    long save(Dto e);

    long delete(Dto e);
    long deleteByKey(Dto e);
    long deleteBySample(Dto e, Dto sample);
    long deleteByIdList(Dto e, List<String> idList);

    long update(Dto e);
    long updateByKey(Dto e);
    long updateBySample(Dto e, Dto sample);
    long updateByIdList(Dto e, List<String> idList);

    Dto findById(Dto e);
    long countByKey(Dto e);
    Dto findByKey(Dto e);

    long count(AbstractSearchCondition searchCondition, Dto sample, String where);

    List<Dto> find(AbstractSearchCondition searchCondition, Dto sample, String where);

    Long findIdByKey(Dto sample);
    List<String> findId(AbstractSearchCondition searchCondition, Dto sample, String where);

    List<Dto> findByIdList(List<String> idList, Dto sample);
}
