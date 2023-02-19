package io.github.vino42.support.sensitive;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/27 11:32
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright :
 * @Decription :
 * =====================================================================================
 */
@Configuration
public class SensitiveWordsAutoConfiguration {

    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        SensitiveWordBs wordBs = SensitiveWordBs.newInstance()
                .init();
        return wordBs;
    }
}
