package chengweiou.universe.blackhole.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import chengweiou.universe.blackhole.util.LogUtil;


/**
 * @DioCache(false) 类上方注解，用于禁止该 basedio 使用缓存
 * @DioDefaultSort("aaa") @DioDefaultSortAz(true) 属性上方注解，用于变更默认排序. 属性名随意 private boolean defaultSortAz;
 */
public abstract class BaseDio<T extends ServiceEntity, Dto extends DtoEntity> {
    protected abstract <Dao extends AbstractBaseDao> Dao getDao();
    private Class<T> tClass;
    private T tNull;
    private String defaultSort = "updateAt";
    private boolean defaultSortAz = false;
    private boolean dioCache = true;

    public BaseDio() {
        setDiocache();
        setDefaultSort();
        setTClass();
    }
    private void setDiocache() {
        DioCache dioCacheAnno = this.getClass().getAnnotation(DioCache.class);
        if (dioCacheAnno != null) dioCache = dioCacheAnno.value();
    }
    private void setDefaultSort() {
        Field[] fieldArray = this.getClass().getDeclaredFields();
        for (Field field: fieldArray) {
            if (field.isAnnotationPresent(DioDefaultSort.class)) {
                DioDefaultSort dioDefaultSortAnno = field.getAnnotation(DioDefaultSort.class);
                defaultSort = dioDefaultSortAnno.value();
            } else if (field.isAnnotationPresent(DioDefaultSortAz.class)) {
                DioDefaultSortAz dioDefaultSortAzAnno = field.getAnnotation(DioDefaultSortAz.class);
                defaultSortAz = dioDefaultSortAzAnno.value();
            }
        }
    }
    private void setTClass() {
        Type type = ((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        try {
            tClass = (Class<T>) Class.forName(type.getTypeName());
            tNull = (T) tClass.getDeclaredField("NULL").get(tClass);
        } catch (ClassNotFoundException ex) {
            LogUtil.e("can NOT general t class(" + type + ") in baseDio.", ex);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            LogUtil.e("try to return T.NULL: " + tClass);
        }
    }

    public void save(T e) throws FailException {
        if (hasKey(e)) {
            long count = getDao().countByKeyCheckExist(e.toDto());
            if (count == 1) throw new FailException("same data exists");
        }
        e.fillNotRequire();
        e.createAt();
        e.updateAt();
        DtoEntity dto = e.toDto();
        long count = getDao().save(dto);
        if (count != 1) throw new FailException();
        e.setId(dto.getId());
        if (dioCache) BaseDioCache.save(createCacheK(dto.getId()), dto);
    }

    public void delete(T e) throws FailException {
        long count = getDao().delete(e.toDto());
        if (count != 1) throw new FailException();
        if (dioCache) BaseDioCache.delete(createCacheK(e.getId()));
    }
    public void deleteByKey(T e) throws FailException {
        Long id = getDao().findIdByKey(e.toDto());
        if (id == null) throw new FailException();
        long count = getDao().deleteByKey(e.toDto());
        if (dioCache) BaseDioCache.delete(createCacheK(id));
    }
    public void deleteBySample(T e, T sample) throws FailException {
        List<String> idList = getDao().findIdBySample(sample.toDto());
        if (idList.isEmpty()) throw new FailException();
        long count = getDao().deleteBySample(e.toDto(), sample.toDto());
        if (dioCache) BaseDioCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
    }
    public void deleteByIdList(List<String> idList) throws FailException {
        if (idList.isEmpty()) throw new FailException("idList is empty");
        long count = getDao().deleteByIdList((Dto) tNull.toDto(), idList);
        if (count != idList.size()) LogUtil.i("delete multi total:" + idList.size() + " success: " + count + ". idList=" + idList);
        if (count == 0) throw new FailException();
        if (dioCache) BaseDioCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
    }

    public long update(T e) throws FailException {
        if (hasKey(e)) {
            long count = getDao().countByKeyCheckExist(e.toDto());
            if (count != 0) throw new FailException("key exists: " + e.toDto().toString());
        }
        e.updateAt();
        long count = getDao().update(e.toDto());
        if (dioCache) BaseDioCache.delete(createCacheK(e.getId()));
        return count;
    }
    public long updateByKey(T e) throws FailException {
        e.updateAt();
        Long id = getDao().findIdByKey(e.toDto());
        long count = getDao().updateByKey(e.toDto());
        if (dioCache) BaseDioCache.delete(createCacheK(id));
        return count;
    }
    public long updateBySample(T e, T sample) throws FailException {
        e.updateAt();
        List<String> idList = getDao().findIdBySample(sample.toDto());
        if (idList.isEmpty()) return 0;
        long count = getDao().updateBySample(e.toDto(), sample.toDto());
        if (dioCache) BaseDioCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
        return count;
    }
    public long updateByIdList(T e, List<String> idList) throws FailException {
        e.updateAt();
        long count = getDao().updateByIdList(e.toDto(), idList);
        if (dioCache) BaseDioCache.delete(idList.stream().map(id -> createCacheK(id)).toList());
        return count;
    }

    /**
     * 如果有id：update， 没有id：save
     * 请不要与 save， update 同时使用
     * @param e
     * @return success count
     * @throws FailException
     */
    public long saveOrUpdate(T e) throws FailException {
        if (e.getId() == null) {
            save(e);
            return 1;
        } else {
            return update(e);
        }
    }

    /**
     * 如果没传入key， 异常
     * key 已存在： updateByKey， key不存在：save
     * 请不要与 save， update 同时使用
     * @param e
     * @return success count
     * @throws FailException
     */
    public long saveOrUpdateByKey(T e) throws FailException {
        if (!hasKey(e)) throw new FailException("key NOT exists: " + e.toDto().toString());
        long count = getDao().countByKey(e.toDto());
        if (count == 0) {
            save(e);
            return 1;
        } else {
            return updateByKey(e);
        }
    }

    public T findById(T e) {
        if (dioCache) {
            Dto incache = (Dto) BaseDioCache.get(createCacheK(e.getId()));
            if (incache != null) return (T) incache.toBean();
        }
        Dto result = (Dto) getDao().findById(e.toDto());
        if (result == null) return (T) tNull;
        if (dioCache) BaseDioCache.save(createCacheK(result.getId()), result);
        return (T) result.toBean();
    }

    public long countByKey(T e) {
        if (!hasKey(e)) return 0;
        long count = getDao().countByKey(e.toDto());
        return count;
    }

    public T findByKey(T e) {
        if (!hasKey(e)) return (T) tNull;
        Dto result = (Dto) getDao().findByKey(e.toDto());
        if (result == null) return (T) tNull;
        if (dioCache) BaseDioCache.save(createCacheK(result.getId()), result);
        return (T) result.toBean();
    }

    public long countByKeyCheckExist(T e) {
        if (!hasKey(e)) return 0;
        long count = getDao().countByKeyCheckExist(e.toDto());
        return count;
    }

    private boolean hasKey(T e) {
        DtoEntity dto = e.toDto();
        return Arrays.asList(dto.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
            .anyMatch(field -> field.isAnnotationPresent(DtoKey.class));
    }

    public long count(AbstractSearchCondition searchCondition, T sample) {
        if (searchCondition.getIdList() != null && searchCondition.getIdList().isEmpty()) return 0;
        Dto dtoSample = sample!=null ? (Dto) sample.toDto() : (Dto) tNull.toDto();
        String where = baseFind(searchCondition, dtoSample);
        return getDao().count(searchCondition, dtoSample, where);
    }

    public List<T> find(AbstractSearchCondition searchCondition, T sample) {
        if (searchCondition.getIdList() != null && searchCondition.getIdList().isEmpty()) return Collections.emptyList();
        searchCondition.setDefaultSort(defaultSort);
        searchCondition.setDefaultSortAz(defaultSortAz);
        Dto dtoSample = sample!=null ? (Dto) sample.toDto() : (Dto) tNull.toDto();
        String where = baseFind(searchCondition, dtoSample);
        List<Dto> dtoList = getDao().find(searchCondition, dtoSample, where);
        List<T> result = dtoList.stream().map(e -> (T) e.toBean()).toList();
        if (dioCache) {
            dtoList.stream().forEach(e -> {
                BaseDioCache.save(createCacheK(e.getId()), e);
            });
        }

        return result;
    }

    public List<String> findId(AbstractSearchCondition searchCondition, T sample) {
        searchCondition.setDefaultSort(defaultSort);
        searchCondition.setDefaultSortAz(defaultSortAz);
        Dto dtoSample = sample!=null ? (Dto) sample.toDto() : (Dto) tNull.toDto();
        String where = baseFind(searchCondition, dtoSample);
        List<String> result = getDao().findId(searchCondition, dtoSample, where);
        return result;
    }

    protected abstract String baseFind(AbstractSearchCondition searchCondition, Dto sample);

    /**
     * 与 find 传递idList不同的是，没有排序，最大化利用缓存，适用于程序内多层级的调用查询
     * @return
     */
    public Map<String, Dto> findMapByIdList(List<String> idList) {
        Map<String, Object> incacheMap = dioCache ? BaseDioCache.get(idList.stream().map(id -> createCacheK(id)).toList()) : new HashMap<>();
        List<Dto> incacheList = incacheMap.values().stream().map(e -> (Dto)e).toList();
        Map<String, Dto> result = incacheList.stream().collect(Collectors.toMap(e -> e.getId()+"", e -> e));
        List<String> leftIdList = idList.stream().filter(id -> !incacheMap.containsKey(createCacheK(id))).toList();
        if (!leftIdList.isEmpty()) {
            Dto dtoSample = (Dto) tNull.toDto();
            List<Dto> leftDtoList = getDao().findByIdList(leftIdList, dtoSample);
            leftDtoList.stream().forEach(e -> {
                if (dioCache) BaseDioCache.save(createCacheK(e.getId()), e);
                result.put(e.getId().toString(), e);
            });
        }
        return result;
    }

    private String createCacheK(Long id) {
        return createCacheK(id+"");
    }
    private String createCacheK(String id) {
        return tClass.getSimpleName() + "Id." + id;
    }

}
