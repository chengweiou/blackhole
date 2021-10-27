package chengweiou.universe.blackhole.formatter;


import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.spring.formatter.BaseLocalDateFormatter;


public class LocalDateFormatterTest {

    @Test
    public void BaseLocalDateFormatterTest() throws ParseException {
        String text = "2021-10-26T20:12:12";
		LocalDate d = new BaseLocalDateFormatter().parse(text, null);
        Assertions.assertEquals("2021-10-26", new BaseLocalDateFormatter().print(d, null));
        text = "2021-10-26 20:12:12";
		d = new BaseLocalDateFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26", new BaseLocalDateFormatter().print(d, null));
        text = "2021-10-26";
		d = new BaseLocalDateFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26", new BaseLocalDateFormatter().print(d, null));
    }

}
