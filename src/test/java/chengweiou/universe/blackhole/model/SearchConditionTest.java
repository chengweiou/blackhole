package chengweiou.universe.blackhole.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearchConditionTest {

    @Test
    public void getK() {
        SearchCondition searchCondition = Builder.set("k", "abc").to(new SearchCondition());
        Assertions.assertEquals("abc", searchCondition.getK());
        Assertions.assertEquals("abc%", searchCondition.getLike().getK());
        Assertions.assertEquals("%abc%", searchCondition.getFull().getLike().getK());
    }

    @Test
    public void getOrderBy() {
        SearchCondition searchCondition = new SearchCondition();
        Assertions.assertEquals("", searchCondition.getOrderBy());
        searchCondition = Builder.set("sort", "c1").to(new SearchCondition());
        Assertions.assertEquals(" order by c1 desc", searchCondition.getOrderBy());
        searchCondition = Builder.set("sort", "c1").set("sortAz", true).set("defaultSort", "c2").to(new SearchCondition());
        Assertions.assertEquals(" order by c1 asc, c2 desc", searchCondition.getOrderBy());
    }

    @Test
    public void getSqlLimit() {
        SearchCondition searchCondition = new SearchCondition();
        Assertions.assertEquals(" limit 0, 10 ", searchCondition.getSqlLimit());
        searchCondition = Builder.set("limit", 3).to(new SearchCondition());
        Assertions.assertEquals(" limit 0, 3 ", searchCondition.getSqlLimit());
        searchCondition = Builder.set("limit", 3).set("skip", 9).to(new SearchCondition());
        Assertions.assertEquals(" limit 9, 3 ", searchCondition.getSqlLimit());
    }
}
