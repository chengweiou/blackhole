package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;

import java.util.Arrays;
import java.util.List;

public class ValidDouble {
    private String k;
    private Double v;
    private String showV;
    private boolean pass;
    public static class Nullable {
        private String k;
        private Double v;
        public Nullable(String k, Double v) {
            this.k = k;
            this.v = v;
        }
        public ValidDouble is() throws ParamException {
            return isNotNull();
        }
        public ValidDouble isNotNull() throws ParamException {
            if (this.v == null) throw new ParamException(this.k + ": null, " + "can not be null");
            return new ValidDouble(this.k, this.v);
        }
        public ValidDouble nullable() {
            if (this.v == null) return new ValidDouble(true);
            return new ValidDouble(this.k, this.v);
        }
    }
    public ValidDouble(String k, Double v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ? this.v.toString() : "null";
    }
    public ValidDouble(boolean pass) {
        this.pass = pass;
    }
    public ValidDouble positive() throws ParamException {
        if (pass) return this;
        if (this.v <= 0) throw new ParamException(this.k + ": " + this.showV + ", must > 0 ");
        return this;
    }
    public ValidDouble nativeNumber() throws ParamException {
        if (pass) return this;
        if (this.v < 0) throw new ParamException(this.k + ": " + this.showV + ", must >= 0 ");
        return this;
    }
    public ValidDouble in(double a) throws ParamException {
        if (pass) return this;
        if (this.v > a) throw new ParamException(this.k + ": " + this.showV + ", must <= " + a);
        return this;
    }
    public ValidDouble in(double a, double b) throws ParamException {
        if (pass) return this;
        double min = Math.min(a, b);
        double max = Math.max(a, b);
        if (this.v < min || this.v > max) throw new ParamException(this.k + ": " + this.showV + ", must " + min + " <= " + this.showV + " <= " + max);
        return this;
    }
    public ValidDouble notOf(Double ...list) throws ParamException {
        if (pass) return this;
        return notOf(Arrays.asList(list));
    }
    public ValidDouble notOf(List<Double> list) throws ParamException {
        if (pass) return this;
        if (!list.contains(this.v)) throw new ParamException(this.k + ": " + this.showV + ", must not be one of " + list);
        return this;
    }
}
