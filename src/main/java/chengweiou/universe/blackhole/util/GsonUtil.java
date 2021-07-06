package chengweiou.universe.blackhole.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * include localDate， localDateTime TypeAdapter
 */
public class GsonUtil {
    public static GsonBuilder builder() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
            ;
    }
    public static Gson create() {
        return builder().create();
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write( final JsonWriter out, final LocalDate value ) throws IOException {
            out.value(value.toString());
        }
        @Override
        public LocalDate read( final JsonReader in ) throws IOException {
            return LocalDate.parse(in.nextString());
        }
    }

    private static final class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write( final JsonWriter out, final LocalDateTime value ) throws IOException {
            out.value(value.toString());
        }

        @Override
        public LocalDateTime read( final JsonReader in ) throws IOException {
            return LocalDateTime.parse(in.nextString()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }
}
