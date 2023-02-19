package io.github.vino42.base.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.SYSTEM_INTERNAL_ERROR;

/**
 * @author VINO
 * @date 2020/4/15 9:07
 * @describe 系统内部错误
 */
@JsonSerialize(using = ExceptionSerializer.class)
public class SystemInternalException extends AbstractYiShuException {

    private long code;

    private String messageDetail;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }


    public SystemInternalException(long code, String messageDetail) {
        this.messageDetail = messageDetail;
        this.code = code;
    }

    public SystemInternalException() {
        super(SYSTEM_INTERNAL_ERROR);
    }

    public SystemInternalException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return SYSTEM_INTERNAL_ERROR;
    }

}
