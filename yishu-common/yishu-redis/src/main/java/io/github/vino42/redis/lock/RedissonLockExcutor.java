package io.github.vino42.redis.lock;
/**
 * 分布式锁原生Redisson处理器
 */

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author VINO
 */
@Slf4j
public class RedissonLockExcutor implements LockExecutor {
    
    @Setter
    private LLLRedissonLock lllRedissonLock;
    
    @Override
    public boolean acquire(String lockKey, String lockValue, long acquireExpire, long waiteTime, boolean ifFairLock, TimeUnit timeUnit) {
        
        boolean lock = lllRedissonLock.tryLock(lockKey, timeUnit, waiteTime, acquireExpire, ifFairLock);
        log.debug("lock {} locked", lock);
        return lock;
    }
    
    @Override
    public boolean releaseLock(LockInfo lockInfo, boolean ifFairLock) {
        
        log.debug("lock {} unlocked", lockInfo.getLockKey());
        if (lockInfo != null) {
            return lllRedissonLock.unlock(lockInfo.getLockKey(), ifFairLock);
        } else {
            return Boolean.TRUE;
        }
        
    }
}
