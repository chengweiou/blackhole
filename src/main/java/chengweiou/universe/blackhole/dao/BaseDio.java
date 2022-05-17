package chengweiou.universe.blackhole.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import chengweiou.universe.blackhole.util.LogUtil;

public abstract class BaseDio<T extends ServiceEntity, Dto extends DtoEntity> {
    protected String getDefaultSort() { return "updateAt"; }
    protected boolean getDefaultSortAz() { return false; }
    protected abstract Class getTClass();
    protected abstract <Dao extends AbstractBaseDao> Dao getDao();

    public void save(T e) throws FailException {
        if (hasKey(e)) {
            long count = getDao().countByKey(e.toDto());
            if (count == 1) throw new FailException("same data exists");
        }
        e.fillNotRequire();
        e.createAt();
        e.updateAt();
        DtoEntity dto = e.toDto();
        long count = getDao().save(dto);
        if (count != 1) throw new FailException();
        e.setId(dto.getId());
        BaseDbCache.save(createCacheK(dto.getId()), dto);
    }

    public void delete(T e) throws FailException {
        long count = getDao().delete(e.toDto());
        if (count != 1) throw new FailException();
        BaseDbCache.delete(createCacheK(e.getId()));
    }
    public void deleteByKey(T e) throws FailException {
        Long id = getDao().findIdByKey(e.toDto());
        if (id == null) throw new FailException();
        long count = getDao().deleteByKey(e.toDto());
        BaseDbCache.delete(createCacheK(id));
    }
    public void deleteBySample(T e, T sample) throws FailException {
        List<String> idList = getDao().findIdBySample(sample.toDto());
        if (idList.isEmpty()) throw new FailException();
        BaseDbCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
        long count = getDao().deleteBySample(e.toDto(), sample.toDto());
    }
    public void deleteByIdList(List<String> idList) throws FailException {
        long count = getDao().deleteByIdList((Dto) getNull().toDto(), idList);
        if (count != idList.size()) LogUtil.i("delete multi total:" + idList.size() + " success: " + count + ". idList=" + idList);
        if (count == 0) throw new FailException();
        BaseDbCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
    }

    public long update(T e) {
        e.updateAt();
        long count = getDao().update(e.toDto());
        BaseDbCache.delete(createCacheK(e.getId()));
        return count;
    }
    public long updateByKey(T e) {
        e.updateAt();
        Long id = getDao().findIdByKey(e.toDto());
        long count = getDao().updateByKey(e.toDto());
        BaseDbCache.delete(createCacheK(id));
        return count;
    }
    public long updateBySample(T e, T sample) {
        e.updateAt();
        List<String> idList = getDao().findIdBySample(sample.toDto());
        if (idList.isEmpty()) return 0;
        BaseDbCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
        return getDao().updateBySample(e.toDto(), sample.toDto());
    }
    public long updateByIdList(T e, List<String> idList) {
        e.updateAt();
        long count = getDao().updateByIdList(e.toDto(), idList);
        BaseDbCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
        return count;
    }

    public T findById(T e) {
        Dto incache = (Dto) BaseDbCache.get(createCacheK(e.getId()));
        if (incache != null) return (T) incache.toBean();
        Dto result = (Dto) getDao().findById(e.toDto());
        if (result == null) return (T) getNull();
        BaseDbCache.save(createCacheK(result.getId()), result);
        return (T) result.toBean();
    }

    public long countByKey(T e) {
        if (!hasKey(e)) return 0;
        long count = getDao().countByKey(e.toDto());
        return count;
    }

    public T findByKey(T e) {
        if (!hasKey(e)) return (T) getNull();
        Dto result = (Dto) getDao().findByKey(e.toDto());
        if (result == null) return (T) getNull();
        BaseDbCache.save(createCacheK(result.getId()), result);
        return (T) result.toBean();
    }

    private boolean hasKey(T e) {
        DtoEntity dto = e.toDto();
        List<String> fieldNameList = Arrays.asList(dto.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
            .filter(field -> field.isAnnotationPresent(DtoKey.class))
            .map(Field::getName)
            .toList();
        return !fieldNameList.isEmpty();
    }

    public long count(AbstractSearchCondition searchCondition, T sample) {
        if (searchCondition.getIdList() != null && searchCondition.getIdList().isEmpty()) return 0;
        Dto dtoSample = sample!=null ? (Dto) sample.toDto() : (Dto) getNull().toDto();
        String where = baseFind(searchCondition, dtoSample);
        return getDao().count(searchCondition, dtoSample, where);
    }

    public List<T> find(AbstractSearchCondition searchCondition, T sample) {
        if (searchCondition.getIdList() != null && searchCondition.getIdList().isEmpty()) return Collections.emptyList();
        searchCondition.setDefaultSort(getDefaultSort());
        searchCondition.setDefaultSortAz(getDefaultSortAz());
        Dto dtoSample = sample!=null ? (Dto) sample.toDto() : (Dto) getNull().toDto();
        String where = baseFind(searchCondition, dtoSample);
        List<Dto> dtoList = getDao().find(searchCondition, dtoSample, where);
        List<T> result = dtoList.stream().map(e -> (T) e.toBean()).toList();
        dtoList.stream().forEach(e -> {
            BaseDbCache.save(createCacheK(e.getId()), e);
        });
        return result;
    }

    public List<String> findId(AbstractSearchCondition searchCondition, T sample) {
        searchCondition.setDefaultSort(getDefaultSort());
        searchCondition.setDefaultSortAz(getDefaultSortAz());
        Dto dtoSample = sample!=null ? (Dto) sample.toDto() : (Dto) getNull().toDto();
        String where = baseFind(searchCondition, dtoSample);
        List<String> result = getDao().findId(searchCondition, dtoSample, where);
        return result;
    }

    protected abstract String baseFind(AbstractSearchCondition searchCondition, Dto sample);

    private T getNull() {
        try {
            return (T) getTClass().getDeclaredField("NULL").get(getTClass());
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            LogUtil.e("try to return T.NULL: " + getTClass());
            return null;
        }
    }

    private String createCacheK(Long id) {
        return createCacheK(id+"");
    }
    private String createCacheK(String id) {
        return getTClass().getSimpleName() + "Id." + id;
    }

    /**
     * 与 find 传递idList不同的是，没有排序，最大化利用缓存，适用于程序内多层级的调用查询
     * @return
     */
    public Map<String, Dto> findMapByIdList(List<String> idList) {
        Map<String, Object> incacheMap = BaseDbCache.get(idList.stream().map(id -> createCacheK(id)).toList());
        List<Dto> incacheList = incacheMap.values().stream().map(e -> (Dto)e).toList();
        Map<String, Dto> result = incacheList.stream().collect(Collectors.toMap(e -> e.getId()+"", e -> e));
        List<String> leftIdList = idList.stream().filter(id -> !incacheMap.containsKey(createCacheK(id))).toList();
        if (!leftIdList.isEmpty()) {
            Dto dtoSample = (Dto) getNull().toDto();
            List<Dto> leftDtoList = getDao().findByIdList(leftIdList, dtoSample);
            leftDtoList.stream().forEach(e -> {
                BaseDbCache.save(createCacheK(e.getId()), e);
                result.put(e.getId().toString(), e);
            });
        }
        return result;
    }
}
