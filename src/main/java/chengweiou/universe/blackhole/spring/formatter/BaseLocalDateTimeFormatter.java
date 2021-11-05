package chengweiou.universe.blackhole.spring.formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

import chengweiou.universe.blackhole.util.DateUtil;

public class BaseLocalDateTimeFormatter {

    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return DateUtil.toDateTime(text);
    }

    public String print(LocalDateTime object, Locale locale) {
        return DateUtil.toString(object);
    }

}
