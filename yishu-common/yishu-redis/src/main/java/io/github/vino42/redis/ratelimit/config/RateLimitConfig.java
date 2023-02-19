package io.github.vino42.redis.ratelimit.config;

import io.github.vino42.redis.ratelimit.expression.LimitExpressionResolver;
import io.github.vino42.redis.ratelimit.expression.LimitKeyResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * =====================================================================================
 *
 * @Created :   2021/3/1 17:23
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :
 * @Copyright :
 * @Description : 
 *
 * =====================================================================================
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RateLimitConfig {
    
    /**
     * key 解析器
     * @return KeyResolver
     */
    @Bean
    @ConditionalOnMissingBean(LimitKeyResolver.class)
    public LimitKeyResolver limitKeyResolver() {
        return new LimitExpressionResolver();
    }
}
