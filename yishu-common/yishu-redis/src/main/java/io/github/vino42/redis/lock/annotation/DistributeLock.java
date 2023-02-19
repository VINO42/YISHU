package io.github.vino42.redis.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/** @author VINO */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DistributeLock {
    
    /** KEY 默认包名+方法名 */
    String[] keys() default "";
    
    /**
     * 过期时间 单位：秒
     *
     * <pre>
     *     过期时间一定是要长于业务的执行时间.
     * </pre>
     */
    long expire() default 1000;
    
    /**
     * 获取锁超时时间 单位：毫秒
     *
     * <pre>
     *     结合业务,建议该时间不宜设置过长,特别在并发高的情况下.
     * </pre>
     */
    long timeout() default 10;
    
    /**
     * 是否为公平锁 默认为非公平的RedissonLock 如果为true 那么使用 getFairLock方法
     */
    boolean ifFairLock() default false;
    
    /**
     * 时间单位 expire 失效时间 默认为毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
    
}
