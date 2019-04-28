package chengweiou.universe.blackhole.util;


import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.test.BuilderEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LogTest {

    @Test
    public void d() {
        LogUtil.d("debug name");
        LogUtil.d("debug name", "debug content");
        BuilderEntity e = Builder
                .set("stringType", "string123")
                .set("integerType", 92)
                .set("longType", 234)
                .set("doubleType", 3.42)
                .set("objectType", Builder.set("stringType", "inner string").to(new BuilderEntity()))
                .to(new BuilderEntity());
        LogUtil.d("debug name", e);
        LogUtil.d("debug name", new NullPointerException("laaaaaalaa"));
    }
    @Test
    public void i() {
        LogUtil.i("info name");
        LogUtil.i("info name", "info content");
        BuilderEntity e = Builder
                .set("stringType", "string123")
                .set("integerType", 92)
                .set("longType", 234)
                .set("doubleType", 3.42)
                .set("objectType", Builder.set("stringType", "inner string").to(new BuilderEntity()))
                .to(new BuilderEntity());
        LogUtil.i("info name", e);
        LogUtil.i("info name", new NullPointerException("laaaaaalaa"));
    }
    @Test
    public void e() {
        LogUtil.e("error name");
        LogUtil.e("error name", new NullPointerException("laaaaaalaa"));
    }

    @BeforeAll
    public static void before() {
        LogUtil.init("com.chengweiou.demobb", "org.spring");
    }
}
