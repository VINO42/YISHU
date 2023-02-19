package io.github.vino42.redis.lock;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/** @author VINO */
@Slf4j
@AllArgsConstructor
public class LLLRedissonLockImpl implements  LLLRedissonLock {
    
    private RedissonClient redissonClient;
    
    @Override
    public RLock lock(String lockKey, boolean ifFair) {
        if (ifFair) {
            RLock lock = redissonClient.getFairLock(lockKey);
            lock.lock();
            return lock;
        }
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }
    
    @Override
    public RLock lock(String lockKey, long timeout, boolean ifFair) throws InterruptedException {
        if (ifFair) {
            RLock lock = redissonClient.getFairLock(lockKey);
            lock.tryLock(timeout, TimeUnit.SECONDS);
            return lock;
        }
        RLock lock = redissonClient.getLock(lockKey);
        lock.tryLock(timeout, TimeUnit.SECONDS);
        return lock;
    }
    
    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout, boolean ifFair) {
        if (ifFair) {
            RLock lock = redissonClient.getLock(lockKey);
            lock.lock(timeout, unit);
            return lock;
        }
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }
    
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime, boolean ifFair) {
        if (ifFair) {
            RLock lock = redissonClient.getFairLock(lockKey);
            try {
                return lock.tryLock(waitTime, leaseTime, unit);
            } catch (InterruptedException e) {
                return false;
            }
        }
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }
    
    /**
     * https://blog.csdn.net/Yunwei_Zheng/article/details/106480759
     * @param lockKey
     * @return
     */
    @Override
    public boolean unlock(String lockKey, boolean ifFair) {
        if (ifFair) {
            RLock lock = redissonClient.getFairLock(lockKey);
            if (lock != null && !Thread.currentThread().isInterrupted()) {
                if (lock.isLocked()) { // 是否还是锁定状态
                    if (lock.isHeldByCurrentThread()) { // 是否为当前线程的锁
                        lock.unlock(); // 释放锁
                        return true;
                    }
                }
            }
            return false;
        }
        RLock lock = redissonClient.getLock(lockKey);
        if (lock != null && !Thread.currentThread().isInterrupted()) {
            if (lock.isLocked()) { // 是否还是锁定状态
                if (lock.isHeldByCurrentThread()) { // 是否为当前线程的锁
                    lock.unlock(); // 释放锁
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void unlock(RLock lock) {
        if (lock != null && !Thread.currentThread().isInterrupted()) {
            if (lock.isLocked()) { // 是否还是锁定状态
                if (lock.isHeldByCurrentThread()) { // 是否为当前线程的锁
                    lock.unlock(); // 释放锁
                }
            }
        }
    }
}
