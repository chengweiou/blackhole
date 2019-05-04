package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;

public class ValidDouble {
    private String k;
    private Double v;
    private String showV;
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
    }
    public ValidDouble(String k, Double v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ? this.v.toString() : "null";
    }
    public ValidDouble positive() throws ParamException {
        if (this.v <= 0) throw new ParamException(this.k + ": " + this.showV + ", must > 0 ");
        return this;
    }
    public ValidDouble nativeNumber() throws ParamException {
        if (this.v < 0) throw new ParamException(this.k + ": " + this.showV + ", must >= 0 ");
        return this;
    }
    public ValidDouble in(double a) throws ParamException {
        if (this.v > a) throw new ParamException(this.k + ": " + this.showV + ", must <= " + a);
        return this;
    }
    public ValidDouble in(double a, double b) throws ParamException {
        double min = Math.min(a, b);
        double max = Math.max(a, b);
        if (this.v < min || this.v > max) throw new ParamException(this.k + ": " + this.showV + ", must " + min + " <= " + this.showV + " <= " + max);
        return this;
    }
}

