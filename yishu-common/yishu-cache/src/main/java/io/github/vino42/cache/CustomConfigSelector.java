

package io.github.vino42.cache;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

import java.util.ArrayList;
import java.util.List;

public class CustomConfigSelector extends AdviceModeImportSelector<EnableMethodCache> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            case ASPECTJ:
//                return getAspectJImports();
            default:
                return null;
        }
    }

    private String[] getProxyImports() {
        List<String> result = new ArrayList<String>();
        result.add(CustomJetCacheConfiguration.class.getName());
        return result.toArray(new String[result.size()]);
    }
}
