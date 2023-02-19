package io.github.vino42.redis.idemopotent.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * =====================================================================================
 *
 * @Created :   2020/9/29 17:29
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :
 * @Copyright :
 * @Description : 幂等注解
 *
 * =====================================================================================
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * 幂等操作的唯一标识 前缀 如 lll:sms:idempotent:
     * @return Spring-EL expression
     */
    String name() default "";
    
    /**
     * 幂等操作的唯一标识，使用spring el表达式 用#来引用方法参数
     * @return Spring-EL expression
     */
    String key();
    
    /**
     * 在有效期内限制次数
     * @return
     */
    int limit() default 1;
    
    /**
     * 有效期 默认：1 有效期要大于程序执行时间，否则请求还是可能会进来
     * @return expireTime
     */
    int expireTime() default 2;
    
    /**
     * 时间单位 默认：s
     * @return TimeUnit
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 提示信息，可自定义
     *
     * @return String
     */
    String info() default "";
    
    /**
     * 是否在业务完成后删除key true:删除 false:不删除
     * @return boolean
     */
    boolean delKey() default false;
}
