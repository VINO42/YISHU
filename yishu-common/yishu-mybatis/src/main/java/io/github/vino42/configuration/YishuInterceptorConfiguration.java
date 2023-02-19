package io.github.vino42.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/11/7 23:39
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Configuration
@Slf4j
//@AutoConfigureAfter(YiShuMybatisAutoConfiguration.class)
public class YishuInterceptorConfiguration implements ApplicationRunner {
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        MybatisAutoFillInterceptor mybatisInterceptor = new MybatisAutoFillInterceptor();

        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            //自己添加
            configuration.addInterceptor(mybatisInterceptor);
        }
        log.debug("[yishu] |- SDK [ Mybatis Plus] Auto Configure.");
    }
}
