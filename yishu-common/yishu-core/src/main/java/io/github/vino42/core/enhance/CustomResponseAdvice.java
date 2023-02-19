package io.github.vino42.core.enhance;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import io.github.vino42.base.constants.YiShuConstant;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.threadlocal.ThreadContext;
import io.github.vino42.base.utils.WebUtils;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;

import static io.github.vino42.base.constants.YiShuConstant.RequestHeaders.REQUEST_HEADER_REQ_START_TIME_HEADER;
import static io.github.vino42.base.constants.YiShuConstant.THREAD_CACHE_KEY.THREAD_LOCAL_REQUEST_START_TIME;


/**
 * 自定义统一响应处理
 */
@RestControllerAdvice(basePackages = "io.github.vino42")
public class CustomResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object o,
            MethodParameter methodParameter,
            MediaType mediaType,
            Class aClass,
            ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse) {

        try {
            if (o instanceof ServiceResponseResult) {
                ServiceResponseResult resultWrapper = (ServiceResponseResult) o;
                Object parameter = ThreadContext.getParameter(THREAD_LOCAL_REQUEST_START_TIME);
                LocalDateTime localDateTime;
                if (parameter != null) {
                    String parameter1 = (String) parameter;
                    localDateTime = LocalDateTimeUtil.of(Long.valueOf(parameter1));
                } else {
                    String header = WebUtils.getAttribute(REQUEST_HEADER_REQ_START_TIME_HEADER);
                    if (StrUtil.isNotBlank(header)) {
                        localDateTime = DateUtil.parseLocalDateTime(header);
                    } else {
                        localDateTime = LocalDateTime.now();
                    }
                }
                resultWrapper.setStartTime(localDateTime);
                resultWrapper.setEndTime(LocalDateTime.now());
                String requestId = MDC.get(YiShuConstant.MDCConstant.MDC_REQUEST_ID);
                resultWrapper.setRequestId(requestId);
                return resultWrapper;
            }
            return o;
        } finally {
            ThreadContext.evictCache();
            MDC.clear();
           WebUtils.removeAttribute(REQUEST_HEADER_REQ_START_TIME_HEADER);
        }
    }
}
