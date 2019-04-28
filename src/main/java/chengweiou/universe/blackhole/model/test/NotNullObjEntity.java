package chengweiou.universe.blackhole.model.test;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;

public class NotNullObjEntity implements NotNullObj {
    private String name;
    private NotNullObjEntity obj;
    public static final NotNullObjEntity NULL = new Null();
    private static class Null extends NotNullObjEntity implements NullObj {
        @Override
        public NotNullObjEntity getObj() {
            return NotNullObjEntity.NULL;
        }
    }

    @Override
    public String toString() {
        return "NotNullObjEntity{" +
                "name='" + name + '\'' +
                ", obj=" + obj +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NotNullObjEntity getObj() {
        return obj;
    }

    public void setObj(NotNullObjEntity obj) {
        this.obj = obj;
    }
}

