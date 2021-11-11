package chengweiou.universe.blackhole.spring.formatter;

import java.text.ParseException;
import java.time.Instant;
import java.util.Locale;

import chengweiou.universe.blackhole.util.DateUtil;

public class BaseInstantFormatter {

    public Instant parse(String text, Locale locale) throws ParseException {
        return DateUtil.toInstant(text);
    }

    public String print(Instant object, Locale locale) {
        return DateUtil.toString(object);
    }

}
