

package io.github.vino42.cache;


import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CustomConfigSelector.class)
@EnableMethodCache(basePackages = {"io.github.vino42"})
@EnableCreateCacheAnnotation
public class JetCacheConfiguration {

}

