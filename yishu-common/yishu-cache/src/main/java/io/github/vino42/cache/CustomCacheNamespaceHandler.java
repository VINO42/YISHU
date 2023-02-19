

package io.github.vino42.cache;

import com.alicp.jetcache.anno.config.CacheAnnotationParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CustomCacheNamespaceHandler extends NamespaceHandlerSupport {
    
    @Override
    public void init() {
        registerBeanDefinitionParser("annotation-driven", new CacheAnnotationParser());
    }
}
