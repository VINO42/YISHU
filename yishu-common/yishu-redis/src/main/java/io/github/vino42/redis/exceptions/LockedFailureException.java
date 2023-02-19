package io.github.vino42.redis.exceptions;



import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.GET_RESOURCE_LOCK_FAILED_429002;


public class LockedFailureException extends LockedException {
    public LockedFailureException() {
        super(GET_RESOURCE_LOCK_FAILED_429002);
    }

    public LockedFailureException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }
}
