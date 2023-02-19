package io.github.vino42.logger.log;

import cn.hutool.json.JSONUtil;
import io.github.vino42.base.constants.YiShuConstant;
import io.github.vino42.base.logger.RequestLogModel;
import io.github.vino42.base.threadlocal.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequestWrapper;

import static io.github.vino42.base.constants.YiShuConstant.Log4jConstant.LogConstant.REQUEST_LOG_FORMAT_PATTERN;
import static io.github.vino42.base.constants.YiShuConstant.NOT_AVALIABLE;
import static io.github.vino42.base.constants.YiShuConstant.THREAD_CACHE_KEY.THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN;


/**
 * =====================================================================================
 *
 * @Created :   2021/9/9 11:52
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :
 * @Copyright :
 * @Description :
 * <p>
 * =====================================================================================
 */
@Slf4j
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    //切点入口 controller包下面所有类的所有方法
    private final String pointCut =
            " execution(* io.github.vino42.web..*(..))";

    @Pointcut(value = pointCut)
    public void point() {
    }

    @Around(value = "point()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object result;
            StringBuilder sb = new StringBuilder();
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object o : args) {
                //class org.springframework.security.web.servletapi.HttpServlet3RequestFactory$Servlet3SecurityContextHolderAwareRequestWrapper
                if (o instanceof HttpServletRequestWrapper) {
                    HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) o;
                    String method = httpServletRequestWrapper.getMethod();
                    if (method.equals(HttpMethod.POST.name())) {
                        continue;
                    }
                    if (method.equals(HttpMethod.GET.name())) {
                        break;
                    }
                    continue;
                }
                if (ObjectUtils.isNotEmpty(o) && StringUtils.isNotBlank(o.toString()) && !"null".equals(o)) {
                    sb.append(String.format("%s ", JSONUtil.toJsonStr(o).equals("{}") ? o.toString() : JSONUtil.toJsonStr(o)));
                }
            }
            long startTime = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long endTime = System.currentTimeMillis();
            Object requestLogFormatPattern = ThreadContext.getParameter(THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN);
            if (requestLogFormatPattern != null) {
                RequestLogModel reqLog = (RequestLogModel) requestLogFormatPattern;
                reqLog.setBody(StringUtils.isBlank(sb.toString()) ? NOT_AVALIABLE : sb.toString());
                reqLog.setDuration(endTime - startTime);
                reqLog.setResult(JSONUtil.toJsonStr(result));
                LOGGER.info(REQUEST_LOG_FORMAT_PATTERN,
                        MDC.get(YiShuConstant.MDCConstant.MDC_REQUEST_ID),
                        reqLog.getAddress(),
                        reqLog.getUa(),
                        reqLog.getToken(),
                        reqLog.getUserId(),
                        reqLog.getMobile(),
                        reqLog.getZonedDateTime(),
                        reqLog.getMethod(),
                        reqLog.getUri(),
                        reqLog.getProtocol(),
                        StringUtils.isAnyEmpty(reqLog.getQueryParams()) ? NOT_AVALIABLE : reqLog.getQueryParams(),
                        reqLog.getBody(),
                        reqLog.getResult());
            }
            return result;
        } finally {
            ThreadContext.remove(THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN);
        }
    }
}
