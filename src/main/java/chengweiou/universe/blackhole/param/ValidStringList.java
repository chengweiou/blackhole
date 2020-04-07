package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.util.StringUtil;

import java.util.List;

public class ValidStringList {
    private String k;
    private List<String> v;
    private String showV;
    public static class Nullable {
        private String k;
        private List<String> v;
        public Nullable(String k, List<String> v) {
            this.k = k;
            this.v = v;
        }
        public ValidStringList are() {
            return new ValidStringList(this.k, this.v);
        }
    }
    public ValidStringList(String k, List<String> v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ?
                this.v.toString().length() > 20 ? StringUtil.ellipsisMid(this.v.toString(), 20) : this.v.toString() :
                "null";
    }
    public ValidStringList notAllNull() throws ParamException {
        if (this.v.stream().allMatch(e -> e == null)) throw new ParamException(this.k + ": " + this.showV + ", cannot be all null");
        return this;
    }
    public ValidStringList noneNull() throws ParamException {
        if (this.v.stream().anyMatch(e -> e == null)) throw new ParamException(this.k + ": " + this.showV + ", cannot be any null");
        return this;
    }
    public ValidStringList notSame() throws ParamException {
        if (this.v.stream().distinct().count() != this.v.size()) throw new ParamException(this.k + ": " + this.showV + ", cannot be any same");
        return this;
    }
}
