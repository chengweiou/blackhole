package chengweiou.universe.blackhole.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DateUtil {
    private static List<String> list = Arrays.asList(
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd"
    );

    public static LocalDate toDate(String text) throws ParseException {
        for (String pattern : list) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .append(DateTimeFormatter.ofPattern(pattern))
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                    .toFormatter();
                return LocalDateTime.parse(text, formatter).toLocalDate();
            } catch(DateTimeParseException ex) {
            }
        }
        return ZonedDateTime.parse(text).toLocalDate();
    }

    public static String toString(LocalDate object) {
        return object.toString();
    }

    public static LocalDateTime toDateTime(String text) throws ParseException {
        for (String pattern : list) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .append(DateTimeFormatter.ofPattern(pattern))
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                    .toFormatter();
                return LocalDateTime.parse(text, formatter);
            } catch(DateTimeParseException ex) {
            }
        }
        return ZonedDateTime.parse(text).toLocalDateTime();
    }

    public static String toString(LocalDateTime object) {
        return object.format(DateTimeFormatter.ofPattern(list.get(0)));
    }
}
