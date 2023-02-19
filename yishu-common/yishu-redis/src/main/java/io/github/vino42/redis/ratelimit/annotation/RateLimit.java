package io.github.vino42.redis.ratelimit.annotation;


import io.github.vino42.redis.ratelimit.config.LimitType;

import java.lang.annotation.*;

/**
 * =====================================================================================
 *
 * @Created :   2021/3/1 16:05
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :
 * @Copyright :
 * @Description : 
 *
 * =====================================================================================
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RateLimit {
    // 资源 key
    String key() default "";
    
    // key prefix
    String prefix() default "";
    
    // 时间的，单位秒
    int period();
    
    // 限制访问次数
    int limitCount() default 1;
    
    // 限制类型
    LimitType limitType() default LimitType.CUSTOMER;
    
    /**
     * 提示信息，可自定义
     * @return String
     */
    String info() default "请稍后重试";
}
