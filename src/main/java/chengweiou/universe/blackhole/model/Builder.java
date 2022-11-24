package chengweiou.universe.blackhole.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import chengweiou.universe.blackhole.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
         * throw assertionError
         *      if class does not have a no param constructor
         *      if class does not have setter method for a prop
         */
        public <T> T to(Class c) {
            T result = null;
            try {
                result = (T) c.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("builder set property fail! " + c + " needs a no param constructor", e);
                throw new AssertionError("builder set property fail! \" + c + \" needs a no param constructor");
            }
            setProp(c, result);
            return result;
        }
        /**
         * no need to try catch, because it must fix when developing.
         * @param instance
         * @param <T>
         * @return
         * throw assertionError
         *      if class does not have a no param constructor
         *      if class does not have setter method for a prop
         */
        public <T> T to(T instance) {
            Class c = instance.getClass();
            setProp(c, instance);
            return instance;
        }

        private <T> void setProp(Class c, T instance) {
            List<Method> methodList = Arrays.asList(c.getMethods()).stream().filter(e -> e.getName().startsWith("set") && e.getParameterCount()==1).toList();
            Map<String, Method> methodMap = methodList.stream().collect(Collectors.toMap(Method::getName, e -> e));
            map.entrySet().stream().forEach(e -> {
                String methodName = "set" + e.getKey().substring(0, 1).toUpperCase() + e.getKey().substring(1);
                if (!methodMap.containsKey(methodName)) throw new AssertionError("builder set property fail! try to call method: "+methodName+", not found!");
                try {
                    methodMap.get(methodName).invoke(instance, e.getValue());
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    log.error("builder set property fail! " + c + "." + methodName + "(" + e.getValue().toString() + ")");
                } catch (IllegalArgumentException ex) {
                    try {
                        Object obj = switch (methodMap.get(methodName).getParameterTypes()[0].getName()) {
                            case "int", "java.lang.Integer" -> new BigDecimal(e.getValue() + "").setScale(0, RoundingMode.HALF_UP).intValueExact();
                            case "java.lang.String" -> {
                                if (e.getValue() instanceof LocalDateTime) {
                                    yield ((LocalDateTime) e.getValue()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                                }
                                yield e.getValue().toString();
                            }
                            case "java.time.LocalDate" -> DateUtil.toDate(e.getValue().toString());
                            case "java.time.LocalDateTime" -> DateUtil.toDateTime(e.getValue().toString());
                            case "java.time.Instant" -> DateUtil.toInstant(e.getValue().toString());
                            default -> methodMap.get(methodName).getParameterTypes()[0].getMethod("valueOf", String.class).invoke(null, e.getValue().toString());
                        };
                        methodMap.get(methodName).invoke(instance, obj);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ParseException exception) {
                        log.error("builder set property fail! " + c + "." + methodName + "(" + e.getValue().toString() + ")");
                    }
                }
            });
        }
    }
}
