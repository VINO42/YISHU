package io.github.vino42.redis.lock.aop;

import io.github.vino42.redis.lock.KeyGenerator;
import io.github.vino42.redis.lock.LockInfo;
import io.github.vino42.redis.lock.LockTemplate;
import io.github.vino42.redis.lock.annotation.DistributeLock;
import io.github.vino42.redis.lock.strategy.LockFailureStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 分布式锁aop处理器
 *
 * @author VINO
 */
@Slf4j
@RequiredArgsConstructor
public class LockInterceptor implements MethodInterceptor {
    
    private final LockTemplate lockTemplate;
    
    private final KeyGenerator lockKeyGenerator;
    
    private final LockFailureStrategy lockFailureStrategy;
    
    
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        
        LockInfo lockInfo = null;
        DistributeLock distributeLock = invocation.getMethod().getAnnotation(DistributeLock.class);
        try {
            String keyName = lockKeyGenerator.getKeyName(invocation, distributeLock);
            lockInfo = lockTemplate.lock(keyName, distributeLock.expire(), distributeLock.timeout(), distributeLock.ifFairLock(), distributeLock.timeUnit());
            if (null != lockInfo) {
                return invocation.proceed();
            }
            log.warn("[try to lock failed],lockInfo:{}", distributeLock.keys());
            lockFailureStrategy.onLockFailure(keyName, invocation.getMethod(), invocation.getArguments());
            return null;
        } finally {
            
            if (null != lockInfo) {
                lockTemplate.releaseLock(lockInfo, distributeLock.ifFairLock());
            }
        }
    }
}
