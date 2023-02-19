package io.github.vino42.redis.lock.aop;

import io.github.vino42.redis.lock.annotation.DistributeLock;
import lombok.NonNull;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 分布式锁aop通知
 *
 * @author VINO
 */
public class LockAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    
    private Advice advice;
    
    private Pointcut pointcut;
    
    public LockAnnotationAdvisor(@NonNull LockInterceptor lockInterceptor) {
        
        this.advice = lockInterceptor;
        this.pointcut = buildPointcut();
    }
    
    @Override
    public Pointcut getPointcut() {
        
        return this.pointcut;
    }
    
    @Override
    public Advice getAdvice() {
        
        return this.advice;
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
    
    private Pointcut buildPointcut() {
        
        return AnnotationMatchingPointcut.forMethodAnnotation(DistributeLock.class);
    }
}
