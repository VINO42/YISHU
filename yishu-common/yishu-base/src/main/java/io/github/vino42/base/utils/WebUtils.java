package io.github.vino42.base.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.vino42.base.constants.YiShuConstant;
import io.github.vino42.base.json.jackson.JacksonUtils;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.text.StrPool.COMMA;
import static io.github.vino42.base.constants.YiShuConstant.RequestHeaders.*;


/**
 *
 */
@UtilityClass
public class WebUtils extends org.springframework.web.util.WebUtils {

    private final String BASIC_ = "Basic ";

    private final String UNKNOWN = "unknown";

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    public static PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    /**
     * 判断是否ajax请求 spring ajax 返回含有 ResponseBody 或者 RestController注解
     *
     * @param handlerMethod HandlerMethod
     * @return 是否ajax请求
     */
    public boolean isBody(HandlerMethod handlerMethod) {

        ResponseBody responseBody = ClassUtils.getAnnotation(handlerMethod, ResponseBody.class);
        return responseBody != null;
    }

    /**
     * 读取cookie
     *
     * @param name cookie name
     * @return cookie value
     */
    public String getCookieVal(String name) {

        HttpServletRequest request = WebUtils.getRequest();
        Assert.notNull(request, "request from RequestContextHolder is null");
        return getCookieVal(request, name);
    }

    /**
     * 读取cookie
     *
     * @param request HttpServletRequest
     * @param name    cookie name
     * @return cookie value
     */
    public String getCookieVal(HttpServletRequest request, String name) {

        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }


    /**
     * 设置cookie
     *
     * @param response        HttpServletResponse
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds maxage
     */
    public void setCookie(
            HttpServletResponse response, String name, String value, int maxAgeInSeconds) {

        Cookie cookie = new Cookie(name, value);
        cookie.setPath(StrUtil.SLASH);
        cookie.setMaxAge(maxAgeInSeconds);
        response.addCookie(cookie);
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return {HttpServletRequest}
     */
    public HttpServletRequest getRequest() {

        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } catch (IllegalStateException e) {
            return null;
        }
    }

    /**
     * 获取 HttpServletRequest header
     *
     * @return {HttpServletRequest}
     */
    public String getHeader(String headerName) {

        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request.getHeader(headerName);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public String getToken() {
        return  getHeader(REQUEST_HEADER_TOKEN_HEADER);
    }

    public void setAttribute(String attrKey, String attrValue) {

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        request.setAttribute(attrKey, attrValue);
    }

    public String getAttribute(String attrKey) {

        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return Convert.toStr(request.getAttribute(attrKey));
        } catch (Exception e) {
            log.error("[getAttribute:{},error]", attrKey, e);
        }
        return null;
    }

    public void removeAttribute(String attrKey) {

        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            request.removeAttribute(attrKey);
        } catch (Exception e) {
            log.error("[getAttribute:{},error]", attrKey, e);
        }
    }


    /**
     * 返回json
     *
     * @param response    HttpServletResponse
     * @param result      结果对象
     * @param contentType contentType
     */
    public void renderJson(HttpServletResponse response, Object result, String contentType) {

        response.setContentType(contentType);
        try (PrintWriter out = response.getWriter()) {
            out.append(JSONUtil.toJsonStr(result));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取ip
     *
     * @return {String}
     */
    public String getIp() {

        return getIp(WebUtils.getRequest());
    }

    /**
     * 获取ip
     *
     * @param request HttpServletRequest
     * @return {String}
     */
    public String getIp(HttpServletRequest request) {

        Assert.notNull(request, "HttpServletRequest is null");
        String ip = request.getHeader("X-Requested-For");
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StrUtil.isBlank(ip) ? null : ip.split(",")[0];
    }


    /**
     * 读取cookie
     *
     * @param name cookie name
     * @return cookie value
     */
    public static String getCookieValue(String name) {
        HttpServletRequest request = WebUtils.getRequest();
        Assert.notNull(request, "request from RequestContextHolder is null");
        return getCookieValue(request, name);
    }

    /**
     * 读取cookie
     *
     * @param request HttpServletRequest
     * @param name    cookie name
     * @return cookie value
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 清除 某个指定的cookie
     *
     * @param response HttpServletResponse
     * @param key      cookie key
     */
    public static void removeCookie(HttpServletResponse response, String key) {
        setCookie(response, key, null, 0);
    }


    /**
     * 获取 HttpServletResponse
     *
     * @return {HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response HttpServletResponse
     * @param object   需要转换的对象
     */
    public static void renderJson(HttpServletResponse response, Object object) {
        renderJson(response, JacksonUtils.toJson(object), MediaType.APPLICATION_JSON.toString());
    }

    /**
     * 客户端返回字符串
     *
     * @param response HttpServletResponse
     * @param string   需要绘制的信息
     */
    public static void renderJson(HttpServletResponse response, String string, String type) {
        try {
            response.setContentType(type);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(string);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("[YiShu] |- Render response to Json error!");
        }
    }

    /**
     * 获取ip
     *
     * @return {String}
     */
    public String getIP() {
        return getIP(WebUtils.getRequest());
    }

    /**
     * 获取ip
     *
     * @param request HttpServletRequest
     * @return {String}
     */
    public String getIP(HttpServletRequest request) {
        Assert.notNull(request, "HttpServletRequest is null");
        String ip = request.getHeader(com.google.common.net.HttpHeaders.X_FORWARDED_FOR);
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(com.google.common.net.HttpHeaders.X_FORWARDED_FOR);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HTTP_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(X_REAL_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.isBlank(ip) ? null : ip.split(COMMA)[0];
    }

    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse toHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }


    private static boolean isHeaderContainMediaType(ServletRequest request, String headerType, String mediaType) {
        String header = toHttp(request).getHeader(headerType);
        return StringUtils.isNotEmpty(header) && header.contains(mediaType);
    }

    private static boolean isHeaderContainJson(ServletRequest request, String headerType) {
        return isHeaderContainMediaType(request, headerType, MediaType.APPLICATION_JSON_VALUE);
    }

    private static boolean isContentTypeHeaderContainJson(ServletRequest request) {
        return isHeaderContainJson(request, com.google.common.net.HttpHeaders.CONTENT_TYPE);
    }

    private static boolean isAcceptHeaderContainJson(ServletRequest request) {
        return isHeaderContainJson(request, com.google.common.net.HttpHeaders.ACCEPT);
    }

    private static boolean isContainAjaxFlag(ServletRequest request) {
        String xRequestedWith = WebUtils.toHttp(request).getHeader(com.google.common.net.HttpHeaders.X_REQUESTED_WITH);
        return XML_HTTP_REQUEST.equalsIgnoreCase(xRequestedWith);
    }

    public static boolean isAjaxRequest(ServletRequest request) {

        //使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
        if (isContentTypeHeaderContainJson(request) || isAcceptHeaderContainJson(request) || isContainAjaxFlag(request)) {
            log.trace("[YiShu] |- Is Ajax Request!!!!!");
            return true;
        }

        log.trace("[YiShu] |- Not a Ajax Request!!!!!");
        return false;
    }


    /**
     * 判断路径是否与路径模式匹配
     *
     * @param pattern 路径模式
     * @param path    url
     * @return 是否匹配
     */
    public static boolean isPathMatch(String pattern, String path) {
        return getPathMatcher().match(pattern, path);
    }

    /**
     * 判断路径是否与路径模式匹配
     *
     * @param patterns 路径模式数组
     * @param path     url
     * @return 是否匹配
     */
    public static boolean isPathMatch(String[] patterns, String path) {
        return isPathMatch(Arrays.asList(patterns), path);
    }

    /**
     * 判断路径是否与路径模式匹配
     *
     * @param patterns 路径模式字符串List
     * @param path     url
     * @return 是否匹配
     */
    public static boolean isPathMatch(List<String> patterns, String path) {
        PathMatcher pathMatcher = getPathMatcher();
        for (String pattern : patterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从Map中获取url匹配的对象。
     *
     * @param path 传入的url
     * @param map  以路径匹配pattern为key的map
     * @param <T>  返回的对象
     * @return T
     */
    public static <T> T getPathMatchedObject(String path, Map<String, T> map) {
        PathMatcher pathMatcher = getPathMatcher();
        for (String key : map.keySet()) {
            if (pathMatcher.match(key, path)) {
                return map.get(key);
            }
        }

        return null;
    }

    public static String getRequestPayload(ServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader();) {
            char[] buffer = new char[1024];
            int length;
            while ((length = reader.read(buffer)) != -1) {
                stringBuilder.append(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("[YiShu] |- Get Request Payload Error!");
        }

        return stringBuilder.toString();
    }


}
