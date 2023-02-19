package io.github.vino42.web;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableMethodCache(basePackages = "io.github.vino42")
@SpringBootApplication(scanBasePackages = "io.github.vino42")
@MapperScan(basePackages = "io.github.vino42.dao.mapper")
public class YishuWebApplication {

    public static void main(String[] args) {
        System.setProperty("log.home", "./logs");
        SpringApplication.run(YishuWebApplication.class, args);
    }

}
