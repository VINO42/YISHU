package io.github.vino42.logger.configuration;

import io.github.vino42.logger.filter.RequestLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * =====================================================================================
 *
 * @Created :   2023/1/1 16:47
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Slf4j
@Configuration
public class YishuLogConfiguration {
    @Bean
    public FilterRegistrationBean requestLogFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new RequestLogFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.setName("requestLogFilter");
        log.info("[YISHU- init RequestLogFilter]");
        return registration;
    }
}
