package chengweiou.universe.blackhole.dao.model;

import chengweiou.universe.blackhole.model.entity.DtoKey;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoProp {
    private String group;
    private String field;
    private Object v;
}
