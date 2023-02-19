package io.github.vino42.redis.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.vino42.base.exceptions.ExceptionSerializer;
import io.github.vino42.base.exceptions.SystemInternalException;
import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.REQUEST_RAIT_LIMIT;

/**
 * 幂等性异常
 */
@JsonSerialize(using = ExceptionSerializer.class)
public class RateLimitedException extends SystemInternalException {

    public RateLimitedException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    public RateLimitedException() {
        super(REQUEST_RAIT_LIMIT);
    }

    public RateLimitedException(long code, String message) {
        super(code, message);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return REQUEST_RAIT_LIMIT;
    }
}
