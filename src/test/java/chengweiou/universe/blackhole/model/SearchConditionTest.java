package chengweiou.universe.blackhole.model;


import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chengweiou.universe.blackhole.model.test.TestSearchCondition;

public class SearchConditionTest {

    @Test
    public void getK() {
        TestSearchCondition searchCondition = Builder.set("k", "abc").to(new TestSearchCondition());
        Assertions.assertEquals("abc", searchCondition.getK());
        Assertions.assertEquals("abc%", searchCondition.getLike().getK());
        Assertions.assertEquals("%abc%", searchCondition.getFull().getLike().getK());
        Assertions.assertEquals("^abc.*$", searchCondition.getReg().getK());
        Assertions.assertEquals("^.*abc.*$", searchCondition.getFull().getReg().getK());
        Assertions.assertEquals("^abc.*$", searchCondition.getReg().getPattern().pattern());
        Assertions.assertEquals("^.*abc.*$", searchCondition.getFull().getReg().getPattern().pattern());
    }

    @Test
    public void getIdList() {
        TestSearchCondition searchCondition = Builder.set("idList", Arrays.asList()).to(new TestSearchCondition());
        Assertions.assertEquals("('0')", searchCondition.getForeachIdList());
        searchCondition = Builder.set("idList", Arrays.asList("3", "7")).to(new TestSearchCondition());
        Assertions.assertEquals("('3','7')", searchCondition.getForeachIdList());
        searchCondition = Builder.set("idList", Arrays.asList("'3'", "'7'")).to(new TestSearchCondition());
        Assertions.assertEquals("('3','7')", searchCondition.getForeachIdList());
    }

    @Test
    public void getOrderBy() {
        TestSearchCondition searchCondition = new TestSearchCondition();
        Assertions.assertEquals("", searchCondition.getOrderBy());
        searchCondition = Builder.set("sort", "c1").to(new TestSearchCondition());
        Assertions.assertEquals(" order by c1 desc", searchCondition.getOrderBy());
        searchCondition = Builder.set("sort", "c1").set("sortAz", true).set("defaultSort", "c2").to(new TestSearchCondition());
        Assertions.assertEquals(" order by c1 asc, c2 desc", searchCondition.getOrderBy());
    }

    @Test
    public void getSqlLimit() {
        TestSearchCondition searchCondition = new TestSearchCondition();
        Assertions.assertEquals(" limit 10 offset 0 ", searchCondition.getSqlLimit());
        searchCondition = Builder.set("limit", 3).to(new TestSearchCondition());
        Assertions.assertEquals(" limit 3 offset 0 ", searchCondition.getSqlLimit());
        searchCondition = Builder.set("limit", 3).set("skip", 9).to(new TestSearchCondition());
        Assertions.assertEquals(" limit 3 offset 9 ", searchCondition.getSqlLimit());
    }
}
