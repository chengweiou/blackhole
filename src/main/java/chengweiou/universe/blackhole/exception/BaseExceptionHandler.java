package chengweiou.universe.blackhole.exception;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.util.LogUtil;

public class BaseExceptionHandler {
    public Rest handleProjException(ProjException ex) {
        return Rest.fail(ex.getCode());
    }
    public Rest handleParamException(ParamException ex) {
        return Rest.fail(BasicRestCode.PARAM);
    }
    public Rest handleFailException(FailException ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);
        LogUtil.i(rest.toString(), ex);
        return rest;
    }
    public Rest handleException(Exception ex) {
        Rest rest = Rest.fail(BasicRestCode.FAIL);
        LogUtil.e(rest.toString(), ex);
        return rest;
    }
}
