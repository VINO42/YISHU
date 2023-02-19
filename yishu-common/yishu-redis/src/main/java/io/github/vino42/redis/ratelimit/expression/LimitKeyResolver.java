package io.github.vino42.redis.ratelimit.expression;

import io.github.vino42.redis.ratelimit.annotation.RateLimit;
import org.aspectj.lang.JoinPoint;

/**
 * =====================================================================================
 *
 * @Created :   2020/9/30 11:41
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :
 * @Copyright :
 * @Description : 唯一标志处理器
 *
 * =====================================================================================
 */
public interface LimitKeyResolver {
    
    /**
     * 解析处理 key
     * @param idempotent 接口注解标识
     * @param point 接口切点信息
     * @return 处理结果
     */
    String resolver(RateLimit idempotent, JoinPoint point);
    
}
