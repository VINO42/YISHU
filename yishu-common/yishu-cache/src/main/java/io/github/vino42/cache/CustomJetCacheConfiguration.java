

package io.github.vino42.cache;

import com.alicp.jetcache.anno.aop.CacheAdvisor;
import com.alicp.jetcache.anno.aop.JetCacheInterceptor;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
public class CustomJetCacheConfiguration implements ImportAware, ApplicationContextAware {
    protected AnnotationAttributes enableMethodCache;
    private ApplicationContext applicationContext;
    
    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableMethodCache = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableMethodCache.class.getName(), false));
        if (this.enableMethodCache == null) {
            throw new IllegalArgumentException(
                    "@EnableMethodCache is not present on importing class " + importMetadata.getClassName());
        }
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean(name = "jetcache2.customInternalCacheAdvisor")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Primary
    public CacheAdvisor jetcacheAdvisor(JetCacheInterceptor jetCacheInterceptor) {
        CacheAdvisor advisor = new CacheAdvisor();
        advisor.setAdviceBeanName("jetcache2.customInternalCacheAdvisor");
        advisor.setAdvice(jetCacheInterceptor);
        advisor.setBasePackages(this.enableMethodCache.getStringArray("basePackages"));
        advisor.setOrder(this.enableMethodCache.<Integer>getNumber("order"));
        return advisor;
    }
}
