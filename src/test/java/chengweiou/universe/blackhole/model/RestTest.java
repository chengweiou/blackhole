package chengweiou.universe.blackhole.model;


import java.time.LocalDateTime;
import java.time.ZoneId;

import com.google.gson.Gson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.model.test.TestRestCode;

public class RestTest {

    @Test
    public void createByCustomCode() {
        String json = new Gson().toJson(Rest.fail(TestRestCode.TEST));
        Rest rest = Rest.from(json, TestRestCode.class);
        Assertions.assertEquals(TestRestCode.TEST, rest.getCode());
    }

    // todo add zone choose to blackhole config.
    @Test
    public void stringToDateTime() {
        String json = "2019-01-01T00:00:00";
        LocalDateTime local = LocalDateTime.parse(json).atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(local);
        LocalDateTime local1 = LocalDateTime.parse(json).atZone(ZoneId.of("UTC")).toLocalDateTime();
        System.out.println(local1);
    }
}
