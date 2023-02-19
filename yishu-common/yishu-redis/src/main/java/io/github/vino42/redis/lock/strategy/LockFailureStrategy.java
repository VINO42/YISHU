package io.github.vino42.redis.lock.strategy;

import java.lang.reflect.Method;

/**
 * 获取锁失败处理策略
 */
public interface LockFailureStrategy {

    /**
     * 锁失败事件
     */
    void onLockFailure(String key, Method method, Object[] arguments);

}
