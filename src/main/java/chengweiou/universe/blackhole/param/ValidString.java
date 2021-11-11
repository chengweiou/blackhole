package chengweiou.universe.blackhole.param;

import java.util.Arrays;
import java.util.List;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.util.DateUtil;
import chengweiou.universe.blackhole.util.StringUtil;

public class ValidString {
    private String k;
    private String v;
    private String showV;
    private boolean pass;
    private boolean emptyWord;

    public static class Nullable {
        private String k;
        private String v;
        public Nullable(String k, String v) {
            this.k = k;
            this.v = v;
        }
        public ValidString is() throws ParamException {
            return isNotNull();
        }
        public ValidString isNotNull() throws ParamException {
            if (this.v == null) throw new ParamException(this.k + ": null, " + "can not be null");
            return new ValidString(this.k, this.v);
        }
        public ValidString nullable() {
            if (this.v == null) return new ValidString(true);
            return new ValidString(this.k, this.v);
        }
    }
    public ValidString allowEmptyWord() {
        this.emptyWord = true;
        return this;
    }

    public ValidString(String k, String v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ?
                this.v.length() > 20 ? StringUtil.ellipsisMid(this.v, 20) : this.v :
                "null";
        this.pass = false;
        this.emptyWord = false;
    }
    public ValidString(boolean pass) {
        this.pass = pass;
    }
    public ValidString notEmpty() throws ParamException {
        if (this.pass) return this;
        if (!emptyWord && !StringUtil.isNotEmptyWord(v)) throw new ParamException(this.k + ": " + this.showV + ", must includes words");
        if (this.v.isBlank()) throw new ParamException(this.k + ": " + this.showV + ", must not empty");
        return this;
    }
    public ValidString lengthIn(int a) throws ParamException {
        if (this.pass) return this;
        if (this.v.length() > a) throw new ParamException(this.k + ": " + this.showV + ", length must <= " + a);
        return this;
    }
    public ValidString lengthIn(int a, int b) throws ParamException {
        if (this.pass) return this;
        if (b < 0) {
            if (this.v.length() < a) throw new ParamException(this.k + ": " + this.showV + ", length must " + this.showV + " <= " + a);
        } else {
            int min = Math.min(a, b);
            int max = Math.max(a, b);
            if (this.v.length() < min || this.v.length() > max) throw new ParamException(this.k + ": " + this.showV + ", length must " + min + " <= " + this.showV + " <= " + max);
        }
        return this;
    }
    public ValidString lengthIs(int a) throws ParamException {
        if (this.pass) return this;
        if (this.v.length() != a) throw new ParamException(this.k + ": " + this.showV + ", length must be " + a);
        return this;
    }
    public ValidString of(String ...list) throws ParamException {
        if (this.pass) return this;
        return of(Arrays.asList(list));
    }
    public ValidString of(List<String> list) throws ParamException {
        if (this.pass) return this;
        if (!list.contains(this.v)) throw new ParamException(this.k + ": " + this.showV + ", must be one of " + list);
        return this;
    }
    public ValidString notOf(String ...list) throws ParamException {
        if (this.pass) return this;
        return notOf(Arrays.asList(list));
    }
    public ValidString notOf(List<String> list) throws ParamException {
        if (this.pass) return this;
        if (list.contains(this.v)) throw new ParamException(this.k + ": " + this.showV + ", must not be one of " + list);
        return this;
    }
    public ValidString include(String ...list) throws ParamException {
        if (this.pass) return this;
        return include(Arrays.asList(list));
    }
    public ValidString include(List<String> list) throws ParamException {
        if (this.pass) return this;
        if (list.stream().anyMatch(e -> !this.v.contains(e))) throw new ParamException(this.k + ": " + this.showV + ", must include all of " + list);
        return this;
    }

    public ValidString date() throws ParamException {
        if (this.pass) return this;
        try {
            DateUtil.toDate(this.v);
            return this;
        } catch (Exception e) {
            throw new ParamException(this.k + ": " + this.showV + ", must be format of date: yyyy-MM-dd");
        }
    }
    public ValidString time() throws ParamException {
        if (this.pass) return this;
        try {
            DateUtil.toInstant(this.v);
            return this;
        } catch (Exception e) {
            throw new ParamException(this.k + ": " + this.showV + ", must be format of datetime: yyyy-MM-ddTHH:mm:ssZ");
        }
    }
    public ValidString dateOrTime() throws ParamException {
        if (this.pass) return this;
        try {
            DateUtil.toInstant(this.v);
            return this;
        } catch (Exception e1) {
            throw new ParamException(this.k + ": " + this.showV + ", must be format of date or datetime: yyyy-MM-dd | yyyy-MM-ddTHH:mm:ssZ");
        }

    }
    public ValidString objectId() throws ParamException {
        if (this.pass) return this;
        int len = this.v.length();
        if (len != 24) throw new ParamException(this.k + ": " + this.showV + ", must be objectId");
        for (int i = 0; i < len; i++) {
            char c = this.v.charAt(i);
            if (c >= '0' && c <= '9') continue;
            if (c >= 'a' && c <= 'f') continue;
            if (c >= 'A' && c <= 'F') continue;
            throw new ParamException(this.k + ": " + this.showV + ", must be objectId");
        }
        return this;
    }
}
