package chengweiou.universe.blackhole.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Rest<T> implements Serializable {
    private RestCode code;
    private T data;
    private String message;
    public static <T> Rest ok(T data) {
        Rest result = new Rest();
        result.setCode(BasicRestCode.OK);
        result.setData(data);
        return result;
    }
    public static Rest fail(RestCode code) {
        Rest result = new Rest();
        result.setCode(code);
        return result;
    }

    public static <T> Rest<T> from(String from) {
        Gson gson = createGson();
        return gson.fromJson(from, new TypeToken<Rest>() {}.getType());

    }
    public static <T> Rest<T> from(String from, Class c) {
        Gson gson = !RestCode.class.isAssignableFrom(c) ? createGson() : createGson(c);
        Type type = createType(Rest.class, c);
        return gson.fromJson(from,  type);
    }

    private static ParameterizedType createType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }

            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    private static Gson createGson() {
        return createGson(BasicRestCode.class);
    }
    private static Gson createGson(Class c) {
        return new GsonBuilder()
                .registerTypeHierarchyAdapter(RestCode.class, (JsonDeserializer<?>) (json, typeOfT, context) -> Enum.valueOf(c, json.getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer) (json, typeOfT, context) -> ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDate())
                .registerTypeAdapter(LocalDate.class, (JsonSerializer) (v, typeOfT, context) -> new JsonPrimitive(v.toString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer) (json, typeOfT, context) -> ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime())
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer) (v, typeOfT, context) -> new JsonPrimitive(v.toString()))
                .create();
    }

    public RestCode getCode() {
        return code;
    }

    public void setCode(RestCode code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Rest{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

