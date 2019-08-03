package chengweiou.universe.blackhole.param;

import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.util.StringUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class ValidString {
    private String k;
    private String v;
    private String showV;

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
    }
    public ValidString(String k, String v) {
        this.k = k;
        this.v = v;
        this.showV = this.v != null ?
                this.v.length() > 20 ? StringUtil.hidMid(this.v) : this.v :
                "null";
    }
    public ValidString notEmpty() throws ParamException {
        if (this.v.isBlank()) throw new ParamException(this.k + ": " + this.showV + ", must not empty");
        return this;
    }
    public ValidString lengthIn(int a) throws ParamException {
        if (this.v.length() > a) throw new ParamException(this.k + ": " + this.showV + ", length must <= " + a);
        return this;
    }
    public ValidString lengthIn(int a, int b) throws ParamException {
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
        if (this.v.length() != a) throw new ParamException(this.k + ": " + this.showV + ", length must be " + a);
        return this;
    }
    public ValidString of(String ...list) throws ParamException {
        return of(Arrays.asList(list));
    }
    public ValidString of(List<String> list) throws ParamException {
        if (!list.contains(this.v)) throw new ParamException(this.k + ": " + this.showV + ", must be one of " + list);
        return this;
    }
    public ValidString notOf(String ...list) throws ParamException {
        return notOf(Arrays.asList(list));
    }
    public ValidString notOf(List<String> list) throws ParamException {
        if (list.contains(this.v)) throw new ParamException(this.k + ": " + this.showV + ", must not be one of " + list);
        return this;
    }
    public ValidString include(String ...list) throws ParamException {
        return include(Arrays.asList(list));
    }
    public ValidString include(List<String> list) throws ParamException {
        if (list.stream().anyMatch(e -> !this.v.contains("a"))) throw new ParamException(this.k + ": " + this.showV + ", must include all of " + list);
        return this;
    }

    public ValidString date() throws ParamException {
        try {
            LocalDate.parse(this.v);
            return this;
        } catch (DateTimeParseException e) {
            throw new ParamException(this.k + ": " + this.showV + ", must not be format of date: yyyy-MM-dd");
        }
    }
    public ValidString time() throws ParamException {
        try {
            LocalDateTime.parse(this.v);
            return this;
        } catch (DateTimeParseException e) {
            throw new ParamException(this.k + ": " + this.showV + ", must not be format of datetime: yyyy-MM-ddTHH:mm:ss");
        }
    }
    public ValidString dateOrTime() throws ParamException {
        try {
            LocalDate.parse(this.v);
            return this;
        } catch (DateTimeParseException e1) {
            try {
                LocalDateTime.parse(this.v);
                return this;
            } catch (DateTimeParseException e2) {
                throw new ParamException(this.k + ": " + this.showV + ", must not be format of date or datetime: yyyy-MM-dd | yyyy-MM-ddTHH:mm:ss");
            }
        }

    }
    public void objectId() throws ParamException {
        int len = this.v.length();
        if (len != 24) throw new ParamException(this.k + ": " + this.showV + ", must not be objectId");
        for (int i = 0; i < len; i++) {
            char c = this.v.charAt(i);
            if (c >= '0' && c <= '9') continue;
            if (c >= 'a' && c <= 'f') continue;
            if (c >= 'A' && c <= 'F') continue;
            throw new ParamException(this.k + ": " + this.showV + ", must not be objectId");
        }
    }
}
