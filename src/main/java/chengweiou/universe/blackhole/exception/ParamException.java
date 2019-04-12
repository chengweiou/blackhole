package chengweiou.universe.blackhole.exception;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.RestCode;

public class ParamException extends Exception {
    private RestCode code = BasicRestCode.PARAM;

    public ParamException() {
        super();
    }
    public ParamException(String message) {
        super(message);
    }

    public RestCode getCode() {
        return code;
    }

    public void setCode(RestCode code) {
        this.code = code;
    }

}
