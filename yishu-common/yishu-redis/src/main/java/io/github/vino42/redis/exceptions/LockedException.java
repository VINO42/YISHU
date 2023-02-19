package io.github.vino42.redis.exceptions;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.vino42.base.exceptions.ExceptionSerializer;
import io.github.vino42.base.exceptions.SystemInternalException;
import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.GET_RESOURCE_LOCK_FAILED_429002;

@JsonSerialize(using = ExceptionSerializer.class)
public class LockedException extends SystemInternalException {
    private static final long serialVersionUID = 1L;

    public LockedException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    public LockedException() {
        super(GET_RESOURCE_LOCK_FAILED_429002);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return GET_RESOURCE_LOCK_FAILED_429002;
    }
}
