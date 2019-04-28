package chengweiou.universe.blackhole.model;


import chengweiou.universe.blackhole.model.test.TestRestCode;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestTest {

    @Test
    public void createByCustomCode() {
        String json = new Gson().toJson(Rest.fail(TestRestCode.TEST));
        Rest rest = Rest.from(json, TestRestCode.class);
        Assertions.assertEquals(TestRestCode.TEST, rest.getCode());
    }

}
