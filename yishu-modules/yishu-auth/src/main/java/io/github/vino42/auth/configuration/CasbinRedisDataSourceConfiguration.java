package io.github.vino42.auth.configuration;

import io.github.vino42.auth.filter.GlobalFilter;
import io.github.vino42.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
//@AutoConfigureBefore(CasbinAutoConfiguration.class)
public class CasbinRedisDataSourceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CasbinRedisDataSourceConfiguration.class);

/*    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public Adapter redisAdapter(StringRedisTemplate stringRedisTemplate) {
        RedisAdapter redisAdapter = new RedisAdapter(stringRedisTemplate);
        LOGGER.info("[YISHU- init RedisAdapter]");
        return redisAdapter;
    }*/

/*    @Bean
    @ConditionalOnMissingBean
    public Enforcer enforcer(CasbinProperties properties, Adapter adapter) {
        Model model = new Model();
        try {
            String modelContext = properties.getModelContext();
            model.loadModelFromText(modelContext);
        } catch (CasbinModelConfigNotFoundException e) {
            // if the local model file address is not set or the file is not found in the default path, the default rbac configuration is used
            if (!properties.isUseDefaultModelIfModelNotSetting()) {
                throw e;
            }
            LOGGER.info("Can't found model config file, use default model config");
            // request definition
            model.addDef("r", "r", "sub, obj, act");
            // policy definition
            model.addDef("p", "p", "sub, obj, act");
            // role definition
            model.addDef("g", "g", "_, _");
            // policy effect
            model.addDef("e", "e", "some(where (p.eft == allow))");
            // matchers
            model.addDef("m", "m", "g(r.sub, p.sub) && r.obj == p.obj && r.act == p.act");
        }
        Enforcer enforcer;
        if (properties.isUseSyncedEnforcer()) {
            enforcer = new SyncedEnforcer(model, adapter);
            LOGGER.info("Casbin use SyncedEnforcer");
        } else {
            enforcer = new Enforcer(model, adapter);
        }
        enforcer.enableAutoSave(properties.isAutoSave());
        return enforcer;
    }*/

    @Bean
    public FilterRegistrationBean globalFilterRegistration(AuthService authService) {
        FilterRegistrationBean registration = new FilterRegistrationBean(new GlobalFilter(authService));
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.setName("globalFilter");
        LOGGER.info("[YISHU- init GlobalFilter]");
        return registration;
    }
}
