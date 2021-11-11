package chengweiou.universe.blackhole.util;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
                .registerTypeAdapter(Instant.class, new InstantAdapter().nullSafe())
            ;
    }
    public static Gson create() {
        return builder().create();
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write( final JsonWriter out, final LocalDate value ) throws IOException {
            out.value(DateUtil.toString(value));
        }
        @Override
        public LocalDate read( final JsonReader in ) throws IOException {
            try {
                return DateUtil.toDate(in.nextString());
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
    }

    private static final class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write( final JsonWriter out, final LocalDateTime value ) throws IOException {
            out.value(DateUtil.toString(value));
        }

        @Override
        public LocalDateTime read( final JsonReader in ) throws IOException {
            try {
                return DateUtil.toDateTime(in.nextString());
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
    }

    private static final class InstantAdapter extends TypeAdapter<Instant> {
        @Override
        public void write( final JsonWriter out, final Instant value ) throws IOException {
            out.value(DateUtil.toString(value));
        }
        @Override
        public Instant read( final JsonReader in ) throws IOException {
            try {
                return DateUtil.toInstant(in.nextString());
            } catch (ParseException e) {
                throw new IOException(e);
            }
        }
    }
}
