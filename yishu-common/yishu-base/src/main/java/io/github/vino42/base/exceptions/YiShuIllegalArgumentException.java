package io.github.vino42.base.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.ILLEGAL_ARGS;

/**
 * @author VINO
 * @date 2020/4/22 16:16
 * @describe
 */
@JsonSerialize(using = ExceptionSerializer.class)
public class YiShuIllegalArgumentException extends SystemInternalException {

    private static final long serialVersionUID = -8674310062308477335L;

    public YiShuIllegalArgumentException() {
        super(ILLEGAL_ARGS);
    }

    public YiShuIllegalArgumentException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return ILLEGAL_ARGS;
    }

}
