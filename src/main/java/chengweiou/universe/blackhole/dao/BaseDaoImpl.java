package chengweiou.universe.blackhole.dao;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.blackhole.util.LogUtil;

public class BaseDaoImpl<T> {
    public String save(T e) {
        List<Field> fieldList = Stream.of(
                    Arrays.asList(e.getClass().getDeclaredFields()),
                    Arrays.asList(e.getClass().getSuperclass().getDeclaredFields())
                ).flatMap(List::stream)
                .filter(field -> {
                    field.setAccessible(true);
                    try {
                        return !Modifier.isStatic(field.getModifiers()) && field.get(e)!=null;
                    } catch (IllegalArgumentException | IllegalAccessException e1) {
                        e1.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return "insert into " + getTable(e)
            + " (" + fieldList.stream().map(Field::getName).collect(Collectors.joining(",")) + ")"
            + " values (" + fieldList.stream().map(f -> "#{"+f.getName()+"}").collect(Collectors.joining(",")) + ")";
    }

    public String delete(T e) {
        return "delete from " + getTable(e)
            + " where id=#{id}";
    }

    public String update(T e) {
        List<Field> fieldList = Stream.of(
                    Arrays.asList(e.getClass().getDeclaredFields()),
                    Arrays.asList(e.getClass().getSuperclass().getDeclaredFields())
                ).flatMap(List::stream)
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(f -> !f.getName().equals("id") && !f.getName().equals("createAt"))
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(e) != null;
                    } catch (IllegalAccessException ex) {
                        LogUtil.e("访问" + e.getClass().getSimpleName() + "中属性："+f.getName(), ex);
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return "update " + getTable(e) + " set "
            + fieldList.stream().map(f -> f.getName() + "=#{"+f.getName()+"} ").collect(Collectors.joining(","))
            + " where id=#{id}";
    }

    public String findById(T e) {
        return "select * from " + getTable(e)
        + " where id=#{id}";
    }

    public String countByKey(T e) {
        List<String> fieldNameList = Arrays.asList(e.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> field.isAnnotationPresent(DtoKey.class))
                .map(Field::getName)
                .collect(Collectors.toList());
        return "select count(*) from " + getTable(e)
            + " where " + fieldNameList.stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
    }

    public String findByKey(T e) {
        List<String> fieldNameList = Arrays.asList(e.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> field.isAnnotationPresent(DtoKey.class))
                .map(Field::getName)
                .collect(Collectors.toList());
        return "select * from " + getTable(e)
            + " where " + fieldNameList.stream().map(name -> name+"=#{"+name+"}").collect(Collectors.joining(" and "));
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
        return "select * from " + getTable(sample)
                + where
                + searchCondition.getOrderBy() + searchCondition.getSqlLimit()
            ;
    }

    private String getTable(T e) {
        String name = e.getClass().getName();
        // 遇到内部静态类，采用外部类名称
        int end = name.contains("$") ? name.lastIndexOf("$") : name.length();
        name = name.substring(name.lastIndexOf(".")+1, end);
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
}
