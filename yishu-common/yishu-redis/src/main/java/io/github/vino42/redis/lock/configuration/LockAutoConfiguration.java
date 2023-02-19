package io.github.vino42.redis.lock.configuration;

import io.github.vino42.redis.configuration.RedissionConfiguration;
import io.github.vino42.redis.lock.*;
import io.github.vino42.redis.lock.aop.LockAnnotationAdvisor;
import io.github.vino42.redis.lock.aop.LockInterceptor;
import io.github.vino42.redis.lock.strategy.DefaultLockFailureStrategy;
import io.github.vino42.redis.lock.strategy.LockFailureStrategy;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动配置器 只支持redisson 暂时不支持redisTemplate
 *
 * @author VINO
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(RedissionConfiguration.class)
public class LockAutoConfiguration {
    //调试中发现有问题 暂时只支持redissonLock
/*    @Bean("redisTemplateLockExecutor")
    @ConditionalOnMissingBean
    @ConditionalOnClass(RedisTemplate.class)
    public LockExecutor lockExecutor(RedisTemplate redisTemplate) {
        
        RedisTemplateLockExecutor redisTemplateLockExecutor = new RedisTemplateLockExecutor();
        redisTemplateLockExecutor.setRedisTemplate(redisTemplate);
        return redisTemplateLockExecutor;
    }*/

    @Bean
    @ConditionalOnMissingBean
    public LLLRedissonLock lllRedissonLock(RedissonClient redissonClient) {

        return new LLLRedissonLockImpl(redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockFailureStrategy lockFailureStrategy() {
        return new DefaultLockFailureStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public LockExecutor lockExecutor(LLLRedissonLock lllRedissonLock) {

        RedissonLockExcutor redissonLockExcutor = new  RedissonLockExcutor();
        redissonLockExcutor.setLllRedissonLock(lllRedissonLock);
        return redissonLockExcutor;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockTemplate lockTemplate(LockExecutor lockExecutor) {
        LockTemplate lockTemplate = new LockTemplate();
        lockTemplate.setLockExecutor(lockExecutor);
        return lockTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockAnnotationAdvisor lockAnnotationAdvisor(LockInterceptor lockInterceptor) {

        LockAnnotationAdvisor lockAnnotationAdvisor = new LockAnnotationAdvisor(lockInterceptor);
        lockAnnotationAdvisor.setOrder(Integer.MAX_VALUE - 10);
        return lockAnnotationAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockInterceptor lockInterceptor(LockTemplate lockTemplate, KeyGenerator lockKeyBuilder,
                                           LockFailureStrategy lockFailureStrategy) {

        LockInterceptor lockInterceptor = new LockInterceptor(lockTemplate, lockKeyBuilder, lockFailureStrategy);
        return lockInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public KeyGenerator keyGenerator() {

        return new LockKeyGenerator();
    }
}
