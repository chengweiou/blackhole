package chengweiou.universe.blackhole.dao;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.blackhole.util.LogUtil;

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
                    LogUtil.e("trying to insert (id=0) into " + getTable(e) + ". Please check code");
                    throw new NullPointerException("trying to insert (id=0) into " + getTable(e) + ". Please check code");
                }
        return "insert into " + getTable(e)
            + " (" + fieldNameList.stream().collect(Collectors.joining(",")) + ")"
            + " values (" + fieldNameList.stream().map(name -> "#{"+name+"}").collect(Collectors.joining(",")) + ")";
    }

    public String delete(T e) {
        return "delete from " + getTable(e)
            + " where id=#{id}";
    }

    public String deleteByKey(T e) {
        return "delete from " + getTable(e)
            + " where "
            + getKeyFieldNameList(e).stream().map(name -> name + "=#{"+name+"} ").collect(Collectors.joining(" and "));
    }

    public String deleteBySample(Map<String, Object> map) {
        T e = (T) map.get("e");
        T sample = (T) map.get("sample");
        return "delete from " + getTable(e)
            + " where "
            + getSampleFieldNameList(e).stream().map(name -> name + "=#{sample."+name+"} ").collect(Collectors.joining(" and "));
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
            + fieldNameList.stream().map(name -> name + "=#{"+name+"} ").collect(Collectors.joining(","))
            + " where id=#{id}";
    }

    public String updateByKey(T e) {
        List<String> fieldNameList = getFieldList(e).stream().map(Field::getName).filter(name -> !name.equals("id") && !name.equals("createAt")).toList();
        return "update " + getTable(e) + " set "
            + fieldNameList.stream().map(name -> name + "=#{"+name+"} ").collect(Collectors.joining(","))
            + " where "
            + getKeyFieldNameList(e).stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String updateBySample(Map<String, Object> map) {
        T e = (T) map.get("e");
        T sample = (T) map.get("sample");
        List<String> fieldNameList = getFieldList(e).stream().map(Field::getName).filter(name -> !name.equals("id") && !name.equals("createAt")).toList();
        return "update " + getTable(e) + " set "
            + fieldNameList.stream().map(name -> name + "=#{e."+name+"} ").collect(Collectors.joining(","))
            + " where "
            + getSampleFieldNameList(e).stream().map(name -> name + "=#{sample."+name+"} ").collect(Collectors.joining(" and "));
    }

    public String updateByIdList(Map<String, Object> map) {
        T e = (T) map.get("e");
        List<String> idList = (List) map.get("idList");
        List<String> fieldNameList = getFieldList(e).stream().map(Field::getName).filter(name -> !name.equals("id") && !name.equals("createAt")).toList();
        return "update " + getTable(e) + " set "
            + fieldNameList.stream().map(name -> name + "=#{e."+name+"} ").collect(Collectors.joining(","))
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
            + getKeyFieldNameList(e).stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String findByKey(T e) {
        return "select * from " + getTable(e)
            + " where "
            + getKeyFieldNameList(e).stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
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
        if (where.toLowerCase().contains("id in (")) return false; // 有 in 查询，不需要
        if (!searchCondition.getSqlLimit().equals("")) return false; // 已有limit，不需要
        LogUtil.i("(" + sql + ")\n"
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
                        LogUtil.e("访问" + e.getClass().getSimpleName() + "中属性："+f.getName(), ex);
                        return false;
                    }
                })
                .toList();
    }

    private List<String> getKeyFieldNameList(T e) {
        return Arrays.asList(e.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> field.isAnnotationPresent(DtoKey.class))
                .map(Field::getName)
                .toList();
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
                        LogUtil.e("sample 访问" + e.getClass().getSimpleName() + "中属性："+f.getName(), ex);
                        return false;
                    }
                })
                .map(Field::getName)
                .toList();
    }
}
