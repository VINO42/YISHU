package io.github.vino42.core.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.HostInfo;
import io.github.vino42.base.constants.YiShuConstant;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static io.github.vino42.base.constants.YiShuConstant.NOT_AVALIABLE;
import static io.github.vino42.base.constants.YiShuConstant.RequestHeaders.REQUEST_HEADER_REQUEST_ID;
import static io.github.vino42.base.constants.YiShuConstant.RequestHeaders.REQUEST_HEADER_TRACE_ID_HEADER;
import static io.github.vino42.base.constants.YiShuConstant.SystemConstant.INSTANCE_ADDRESS;
import static io.github.vino42.base.constants.YiShuConstant.SystemConstant.INSTANCE_NAME;


/**
 * 用来做trace的日志拦截器
 */
@Component
public class LoggerInterceptor implements HandlerInterceptor {


    public LoggerInterceptor() {

        super();
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {

        String traceId = request.getHeader(REQUEST_HEADER_TRACE_ID_HEADER);
        String requestId = request.getHeader(REQUEST_HEADER_REQUEST_ID);
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }

        HostInfo hostInfo = new HostInfo();
        MDC.put(INSTANCE_NAME, hostInfo.getName());
        MDC.put(INSTANCE_ADDRESS, hostInfo.getAddress());
        MDC.put(YiShuConstant.MDCConstant.MDC_REQUEST_ID, requestId);
        MDC.put(YiShuConstant.MDCConstant.MDC_APM_TRACE_ID, StringUtils.isEmpty(traceId) ? NOT_AVALIABLE : traceId);
        response.addHeader(REQUEST_HEADER_TRACE_ID_HEADER, StringUtils.isEmpty(traceId) ? NOT_AVALIABLE : traceId);
        response.addHeader(REQUEST_HEADER_REQUEST_ID, requestId);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
