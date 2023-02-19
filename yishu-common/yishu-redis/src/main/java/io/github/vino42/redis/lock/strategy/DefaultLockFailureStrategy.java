package io.github.vino42.redis.lock.strategy;

import io.github.vino42.redis.exceptions.LockedFailureException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.GET_RESOURCE_LOCK_FAILED_429002;


/**
 * =====================================================================================
 *
 * @Created :   2021/6/26 12:27
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email :
 * @Copyright :
 * @Decription : 默认的获取锁失败处理策略
 * =====================================================================================
 */
@Slf4j
public class DefaultLockFailureStrategy implements LockFailureStrategy {


    @Override
    public void onLockFailure(String key, Method method, Object[] arguments) {
        throw new LockedFailureException(GET_RESOURCE_LOCK_FAILED_429002);
    }
}
