package io.github.vino42.logger.lookup;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import static io.github.vino42.base.constants.YiShuConstant.NOT_AVALIABLE;

/**
 * =====================================================================================
 *
 * @Created :   2021/12/16 15:57
 * @Compiler :   jdk 11
 * @Author :   VINO
 * @Email :  VINO
 * @Copyright :   VINO
 * @Description :  扩展log4j2的lookup 支持Spring的env的获取
 * <p>
 * =====================================================================================
 */
@Plugin(
        name = "spring",
        category = StrLookup.CATEGORY
)
public class SpringEnvironmentLookUp extends AbstractLookup implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    private static Environment environment;

    @Override
    public String lookup(LogEvent event, String key) {

        if (environment != null) {
            return environment.getProperty(key);
        } else {
            return NOT_AVALIABLE;
        }
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        environment = applicationContext.getEnvironment();
    }
}
