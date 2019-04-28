package chengweiou.universe.blackhole.exception;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.RestCode;

public class ProjException extends Exception {
    private RestCode code;

    public ProjException(RestCode code) {
        super();
        this.code = code;
    }
    public ProjException(String message, RestCode code) {
        super(message);
        this.code = code;
    }

    public RestCode getCode() {
        return code;
    }

    public void setCode(RestCode code) {
        this.code = code;
    }

}
