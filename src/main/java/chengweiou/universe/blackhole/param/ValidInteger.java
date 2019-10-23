package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;

import java.util.Arrays;
import java.util.List;

public class ValidInteger {
    private String k;
    private Integer v;
    private String showV;
    private boolean pass;
    public static class Nullable {
        private String k;
        private Integer v;
        public Nullable(String k, Integer v) {
            this.k = k;
            this.v = v;
        }
        public ValidInteger is() throws ParamException {
            return isNotNull();
        }
        public ValidInteger isNotNull() throws ParamException {
            if (this.v == null) throw new ParamException(this.k + ": null, " + "can not be null");
            return new ValidInteger(this.k, this.v);
        }
        public ValidInteger nullable() {
            if (this.v == null) return new ValidInteger(true);
            return new ValidInteger(this.k, this.v);
        }
    }
    public ValidInteger(String k, Integer v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ? this.v.toString() : "null";
    }
    public ValidInteger(boolean pass) {
        this.pass = pass;
    }
    public ValidInteger positive() throws ParamException {
        if (pass) return this;
        if (this.v <= 0) throw new ParamException(this.k + ": " + this.showV + ", must > 0 ");
        return this;
    }
    public ValidInteger nativeNumber() throws ParamException {
        if (pass) return this;
        if (this.v < 0) throw new ParamException(this.k + ": " + this.showV + ", must >= 0 ");
        return this;
    }
    public ValidInteger in(int a) throws ParamException {
        if (pass) return this;
        if (this.v > a) throw new ParamException(this.k + ": " + this.showV + ", must <= " + a);
        return this;
    }
    public ValidInteger in(int a, int b) throws ParamException {
        if (pass) return this;
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        if (this.v < min || this.v > max) throw new ParamException(this.k + ": " + this.showV + ", must " + min + " <= " + this.showV + " <= " + max);
        return this;
    }

    public ValidInteger notOf(Integer ...list) throws ParamException {
        if (pass) return this;
        return notOf(Arrays.asList(list));
    }
    public ValidInteger notOf(List<Integer> list) throws ParamException {
        if (pass) return this;
        if (!list.contains(this.v)) throw new ParamException(this.k + ": " + this.showV + ", must not be one of " + list);
        return this;
    }
}

