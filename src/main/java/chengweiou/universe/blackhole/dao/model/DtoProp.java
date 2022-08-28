package chengweiou.universe.blackhole.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoProp {
    private String group;
    private String field;
    private Object v;
    private Boolean canEmpty;

    public static final String SINGLE_GROUP_PREV = "noGroup";
    public static final String DEFAULT_GROUP = "defaultGroup";
}
