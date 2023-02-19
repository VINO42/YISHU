package io.github.vino42.cache;

import com.alicp.jetcache.anno.aop.CacheAdvisor;
import com.alicp.jetcache.anno.aop.JetCacheInterceptor;
import com.alicp.jetcache.anno.support.ConfigMap;
import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import static io.github.vino42.cache.JetCacheConstant.CACHE_ADVISOR_BEAN_NAME;


public class CustomCacheAnnotationParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        doParse(element, parserContext);
        return null;
    }
    
    private void doParse(Element element, ParserContext parserContext) {
        String[] basePackages = StringUtils.tokenizeToStringArray(element.getAttribute("base-package"), ",; \t\n");
        AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);
        if (!parserContext.getRegistry().containsBeanDefinition(CACHE_ADVISOR_BEAN_NAME)) {
            Object eleSource = parserContext.extractSource(element);
            
            RootBeanDefinition configMapDef = new RootBeanDefinition(ConfigMap.class);
            configMapDef.setSource(eleSource);
            configMapDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            String configMapName = parserContext.getReaderContext().registerWithGeneratedName(configMapDef);
            
            RootBeanDefinition interceptorDef = new RootBeanDefinition(JetCacheInterceptor.class);
            interceptorDef.setSource(eleSource);
            interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            interceptorDef.getPropertyValues().addPropertyValue(new PropertyValue("cacheConfigMap", new RuntimeBeanReference(configMapName)));
            String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);
            
            RootBeanDefinition advisorDef = new RootBeanDefinition(CacheAdvisor.class);
            advisorDef.setSource(eleSource);
            advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            advisorDef.getPropertyValues().addPropertyValue(new PropertyValue("adviceBeanName", interceptorName));
            advisorDef.getPropertyValues().addPropertyValue(new PropertyValue("cacheConfigMap", new RuntimeBeanReference(configMapName)));
            advisorDef.getPropertyValues().addPropertyValue(new PropertyValue("basePackages", basePackages));
            parserContext.getRegistry().registerBeanDefinition(CACHE_ADVISOR_BEAN_NAME, advisorDef);
            
            CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(),
                    eleSource);
            compositeDef.addNestedComponent(new BeanComponentDefinition(configMapDef, configMapName));
            compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
            compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, CACHE_ADVISOR_BEAN_NAME));
            parserContext.registerComponent(compositeDef);
        }
    }
}
