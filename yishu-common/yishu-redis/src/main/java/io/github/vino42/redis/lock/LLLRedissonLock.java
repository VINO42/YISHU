package io.github.vino42.redis.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson的Lock接口 这里就不提供统一的Lock父接口
 *
 * @author VINO
 */
public interface LLLRedissonLock {
    
    RLock lock(String lockKey, boolean ifFair);
    
    RLock lock(String lockKey, long timeout, boolean ifFair) throws InterruptedException;
    
    RLock lock(String lockKey, TimeUnit unit, long timeout, boolean ifFair);
    
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime, boolean ifFair);
    
    boolean unlock(String lockKey, boolean ifFair);
    
    void unlock(RLock lock);
}
