package chengweiou.universe.blackhole.model;

import chengweiou.universe.blackhole.util.LogUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Builder {
    /**
     * 1. create a new Entity with properties
     *  Builder.set(property, value).set(property, value).to(new Entity() || Entity.class)
     * 2. add properties to exists entity:
     *  Builder.set(property, value).set(property, value).to(entity)
     * 3. just return new obj or do not thing
     *  Builder.to(new Entity || Entity.class)
     *  Builder.to(entity)
     * @param k
     * @param v
     * @return
     */
    public static Store set(String k, Object v) {
        return new Store(k, v);
    }
    public static <T> T to(Class c) {
        return new Store().to(c);
    }
    public static <T> T to(T instance) {
        return new Store().to(instance);
    }
    public static class Store {
        public Store() {
            super();
        }
        public Store(String k, Object v) {
            super();
            this.set(k, v);
        }

        private Map<String, Object> map = new HashMap<>();

        public Store set(String k, Object v) {
            map.put(k, v);
            return this;
        }
        /**
         * no need to try catch, because it must fix when developing.
         * @param c
         * @param <T>
         * @return
         * throw nullPointException
         *      if class does not have a no param constructor
         *      if class does not have setter method for a prop
         */
        public <T> T to(Class c) {
            T result = null;
            try {
                result = (T) c.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                LogUtil.e("builder set property fail! " + c + " needs a no param constructor", e);
                throw new NullPointerException("builder set property fail! \" + c + \" needs a no param constructor");
            }
            setProp(c, result);
            return result;
        }
        /**
         * no need to try catch, because it must fix when developing.
         * @param instance
         * @param <T>
         * @return
         * throw nullPointException
         *      if class does not have a no param constructor
         *      if class does not have setter method for a prop
         */
        public <T> T to(T instance) {
            Class c = instance.getClass();
            setProp(c, instance);
            return instance;
        }

        private <T> void setProp(Class c, T instance) {
            List<Method> methodList = Arrays.asList(c.getMethods()).stream().filter(e -> e.getName().startsWith("set") && e.getParameterCount()==1).collect(Collectors.toList());
            Map<String, Method> methodMap = methodList.stream().collect(Collectors.toMap(Method::getName, e -> e));
            map.entrySet().stream().forEach(e -> {
                String methodName = "set" + e.getKey().substring(0, 1).toUpperCase() + e.getKey().substring(1);
                try {
                    methodMap.get(methodName).invoke(instance, e.getValue());
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    LogUtil.e("builder set property fail! " + c + "." + methodName + "(" + e.getValue().toString() + ")");
                } catch (IllegalArgumentException ex) {
                    Object obj;
                    try {
                        switch (methodMap.get(methodName).getParameterTypes()[0].getName()) {
                            case "java.time.LocalDate":
                                obj = LocalDate.parse(e.getValue().toString()); break;
                            case "java.time.LocalDateTime":
                                obj = LocalDateTime.parse(e.getValue().toString()); break;
                            default:
                                obj = methodMap.get(methodName).getParameterTypes()[0].getMethod("valueOf", String.class).invoke(null, e.getValue().toString());
                                break;
                        }
                        methodMap.get(methodName).invoke(instance, obj);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
                        LogUtil.e("builder set property fail! " + c + "." + methodName + "(" + e.getValue().toString() + ")");
                    }
                }
            });
        }
    }
}
