package chengweiou.universe.blackhole.dao;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chengweiou.universe.blackhole.dao.model.DtoProp;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseDaoImpl<T> {
    public String save(T e) {
        AtomicBoolean fail = new AtomicBoolean();
        List<String> fieldNameList = getFieldList(e).stream()
                .filter(field -> {
                    try {
                        Object v = field.get(e);
                        if (field.getName().equals("id") && v!=null && v.equals(0L)) fail.set(true);
                        return v!=null;
                    } catch (IllegalArgumentException | IllegalAccessException e1) {
                        e1.printStackTrace();
                        return false;
                    }
                })
                .map(Field::getName)
                .toList();
                if (fail.get()) {
                    log.error("trying to insert (id=0) into " + getTable(e) + ". Please check code");
                    throw new AssertionError("trying to insert (id=0) into " + getTable(e) + ". Please check code");
                }
        return "insert into " + getTable(e)
            + fieldNameList.stream().collect(Collectors.joining(", ", "(", ")"))
            + " values" + fieldNameList.stream().map(name -> "#{"+name+"}").collect(Collectors.joining(", ", "(", ")"));
    }

    public String delete(T e) {
        return "delete from " + getTable(e)
            + " where id=#{id}";
    }

    public String deleteByKey(T e) {
        return "delete from " + getTable(e)
            + " where "
            + getOneHasValueKeyList(e).stream().map(name -> name + "=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String deleteBySample(Map<String, Object> map) {
        T e = (T) map.get("e");
        T sample = (T) map.get("sample");
        return "delete from " + getTable(e)
            + " where "
            + getSampleFieldNameList(e).stream().map(name -> name + "=#{sample."+name+"}").collect(Collectors.joining(" and "));
    }

    public String deleteByIdList(Map<String, Object> map) {
        T e = (T) map.get("e");
        List<String> idList = (List) map.get("idList");
        return "delete from " + getTable(e)
            + " where id = "
            + " any ( select id from " + getTable(e) + " where id in "
                + "(" + idList.stream().map(id->id.toString()).collect(Collectors.joining(",")) + ")"
            + " )";
    }

    public String update(T e) {
        List<String> fieldNameList = getFieldList(e).stream().map(Field::getName).filter(name -> !name.equals("id") && !name.equals("createAt")).toList();
        return "update " + getTable(e) + " set "
            + fieldNameList.stream().map(name -> name + "=#{"+name+"}").collect(Collectors.joining(", "))
            + " where id=#{id}";
    }


    public String updateByKey(T e) {
        List<String> fieldNameList = getFieldList(e).stream()
            // dtoKey 的部分不更新,不管是偏的还是全的
            .filter(field->!field.isAnnotationPresent(DtoKey.class))
            .map(Field::getName).filter(name -> !name.equals("id") && !name.equals("createAt")).toList();
        return "update " + getTable(e) + " set "
            + fieldNameList.stream().map(name -> name + "=#{"+name+"}").collect(Collectors.joining(", "))
            + " where "
            + getOneHasValueKeyList(e).stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String updateBySample(Map<String, Object> map) {
        T e = (T) map.get("e");
        T sample = (T) map.get("sample");
        List<String> fieldNameList = getFieldList(e).stream()
            // dtoKey 的部分不更新,不管是偏的还是全的
            .filter(field->!field.isAnnotationPresent(DtoKey.class))
            .map(Field::getName).filter(name -> !name.equals("id") && !name.equals("createAt")).toList();
        return "update " + getTable(e) + " set "
            + fieldNameList.stream().map(name -> name + "=#{e."+name+"} ").collect(Collectors.joining(", "))
            + " where "
            + getSampleFieldNameList(e).stream().map(name -> name + "=#{sample."+name+"}").collect(Collectors.joining(" and "));
    }

    public String updateByIdList(Map<String, Object> map) {
        T e = (T) map.get("e");
        List<String> idList = (List) map.get("idList");
        List<String> fieldNameList = getFieldList(e).stream().map(Field::getName).filter(name -> !name.equals("id") && !name.equals("createAt")).toList();
        return "update " + getTable(e) + " set "
            + fieldNameList.stream().map(name -> name + "=#{e."+name+"} ").collect(Collectors.joining(", "))
            + " where id in "
            + "(" + idList.stream().map(id->id.toString()).collect(Collectors.joining(",")) + ")";
    }

    public String findById(T e) {
        return "select * from " + getTable(e)
        + " where id=#{id}";
    }

    public String countByKey(T e) {
        return "select count(*) from " + getTable(e)
            + " where "
            + getOneHasValueKeyList(e).stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String findByKey(T e) {
        return "select * from " + getTable(e)
            + " where "
            + getOneHasValueKeyList(e).stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String countByKeyCheckExist(T e) {
        String idWhere = "";
        try {
            Method method = e.getClass().getMethod("getId");
            Object id = method.invoke(e);
            if (id!=null) idWhere = " and id!=#{id}";
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
            log.info("entity " + e.getClass() + " 没有 id ");
        }
        Map<String, List<String>> successGroupMap = getAllHasValueKeyMap(e);
        if (successGroupMap.isEmpty()) return "select 0"; // todo 这里内部跑了异常
        String keyWhere = successGroupMap.entrySet().stream()
            .collect(Collectors.toMap(entry->entry.getKey(), entry->entry.getValue().stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and ", "(", ")"))))
            .values().stream().collect(Collectors.joining(" or ", "(", ")"))
            ;
        // 这里需要所有的key a=a or b=b or (c=c and d=d) if (id!=null) id!=id
        return "select count(*) from " + getTable(e)
        + " where "
        + keyWhere + idWhere;
    }

    /**
     * 要有 searchCondition(用于skip, limit), sample(用于 table)
     * 条件传递完整的 where 语句
     * @param map
     * @return
     */
    public String count(Map<String, Object> map) {
        AbstractSearchCondition searchCondition = (AbstractSearchCondition) map.get("searchCondition");
        T sample = (T) map.get("sample");
        String where = (String) map.get("where");
        return "select count(*) from " + getTable(sample)
                + where
            ;
    }
    public String find(Map<String, Object> map) {
        AbstractSearchCondition searchCondition = (AbstractSearchCondition) map.get("searchCondition");
        T sample = (T) map.get("sample");
        String where = (String) map.get("where");
        String result = "select * from " + getTable(sample)
                        + where
                        + searchCondition.getOrderBy() + searchCondition.getSqlLimit();
        if (needLimit(result, where, searchCondition)) result += searchCondition.getSqlLimit(true);
        return result;
    }

    public String findIdByKey(T e) {
        return "select id from " + getTable(e)
            + " where "
            + getOneHasValueKeyList(e).stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String findIdBySample(T e) {
        return "select id from " + getTable(e)
            + " where "
            + getSampleFieldNameList(e).stream().map(name -> name + "=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String findId(Map<String, Object> map) {
        AbstractSearchCondition searchCondition = (AbstractSearchCondition) map.get("searchCondition");
        T sample = (T) map.get("sample");
        String where = (String) map.get("where");
        String result = "select * from " + getTable(sample)
                        + where
                        + searchCondition.getOrderBy() + searchCondition.getSqlLimit();
        if (needLimit(result, where, searchCondition)) result += searchCondition.getSqlLimit(true);
        return result;
    }

    public String findByIdList(Map<String, Object> map) {
        T sample = (T) map.get("sample");
        List<String> idList = (List<String>) map.get("idList");
        idList = idList.parallelStream().map(e -> e.replaceAll("'", "").replaceAll("\"", "")).distinct().toList();
        String foreachIdList = "(" + idList.parallelStream().map(e -> "'" + e + "'").collect(Collectors.joining(",")) + ")";
        String result = "select * from " + getTable(sample)
                        + " where id in " + foreachIdList;
        return result;
    }

    private boolean needLimit(String sql, String where, AbstractSearchCondition searchCondition) {
        if (searchCondition.getLimit() != 10) return false; // 被手动设置过limit，不需要
        if (where.toLowerCase().contains("id in ")) return false; // 有 in 查询，不需要
        if (!searchCondition.getSqlLimit().equals("")) return false; // 已有limit，不需要
        log.info("(" + sql + ")\n"
            + "This sql is NOT include (sql in) sentense && not set limit.\n"
            + "Is searchCondition.idList!=null && dio does not use #{searchCondition.foreachIdList} ? \n"
            );
        return true;
    }

    private String getTable(T e) {
        String name = e.getClass().getName();
        // 遇到内部静态类，采用外部类名称
        int end = name.contains("$") ? name.lastIndexOf("$") : name.length();
        name = name.substring(name.lastIndexOf(".")+1, end);
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private List<Field> getFieldList(T e) {
        return Stream.of(
                    Arrays.asList(e.getClass().getDeclaredFields()),
                    Arrays.asList(e.getClass().getSuperclass().getDeclaredFields())
                ).flatMap(List::stream)
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(f -> {
                    f.setAccessible(true);
                    try {
                        return f.get(e) != null;
                    } catch (IllegalAccessException ex) {
                        log.error("访问" + e.getClass().getSimpleName() + "中属性："+f.getName(), ex);
                        return false;
                    }
                })
                .toList();
    }

    /**
     * 获取其中一个有值的 dtoKey 组，如果有多个组，只获取第一个成功的。一个组已经能确认一个唯一用户
     * @param e
     * @return 如果 没有成功获取 完整的一组，抛 assertionerror 异常
     */
    private List<String> getOneHasValueKeyList(T e) {
        Map<String, List<String>> successGroupMap = getAllHasValueKeyMap(e);
        if (successGroupMap.isEmpty()) throw new AssertionError("can not find a group of key field is NOT all null: " + getTable(e) + ". Please check code");
        return successGroupMap.values().stream().findFirst().orElse(Collections.emptyList());
    }

    /**
     * 获取全部有值的 dtoKey 组, 一个 dtoKey 组的成员全部有值，才成功获取
     * @param e
     * @return 如果 没有成功获取 完整的一组，返回空集合
     * 添加 的
     */
    private Map<String, List<String>> getAllHasValueKeyMap(T e) {
        List<DtoProp> dtoPropList = getKeyDtoPropList(e);
        // 清理，如果这个组里有一个值是empty string，则排除这个组
        List<String> hasEmptyGroupList = dtoPropList.stream().filter(dtoProp->dtoProp.getCanEmpty()&&"".equals(dtoProp.getV())).map(DtoProp::getGroup).distinct().toList();
        dtoPropList = dtoPropList.stream().filter(dtoProp->!hasEmptyGroupList.contains(dtoProp.getGroup())).toList();
        Map<String, List<DtoProp>> groupMap = dtoPropList.stream().collect(Collectors.groupingBy(dtoProp -> dtoProp.getGroup()));
        List<String> successGroupList = groupMap.entrySet().stream()
            .map(entrySet -> entrySet.getValue().stream().reduce(new DtoProp("", null, true, false), (a,b)-> new DtoProp(b.getGroup(), null, a.getV()!=null&&b.getV()!=null, false)))
            .filter(dtoProp->(Boolean)(dtoProp.getV())).map(dtoProp->dtoProp.getGroup()).toList();
        if (successGroupList.isEmpty()) return Map.of();
        return successGroupList.stream().collect(Collectors.toMap(group->group, group->groupMap.get(group).stream()
            .filter(dtoProp-> !dtoProp.getCanEmpty() || !dtoProp.getV().toString().isBlank())
            .map(DtoProp::getField).toList()));
    }

    private List<DtoProp> getKeyDtoPropList(T e) {
        List<DtoProp> result = new ArrayList<>();
        int singleCount = 1;
        List<Field> fieldList = Arrays.asList(e.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
            .filter(field -> field.isAnnotationPresent(DtoKey.class)).toList();
        for (Field field : fieldList) {
            DtoKey dtoKey = field.getDeclaredAnnotation(DtoKey.class);
            String groupName = dtoKey.group();
            if (groupName.startsWith(DtoProp.SINGLE_GROUP_PREV)) throw new AssertionError("Dto Key group name cannot start with: " + DtoProp.SINGLE_GROUP_PREV);
            if (dtoKey.single()) {
                groupName = DtoProp.SINGLE_GROUP_PREV + singleCount;
                singleCount ++;
            }
            Object v = null;
            try {
                field.setAccessible(true);
                v = field.get(e);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
            }
            result.add(new DtoProp(groupName, field.getName(), v, dtoKey.canEmpty()));
        }
        return result;
    }

    private List<String> getSampleFieldNameList(T e) {
        return Stream.of(
                    Arrays.asList(e.getClass().getDeclaredFields())
                ).flatMap(List::stream)
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(e) != null;
                    } catch (IllegalAccessException ex) {
                        log.error("sample 访问" + e.getClass().getSimpleName() + "中属性："+f.getName(), ex);
                        return false;
                    }
                })
                .map(Field::getName)
                .toList();
    }
}
