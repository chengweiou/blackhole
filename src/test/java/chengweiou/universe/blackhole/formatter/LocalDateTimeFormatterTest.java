package chengweiou.universe.blackhole.formatter;


import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.spring.formatter.BaseLocalDateTimeFormatter;


public class LocalDateTimeFormatterTest {

    @Test
    public void BaseLocalDateTimeFormatterTest() throws ParseException {
        String text = "2021-10-26T20:12:12";
		LocalDateTime d = new BaseLocalDateTimeFormatter().parse(text, null);
        Assertions.assertEquals("2021-10-26T20:12:12", new BaseLocalDateTimeFormatter().print(d, null));
        text = "2021-10-26 20:12:12";
		d = new BaseLocalDateTimeFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T20:12:12", new BaseLocalDateTimeFormatter().print(d, null));
        text = "2021-10-26";
		d = new BaseLocalDateTimeFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T00:00:00", new BaseLocalDateTimeFormatter().print(d, null));
        text = "2021-10-26T20:12:12.254181";
		d = new BaseLocalDateTimeFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T20:12:12", new BaseLocalDateTimeFormatter().print(d, null));
        text = "2021-10-26 20:12:12.254";
		d = new BaseLocalDateTimeFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T20:12:12", new BaseLocalDateTimeFormatter().print(d, null));
    }

}
