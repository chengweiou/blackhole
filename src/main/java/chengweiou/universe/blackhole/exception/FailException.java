package chengweiou.universe.blackhole.exception;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.RestCode;

public class FailException extends Exception {
    private RestCode code = BasicRestCode.FAIL;

    public FailException() {
        super();
    }
    public FailException(String message) {
        super(message);
    }

    public RestCode getCode() {
        return code;
    }

    public void setCode(RestCode code) {
        this.code = code;
    }

}
