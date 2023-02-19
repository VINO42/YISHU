package io.github.vino42.auth.filter;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.vino42.auth.service.AuthService;
import io.github.vino42.base.constants.YiShuConstant;
import io.github.vino42.base.context.Authentication;
import io.github.vino42.base.context.CasbinAuthentication;
import io.github.vino42.base.context.YiShuSecurityContextHolder;
import io.github.vino42.base.domain.UserSessionDto;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.threadlocal.ThreadContext;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static io.github.vino42.base.constants.YiShuConstant.ENDPOINT_CONSTANT.ACTUATOR_PREFIX;
import static io.github.vino42.base.constants.YiShuConstant.RequestHeaders.REQUEST_HEADER_TOKEN_HEADER;
import static io.github.vino42.base.constants.YiShuConstant.THREAD_CACHE_KEY.THREAD_LOCAL_REQUEST_START_TIME;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 22:33
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class GlobalFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/wechat/userPublishBookRecord/page","/*/common/**","/wechat/geo","/wechat/login","/wechat/miniapp/**", "/v3/api-docs/**", "/swagger-ui/index.html", "/management/**", "/login", "/logOut", "/regist", "/platform/login",
                    "/platform/logOut", "/platform/regist", "/favicon.ico", "/doc.html", "/webjars/**", "/swagger-resources", "/v2/api-docs", "/test/**")));
    private final AuthService authService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    public GlobalFilter(AuthService authService) {
        this.authService = authService;
    }


    private void assembleRequest(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        String requestId = UUID.randomUUID().toString();
        httpServletRequest.setAttribute(YiShuConstant.RequestHeaders.REQUEST_HEADER_REQUEST_ID, requestId);
        httpServletRequest.setAttribute(YiShuConstant.RequestHeaders.REQUEST_HEADER_REQ_START_TIME_HEADER, startTime);
        response.addHeader(YiShuConstant.RequestHeaders.REQUEST_HEADER_REQUEST_ID, requestId);
        ThreadContext.setParameter(THREAD_LOCAL_REQUEST_START_TIME, String.valueOf(startTime));
        MDC.put(YiShuConstant.MDCConstant.MDC_REQUEST_ID, String.valueOf(requestId));
    }

    private void auth(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = request.getHeader(REQUEST_HEADER_TOKEN_HEADER);
            if (StrUtil.isBlankOrUndefined(token)) {
                ServiceResponseResult<Object> serviceResponseResult = ResultMapper.pleaseLogin();
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(JSONUtil.toJsonStr(serviceResponseResult));
                return;
            }
            UserSessionDto userSesssionDTO = authService.auth(token);
            if (userSesssionDTO == null) {
                //不通过
                ServiceResponseResult<Object> serviceResponseResult = ResultMapper.pleaseLogin();
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(JSONUtil.toJsonStr(serviceResponseResult));
                return;
            }
            String requestURI = request.getRequestURI();
            if (userSesssionDTO.getPerms().contains(requestURI)) {
                // 通过
                Authentication authResult = new CasbinAuthentication(userSesssionDTO);
                YiShuSecurityContextHolder.getContext().setAuthentication(authResult);
                filterChain.doFilter(request, response);
                return;
            } else {
                //不通过
                ServiceResponseResult<Object> serviceResponseResult = ResultMapper.forbidden();
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(JSONUtil.toJsonStr(serviceResponseResult));
            }
        } finally {
            MDC.clear();
            ThreadContext.evictCache();
            YiShuSecurityContextHolder.clearContext();
        }

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        assembleRequest(request, response);

        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", EMPTY);
        if (path.startsWith(ACTUATOR_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        Optional<String> match = ALLOWED_PATHS.stream().filter(d -> antPathMatcher.match(d, path)).findFirst();
        if (match.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        auth(request, response, filterChain);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        MDC.clear();
        ThreadContext.evictCache();
        YiShuSecurityContextHolder.clearContext();
        Filter.super.destroy();
    }
}
