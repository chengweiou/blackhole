package chengweiou.universe.blackhole.spring.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import chengweiou.universe.blackhole.util.DateUtil;

public class BaseLocalDateFormatter {
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return DateUtil.toDate(text);
    }

    public String print(LocalDate object, Locale locale) {
        return DateUtil.toString(object);
    }

}
