package chengweiou.universe.blackhole.exception;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.RestCode;

public class UnauthException extends Exception {
    private RestCode code = BasicRestCode.UNAUTH;

    public UnauthException() {
        super();
    }
    public UnauthException(String message) {
        super(message);
    }

    public RestCode getCode() {
        return code;
    }

    public void setCode(RestCode code) {
        this.code = code;
    }

}
