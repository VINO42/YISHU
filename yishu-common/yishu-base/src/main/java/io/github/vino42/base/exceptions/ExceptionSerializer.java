package io.github.vino42.base.exceptions;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.threadlocal.ThreadContext;

import java.io.IOException;
import java.time.LocalDateTime;

import static io.github.vino42.base.constants.YiShuConstant.THREAD_CACHE_KEY.THREAD_LOCAL_REQUEST_START_TIME;


/**
 * @author VINO
 * @date 2020/4/22 14:44
 * @describe
 */
public class ExceptionSerializer extends StdSerializer<SystemInternalException> {

    private static final long serialVersionUID = 7463153313955193007L;

    public ExceptionSerializer() {

        super(SystemInternalException.class);
    }

    @Override
    public void serialize(
            SystemInternalException e, JsonGenerator gen, SerializerProvider serializerProvider)
            throws IOException {
        Object parameter = ThreadContext.getParameter(THREAD_LOCAL_REQUEST_START_TIME);
        ServiceResponseResult<Object> serviceResponseResult = e.getServiceResponseResult();
        LocalDateTime requestStartTime = LocalDateTime.now();
        if (parameter != null) {
            String startTimeStr = (String) parameter;
            requestStartTime = DateUtil.parseLocalDateTime(startTimeStr);
        }
        serviceResponseResult.setStartTime(requestStartTime);
        serviceResponseResult.setEndTime(LocalDateTime.now());
        gen.writeStartObject();
        gen.writeObject(serviceResponseResult);
        gen.writeEndObject();
    }
}
