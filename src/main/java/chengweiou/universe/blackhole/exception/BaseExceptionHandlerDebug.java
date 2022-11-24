package chengweiou.universe.blackhole.exception;


import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseExceptionHandlerDebug {
    public Rest handleProjException(ProjException ex) {
        Rest rest = Rest.fail(ex.getCode());
        rest.setMessage(ex.getMessage());
        return rest;
    }
    public Rest handleParamException(ParamException ex) {
        Rest rest = Rest.fail(BasicRestCode.PARAM);
        rest.setMessage(ex.getMessage());
        return rest;
    }
    public Rest handleFailException(FailException ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);
        rest.setMessage(ex.getMessage());
        log.info(rest.toString(), ex);
        return rest;
    }
    public Rest handleException(Exception ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);
        rest.setMessage(ex.getMessage());
        log.error(rest.toString(), ex);
        return rest;
    }
}
