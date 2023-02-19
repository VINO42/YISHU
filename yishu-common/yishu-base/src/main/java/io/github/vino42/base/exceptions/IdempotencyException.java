package io.github.vino42.base.exceptions;



import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.DUMPLICATE_REQUEST_LIMIT;


/**
 * 幂等性异常
 */
public class IdempotencyException extends SystemInternalException {

    private static final long serialVersionUID = 6610083281801529147L;

    private int code;

    private String message;

    public IdempotencyException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    public IdempotencyException(long code, String message) {
        super(code, message);
    }

    public IdempotencyException() {
        super(DUMPLICATE_REQUEST_LIMIT);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return DUMPLICATE_REQUEST_LIMIT;
    }
}
