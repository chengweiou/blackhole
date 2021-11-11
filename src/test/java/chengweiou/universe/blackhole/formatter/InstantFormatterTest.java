package chengweiou.universe.blackhole.formatter;


import java.text.ParseException;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.spring.formatter.BaseInstantFormatter;


public class InstantFormatterTest {

    @Test
    public void BaseInstantFormatterTest() throws ParseException {
        String text = "2021-10-26T20:12:12Z";
		Instant d = new BaseInstantFormatter().parse(text, null);
        Assertions.assertEquals("2021-10-26T20:12:12Z", new BaseInstantFormatter().print(d, null));
        text = "2021-10-26T20:12:12";
		d = new BaseInstantFormatter().parse(text, null);
        Assertions.assertEquals("2021-10-26T20:12:12Z", new BaseInstantFormatter().print(d, null));
        text = "2021-10-26 20:12:12";
		d = new BaseInstantFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T20:12:12Z", new BaseInstantFormatter().print(d, null));
        text = "2021-10-26";
		d = new BaseInstantFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T00:00:00Z", new BaseInstantFormatter().print(d, null));
        text = "2021-10-26T20:12:12.254181";
		d = new BaseInstantFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T20:12:12Z", new BaseInstantFormatter().print(d, null));
        text = "2021-10-26 20:12:12.254";
		d = new BaseInstantFormatter().parse(text, null);
		Assertions.assertEquals("2021-10-26T20:12:12Z", new BaseInstantFormatter().print(d, null));
    }

}
