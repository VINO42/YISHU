package io.github.vino42.base.exceptions;


import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.AUTH_401_NEED_AUTH;


/**
 * 幂等性异常
 */
public class NeedAuthException extends SystemInternalException {

    private static final long serialVersionUID = 6610083281801529147L;

    private int code;

    private String message;

    public NeedAuthException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    public NeedAuthException(long code, String message) {
        super(code, message);
    }

    public NeedAuthException() {
        super(AUTH_401_NEED_AUTH);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return AUTH_401_NEED_AUTH;
    }
}
