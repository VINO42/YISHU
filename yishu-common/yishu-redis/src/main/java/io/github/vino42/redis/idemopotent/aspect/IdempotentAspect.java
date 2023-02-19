package io.github.vino42.redis.idemopotent.aspect;

import cn.hutool.core.text.StrPool;
import io.github.vino42.redis.exceptions.IdempotencyException;
import io.github.vino42.redis.idemopotent.annotation.Idempotent;
import io.github.vino42.redis.idemopotent.expression.KeyResolver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.DUMPLICATE_REQUEST_LIMIT;


/**
 * =====================================================================================
 *
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :
 * @Copyright :
 * @Description : The Idempotent Aspect
 * <p>
 * =====================================================================================
 */
@Aspect
public class IdempotentAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdempotentAspect.class);
    private static final String RMAPCACHE_KEY = "idempotent";
    private static final String KEY = "key";
    private static final String DELKEY = "delKey";
    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal();
    @Autowired
    private Redisson redisson;

    @Autowired
    private KeyResolver keyResolver;

    @Pointcut("@annotation(io.github.vino42.redis.idemopotent.annotation.Idempotent)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void beforePointCut(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return;
        }
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        String key;

        // 若没有配置 幂等 标识编号，则使用 url + 参数列表作为区分
        if (StringUtils.isEmpty(idempotent.key())) {
            String url = request.getRequestURL().toString();
            String argString = Arrays.asList(joinPoint.getArgs()).toString();
            key = url + argString;
        } else {
            // 使用jstl 规则区分
            key = keyResolver.resolver(idempotent, joinPoint);
        }
        String name = idempotent.name();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(name) && !name.endsWith(StrPool.COLON)) {
            name = name + StrPool.COLON;
        }
        key = name + key;
        long expireTime = idempotent.expireTime();
        String info = idempotent.info();
        TimeUnit timeUnit = idempotent.timeUnit();
        boolean delKey = idempotent.delKey();

        // do not need check null
        RMapCache<String, Object> rMapCache = redisson.getMapCache(RMAPCACHE_KEY);
        String value = LocalDateTime.now().toString().replace("T", " ");
        Object v1;
        if (null != rMapCache.get(key)) {
            // had stored
            throw new IdempotencyException(DUMPLICATE_REQUEST_LIMIT.status, info);
        }
        synchronized (this) {
            v1 = rMapCache.putIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
            if (null != v1) {
                throw new IdempotencyException(DUMPLICATE_REQUEST_LIMIT.status, info);
            } else {
                LOGGER.info("[idempotent]:has stored key={},value={},expireTime={}{},now={}", key, value, expireTime,
                        timeUnit, LocalDateTime.now().toString());
            }
        }

        Map<String, Object> map = CollectionUtils.isEmpty(threadLocal.get()) ? new HashMap<>(4) : threadLocal.get();
        map.put(KEY, key);
        map.put(DELKEY, delKey);
        threadLocal.set(map);

    }

    @After("pointCut()")
    public void afterPointCut(JoinPoint joinPoint) {
        Map<String, Object> map = threadLocal.get();
        if (CollectionUtils.isEmpty(map)) {
            return;
        }

        RMapCache<Object, Object> mapCache = redisson.getMapCache(RMAPCACHE_KEY);
        if (mapCache.size() == 0) {
            return;
        }

        String key = map.get(KEY).toString();
        boolean delKey = (boolean) map.get(DELKEY);

        if (delKey) {
            mapCache.fastRemove(key);
            LOGGER.info("[idempotent]:has removed key={}", key);
        }
        threadLocal.remove();
    }

}
