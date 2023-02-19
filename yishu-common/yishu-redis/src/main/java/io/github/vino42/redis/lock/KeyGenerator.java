package io.github.vino42.redis.lock;

import io.github.vino42.redis.lock.annotation.DistributeLock;
import org.aopalliance.intercept.MethodInvocation;

/**
 * =====================================================================================
 *
 * @Created :   2021/6/26 12:21
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription : 锁的key生成器
 * =====================================================================================
 */
public interface KeyGenerator {

    String getKeyName(MethodInvocation invocation, DistributeLock rlock);
}