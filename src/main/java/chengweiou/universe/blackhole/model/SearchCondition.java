package chengweiou.universe.blackhole.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchCondition {
    private String k;
    private String minDate;
    private String maxDate;
    private int skip;
    private int limit = 10;
    private String sort;
    private boolean sortAz;
    private String defaultSort;
    private boolean defaultSortAz;

    private Full full;
    private Like like;

    public String getSqlLimit() {
        return " limit " + skip + ", " + limit + " ";
    }
    public String getOrderBy() {
        List<String> list = new ArrayList<>();
        if (sort != null) list.add(sort + (sortAz?" asc":" desc"));
        if (defaultSort != null) list.add(defaultSort + (defaultSortAz?" asc":" desc"));
        return (!list.isEmpty() ? " order by " : "") + list.stream().collect(Collectors.joining(", "));
    }

    // todo 别名 配置器
    public Full getFull() {
        if (full == null) full = new Full(new Like("%" + k + "%"));
        return full;
    }
    public static class Full {
        private Like like;
        Full(Like like) {
            this.like = like;
        }
        public Like getLike() { return like; }
    }
    public Like getLike() {
        if (like == null) like = new Like(k + "%");
        return like;
    }
    public static class Like {
        private String k;
        Like(String k) { this.k = k; }
        public String getK() { return this.k; }
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "k='" + k + '\'' +
                ", minDate='" + minDate + '\'' +
                ", maxDate='" + maxDate + '\'' +
                ", skip=" + skip +
                ", limit=" + limit +
                ", sort='" + sort + '\'' +
                ", sortAz=" + sortAz +
                ", defaultSort='" + defaultSort + '\'' +
                ", defaultSortAz=" + defaultSortAz +
                ", full=" + full +
                ", like=" + like +
                '}';
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isSortAz() {
        return sortAz;
    }

    public void setSortAz(boolean sortAz) {
        this.sortAz = sortAz;
    }

    public String getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(String defaultSort) {
        this.defaultSort = defaultSort;
    }

    public boolean isDefaultSortAz() {
        return defaultSortAz;
    }

    public void setDefaultSortAz(boolean defaultSortAz) {
        this.defaultSortAz = defaultSortAz;
    }
}

