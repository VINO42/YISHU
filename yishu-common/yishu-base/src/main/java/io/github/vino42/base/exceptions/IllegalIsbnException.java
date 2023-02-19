package io.github.vino42.base.exceptions;


import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.AUTH_401_NEED_AUTH;
import static io.github.vino42.base.response.ServiceResponseCodeEnum.ILLEGAL_ISBN_ERROR;


/**
 * 非法的isbn异常
 */
public class IllegalIsbnException extends SystemInternalException {

    private static final long serialVersionUID = 6610083281801529147L;

    private int code;

    private String message;

    public IllegalIsbnException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    public IllegalIsbnException(long code, String message) {
        super(code, message);
    }

    public IllegalIsbnException() {
        super(ILLEGAL_ISBN_ERROR);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return ILLEGAL_ISBN_ERROR;
    }
}
