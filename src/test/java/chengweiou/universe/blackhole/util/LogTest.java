// package chengweiou.universe.blackhole.util;


// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;

// import chengweiou.universe.blackhole.model.Builder;
// import chengweiou.universe.blackhole.model.test.BuilderEntity;

// public class LogTest {

//     @Test
//     public void d() {
//         log.debug("debug name");
//         log.debug("debug name", "debug content");
//         BuilderEntity e = Builder
//                 .set("stringType", "string123")
//                 .set("integerType", 92)
//                 .set("longType", 234)
//                 .set("doubleType", 3.42)
//                 .set("objectType", Builder.set("stringType", "inner string").to(new BuilderEntity()))
//                 .to(new BuilderEntity());
//         log.debug("debug name", e);
//         log.debug("debug name", new AssertionError("laaaaaalaa"));
//     }
//     @Test
//     public void i() {
//         log.info("info name");
//         log.info("info name", "info content");
//         BuilderEntity e = Builder
//                 .set("stringType", "string123")
//                 .set("integerType", 92)
//                 .set("longType", 234)
//                 .set("doubleType", 3.42)
//                 .set("objectType", Builder.set("stringType", "inner string").to(new BuilderEntity()))
//                 .to(new BuilderEntity());
//         log.info("info name", e);
//         log.info("info name", new AssertionError("laaaaaalaa"));
//     }
//     @Test
//     public void e() {
//         log.error("error name");
//         log.error("error name", new AssertionError("laaaaaalaa"));
//     }

//     @BeforeAll
//     public static void before() {
//         log.infonit("com.chengweiou.demobb", "org.spring");
//     }
// }
