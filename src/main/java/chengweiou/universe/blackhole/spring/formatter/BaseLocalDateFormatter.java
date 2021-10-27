package chengweiou.universe.blackhole.spring.formatter;

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
import java.util.Locale;

public class BaseLocalDateFormatter {
    private List<String> list = Arrays.asList(
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd"
    );

    public LocalDate parse(String text, Locale locale) throws ParseException {
        for (String pattern : list) {
            try {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .append(DateTimeFormatter.ofPattern(pattern))
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
                return LocalDateTime.parse(text, formatter).toLocalDate();
            } catch(DateTimeParseException ex) {
            }
        }
        return ZonedDateTime.parse(text).toLocalDate();
    }

    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }

}
