package io.github.vino42.logger.filter;

import cn.hutool.core.util.StrUtil;
import io.github.vino42.base.constants.YiShuConstant;
import io.github.vino42.base.logger.RequestLogModel;
import io.github.vino42.base.threadlocal.ThreadContext;
import io.github.vino42.base.utils.IpUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static io.github.vino42.base.constants.YiShuConstant.MDCConstant.MDC_REQUEST_ID;
import static io.github.vino42.base.constants.YiShuConstant.NOT_AVALIABLE;
import static io.github.vino42.base.constants.YiShuConstant.RequestHeaders.*;
import static io.github.vino42.base.constants.YiShuConstant.THREAD_CACHE_KEY.THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN;


@Slf4j
public class RequestLogFilter extends AbstractRequestLoggingFilter {


    @SneakyThrows
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String requestHeader = request.getHeader(REQUEST_HEADER_REQUEST_ID);
            if (StringUtils.isNotBlank(requestHeader)) {
                MDC.put(MDC_REQUEST_ID, requestHeader);
                MDC.put(YiShuConstant.MDCConstant.MDC_REQUEST_ID, requestHeader);
            }
            RequestLogModel reqLog = assembleRequestLog(request);
            request.getSession().setAttribute(THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN, reqLog);
            if (request.getMethod().equals(HttpMethod.GET.name())) {
                reqLog.setQueryParams(request.getQueryString());
                reqLog.setBody(NOT_AVALIABLE);
            }
            if (request.getMethod().equals(HttpMethod.POST.name())) {
                reqLog.setQueryParams(NOT_AVALIABLE);
            }
            ThreadContext.setParameter(THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN, reqLog);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_REQUEST_ID);
            MDC.remove(YiShuConstant.MDCConstant.MDC_REQUEST_ID);
            ThreadContext.remove(THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN);
            request.getSession().removeAttribute(THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN);
        }

    }


    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
    }


    private RequestLogModel assembleRequestLog(HttpServletRequest req) {
        RequestLogModel requestLogModel = new RequestLogModel();


        String userAgent = req.getHeader(HttpHeaders.USER_AGENT);
        String clientType = req.getHeader(REQUEST_HEADER_CLIENT_TYPE_HEADER);
        String isEncoded = req.getHeader(REQUEST_HEADER_IS_ENCODED_HEADER);
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlankOrUndefined(authHeader)) {
            authHeader = req.getHeader(REQUEST_HEADER_SERVICE_TOKEN_HEADER);
        }

        String userId = req.getHeader(REQUEST_HEADER_USER_ID_HEADER);
        String userMobile = req.getHeader(REQUEST_HEADER_USER_MOBILE_HEADER);


        requestLogModel.setStatus(NOT_AVALIABLE);
        requestLogModel.setMethod(req.getMethod());
        requestLogModel.setUri(req.getRequestURI());
        requestLogModel.setProtocol(req.getProtocol());
        requestLogModel.setPort(req.getLocalPort());
        requestLogModel.setAddress(IpUtil.getIp(req));
        requestLogModel.setUserId(StrUtil.isBlankOrUndefined(userId) ? NOT_AVALIABLE : userId);
        requestLogModel.setMobile(StrUtil.isBlankOrUndefined(userMobile) ? NOT_AVALIABLE : userMobile);
        requestLogModel.setClientType(StringUtils.isBlank(clientType) ? NOT_AVALIABLE : clientType);
        requestLogModel.setUa(StringUtils.isBlank(userAgent) ? NOT_AVALIABLE : userAgent);
        requestLogModel.setIsEncoded(StringUtils.isBlank(isEncoded) ? NOT_AVALIABLE : isEncoded);
        requestLogModel.setToken(StringUtils.isBlank(authHeader) ? NOT_AVALIABLE : authHeader);
        return requestLogModel;
    }

    /**
     * =====================================================================================
     *
     * @Created :   2021/8/11 14:07
     * @Compiler :   jdk 11
     * @Author :   VINO
     * @Email :
     * @Copyright :
     * @Description : 过滤器请求包裹类  防止流读取一次后，Controller无法获取流数据
     * <p>
     * =====================================================================================
     */
    class FilterRequestWrapper extends HttpServletRequestWrapper {

        private String body;

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request the {@link HttpServletRequest} to be wrapped.
         * @throws IllegalArgumentException if the request is null
         */
        FilterRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);

            copyBody(request);
        }

        private void copyBody(HttpServletRequest request) throws IOException {
            // 读取数据
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = br.read(charBuffer)) > 0) {
                sb.append(charBuffer, 0, bytesRead);
            }
            body = sb.toString();
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return bais.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
        }

        @Override
        public String getQueryString() {
            return super.getQueryString();
        }

        public String getBody() {
            return this.body;
        }

    }
}
