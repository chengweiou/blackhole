package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.util.StringUtil;

import java.util.List;

public class ValidObjList {
    private String k;
    private List<Object> v;
    private String showV;
    public static class Nullable {
        private String k;
        private List<Object> v;
        public Nullable(String k, List<Object> v) {
            this.k = k;
            this.v = v;
        }
        public ValidObjList are() {
            return new ValidObjList(this.k, this.v);
        }
    }
    public ValidObjList(String k, List<Object> v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ?
                this.v.toString().length() > 20 ? StringUtil.hidMid(this.v.toString()) : this.v.toString() :
                "null";
    }
    public ValidObjList notAllNull() throws ParamException {
        if (this.v.stream().allMatch(e -> e == null)) throw new ParamException(this.k + ": " + this.showV + ", cannot be all null");
        return this;
    }
    public ValidObjList noneNull() throws ParamException {
        if (this.v.stream().anyMatch(e -> e == null)) throw new ParamException(this.k + ": " + this.showV + ", cannot be any null");
        return this;
    }
    public ValidObjList notSame() throws ParamException {
        if (this.v.stream().distinct().count() != this.v.size()) throw new ParamException(this.k + ": " + this.showV + ", cannot be any same");
        return this;
    }
}
