package io.github.vino42.redis.idemopotent.config;

import io.github.vino42.redis.configuration.RedissionConfiguration;
import io.github.vino42.redis.idemopotent.aspect.IdempotentAspect;
import io.github.vino42.redis.idemopotent.expression.ExpressionResolver;
import io.github.vino42.redis.idemopotent.expression.KeyResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * =====================================================================================
 *
 * @Created :   2020/9/29 17:56
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :
 * @Copyright :
 * @Description : 幂等自动装配类
 * <p>
 * =====================================================================================
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedissionConfiguration.class)
@EnableAspectJAutoProxy
public class IdempotentAutoConfiguration {

    /**
     * 切面 拦截处理所有 @Idempotent
     *
     * @return Aspect
     */
    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }

    /**
     * key 解析器
     *
     * @return KeyResolver
     */
    @Bean
    @ConditionalOnMissingBean(KeyResolver.class)
    public KeyResolver keyResolver() {
        return new ExpressionResolver();
    }

}
