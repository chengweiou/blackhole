package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.util.StringUtil;

import java.util.List;

public class ValidObj {
    private String k;
    private Object v;
    private String showV;
    public static class Nullable {
        private String k;
        private Object v;
        public Nullable(String k, Object v) {
            this.k = k;
            this.v = v;
        }
        public ValidObj is() throws ParamException {
            return isNotNull();
        }
        public ValidObj isNotNull() throws ParamException {
            if (this.v == null) throw new ParamException(this.k + ": null, " + "can not be null");
            return new ValidObj(this.k, this.v);
        }
    }
    public ValidObj(String k, Object v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ?
                this.v.toString().length() > 20 ? StringUtil.hidMid(this.v.toString()) : this.v.toString() :
                "null";
    }
    // todo it may be null in this situaction
    public ValidObj of(List<String> list) throws ParamException {
        if (list.contains(this.v.toString())) throw new ParamException(this.k + ": " + this.showV + ", must be one of " + list);
        return this;
    }
}

