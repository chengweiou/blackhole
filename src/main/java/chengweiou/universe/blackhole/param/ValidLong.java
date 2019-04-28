package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;

public class ValidLong {
    private String k;
    private Long v;
    private String showV;
    public static class Nullable {
        private String k;
        private Long v;
        public Nullable(String k, Long v) {
            this.k = k;
            this.v = v;
        }
        public ValidLong is() throws ParamException {
            return isNotEmpty();
        }
        public ValidLong isNotEmpty() throws ParamException {
            if (this.v == null) throw new ParamException(this.k + ": null, " + "can not be null");
            return new ValidLong(this.k, this.v);
        }
    }
    public ValidLong(String k, Long v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ? this.v.toString() : "null";
    }
    public ValidLong positive() throws ParamException {
        if (this.v <= 0) throw new ParamException(this.k + ": " + this.showV + ", must > 0 ");
        return this;
    }
    public ValidLong nativeNumber() throws ParamException {
        if (this.v < 0) throw new ParamException(this.k + ": " + this.showV + ", must >= 0 ");
        return this;
    }
    public ValidLong in(int a) throws ParamException {
        if (this.v > a) throw new ParamException(this.k + ": " + this.showV + ", must <= " + a);
        return this;
    }
    public ValidLong in(int a, int b) throws ParamException {
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        if (this.v < min || this.v > max) throw new ParamException(this.k + ": " + this.showV + ", must " + min + " <= " + this.showV + " <= " + max);
        return this;
    }
}