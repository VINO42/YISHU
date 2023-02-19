package io.github.vino42.configuration;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis自动配置
 */
@Configuration
@MapperScan(value = {"io.github.vino42.mapper", "io.github.vino42.domain.mapper", "io.github.vino42.dao"})
@EnableTransactionManagement
public class YiShuMybatisAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(YiShuMybatisAutoConfiguration.class);

    @Bean(name = "plusPaginationInterceptor")
    public PaginationInnerInterceptor paginationInterceptor() {

        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setMaxLimit(200L);
        return paginationInterceptor;
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(paginationInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
//        mybatisPlusInterceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        return mybatisPlusInterceptor;
    }


    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MetaObjectHandlerConfig());
        return globalConfig;
    }


    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        log.trace("[yishu] |- Bean [Block Attack Inner Interceptor] Auto Configure.");
        return blockAttackInnerInterceptor;
    }
}
