package io.github.vino42.redis.idemopotent.expression;

import io.github.vino42.redis.idemopotent.annotation.Idempotent;
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
 * <p>
 * =====================================================================================
 */
public interface KeyResolver {

    /**
     * 解析处理 key
     *
     * @param idempotent 接口注解标识
     * @param point      接口切点信息
     * @return 处理结果
     */
    String resolver(Idempotent idempotent, JoinPoint point);

}
