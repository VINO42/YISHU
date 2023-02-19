package io.github.vino42.redis.lock;

import io.github.vino42.base.response.ServiceResponseCodeEnum;
import io.github.vino42.redis.exceptions.LockedException;
import io.github.vino42.redis.lock.util.LockUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * 锁模板方法
 */
@Slf4j
public class LockTemplate {
    
    private static final String PROCESS_ID = LockUtil.getLocalMac() + LockUtil.getJvmPid();
    @Setter
    private LockExecutor lockExecutor;
    
    public LockInfo lock(String key, long expire, long timeout, boolean ifFairLock, TimeUnit timeUnit) {
        Assert.isTrue(timeout > 0, "tryTimeout must more than 0");
        
        long start = System.currentTimeMillis();
        int acquireCount = 0;
        /** 定义LockValue */
        String value = PROCESS_ID + Thread.currentThread().getId();
        try {
            while (System.currentTimeMillis() - start < timeout) {
                boolean result = lockExecutor.acquire(key, value, expire, timeout, ifFairLock, timeUnit);
                acquireCount++;
                if (result) {
                    return new  LockInfo(key, value, expire, timeout, acquireCount, timeUnit);
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            log.error("lock error", e);
            throw new LockedException(ServiceResponseCodeEnum.GET_RESOURCE_LOCK_FAILED_429002);
        }
        log.info("get lock failed,cause timeout, try {} times", acquireCount);
        return null;
    }
    
    public boolean releaseLock( LockInfo lockInfo, boolean ifFairLock) {
        if (lockInfo != null) {
            return lockExecutor.releaseLock(lockInfo, ifFairLock);
        } else {
            return Boolean.TRUE;
        }
    }
}
