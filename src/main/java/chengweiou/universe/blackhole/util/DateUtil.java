package chengweiou.universe.blackhole.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

/**
 * 接收到的一律视为utc时间，因为服务器时间与客户端时间不一定匹配
 */
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

    public static Instant toInstant(String text) throws ParseException {
        if (text.length() == 10) text = text + "T00:00:00Z";
        if (text.indexOf(' ') == 10) text = text.replace(" ", "T");
        if (text.length() > 20) text = text.substring(0, 19); // 2021-10-26T20:12:12.254181 -> 2021-10-26T20:12:12
        if (text.indexOf('Z') != text.length()-1) text = text + "Z";
        return Instant.parse(text);
    }

    public static String toString(Instant object) {
        return object.toString();
    }
}
