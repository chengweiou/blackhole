package chengweiou.universe.blackhole.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseSQL {
    private List<String> whereList = new ArrayList<>();
    public void WHERE(String where) {
        whereList.add(where);
    }
    public String toString() {
        if (whereList.size() == 0) return "";
        return " where " + whereList.stream().collect(Collectors.joining(" and "));
    }
}
