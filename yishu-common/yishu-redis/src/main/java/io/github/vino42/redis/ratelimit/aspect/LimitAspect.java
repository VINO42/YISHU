package io.github.vino42.redis.ratelimit.aspect;

import io.github.vino42.redis.configuration.RedisService;
import io.github.vino42.redis.exceptions.RateLimitedException;
import io.github.vino42.redis.ratelimit.annotation.RateLimit;
import io.github.vino42.redis.ratelimit.config.LimitType;
import io.github.vino42.redis.ratelimit.expression.LimitKeyResolver;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static cn.hutool.core.text.StrPool.*;
import static io.github.vino42.base.response.ServiceResponseCodeEnum.REQUEST_RAIT_LIMIT;

@Aspect
public class LimitAspect {

    private static final String UNKNOWN = "unknown";
    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);
    @Autowired
    RedisService redisService;
    @Autowired
    LimitKeyResolver limitKeyResolver;

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String localhost = "127.0.0.1";
        if (ip.contains(COMMA)) {
            ip = ip.split(COMMA)[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    @Pointcut("@annotation(io.github.vino42.redis.ratelimit.annotation.RateLimit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object beforePointCut(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = getIp(request);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        signatureMethod.getAnnotations();
        RateLimit limit = signatureMethod.getAnnotation(RateLimit.class);
        LimitType limitType = limit.limitType();
        String key = limitKeyResolver.resolver(limit, joinPoint);
        if (StringUtils.isEmpty(key)) {
            if (limitType == LimitType.IP) {
                key = getIp(request);
            } else {
                key = signatureMethod.getName();
            }
        }
        String rateKey = StringUtils.join(limit.prefix(), COLON, key, request.getRequestURI().replaceAll(SLASH, COLON));

        RRateLimiter rateLimite = redisService.getRateLimite(rateKey);
        try {
            rateLimite.trySetRate(RateType.OVERALL, limit.limitCount(), limit.period(), RateIntervalUnit.SECONDS);
            boolean ifLimited = rateLimite.tryAcquire(1);
            if (ifLimited) {
                return joinPoint.proceed();
            } else {
                logger.info("访问受限制 requestURI:{},ip:{}", request.getRequestURI(), ip);
                rateLimite.expire(limit.period() * 2, TimeUnit.SECONDS);
                throw new RateLimitedException(REQUEST_RAIT_LIMIT.status, limit.info());
            }
        } finally {
            rateLimite.expire(limit.period() * 2, TimeUnit.SECONDS);
        }
    }

}
