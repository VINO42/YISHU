

package io.github.vino42.cache;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Import;

@EnableMethodCache(basePackages = {"io.github.vino42"})
@Import(CustomConfigSelector.class)
public @interface EnableYiShuMehodCache {
}
