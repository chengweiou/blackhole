package chengweiou.universe.blackhole.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractSearchCondition {
    /**
     * WHERE("name LIKE #{searchCondition.like.k}");
     * WHERE("name LIKE #{searchCondition.full.like.k}");
     */
    private String k;
    private String minDate;
    private String maxDate;
    /**
     * if (searchCondition.getIdList() != null) WHERE("id in ${searchCondition.foreachIdList}");
     */
    private List<String> idList;
    /**
     * new SQL{{}}.toString().concat(searchCondition.getOrderBy()).concat(searchCondition.getSqlLimit());
     */
    private int skip;
    /**
     * 0 will make it unlimited
     *  limit 3, 10 || offset 3 (when limit 0)
     */
    private int limit = 10;
    /**
     * suggest for frontend use
     */
    private String sort;
    private boolean sortAz;
    /**
     * suggest for backend use
     */
    private String defaultSort;
    private boolean defaultSortAz;

    private Full full;
    private Like like;
    private Reg reg;

    public String getForeachIdList() {
        if (idList.isEmpty()) return "('0')";
        idList = idList.parallelStream().map(e -> e.replaceAll("'", "").replaceAll("\"", "")).distinct().toList();
        return "(" + idList.parallelStream().map(e -> "'" + e + "'").collect(Collectors.joining(",")) + ")";
    }
    public String getSqlLimit() {
        if (limit == 0) return "";
        if (idList != null && !idList.isEmpty()) return "";
        return " limit " + limit + " offset " + skip + " ";
    }
    public String getOrderBy() {
        List<String> list = new ArrayList<>();
        if (sort != null) list.add(sort + (sortAz?" asc":" desc"));
        if (defaultSort != null) list.add(defaultSort + (defaultSortAz?" asc":" desc"));
        return (!list.isEmpty() ? " order by " : "") + list.stream().collect(Collectors.joining(", "));
    }

    // todo 别名 配置器
    public Full getFull() {
        if (full == null) full = new Full(k);
        return full;
    }
    public static class Full {
        private String k;
        private Like like;
        private Reg reg;
        Full(String k) { this.k = k; }
        public Like getLike() {
            if (like == null) like = new Like("%" + k);
            return like;
        }
        public Reg getReg() {
            if (reg == null) reg = new Reg(".*" + k);
            return reg;
        }
    }
    public static class Like {
        private String k;
        Like(String k) { this.k = k; }
        public String getK() { return this.k + "%"; }
    }
    public static class Reg {
        private String k;
        Reg(String k) { this.k = k; }
        public String getK() { return "^" + this.k + ".*$"; }
        public Pattern getPattern() { return Pattern.compile("^" + this.k + ".*$", Pattern.CASE_INSENSITIVE); }
    }

    public Like getLike() {
        if (like == null) like = new Like(k);
        return like;
    }
    public Reg getReg() {
        if (reg == null) reg = new Reg(k);
        return reg;
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

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
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
