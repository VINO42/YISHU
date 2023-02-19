package io.github.vino42.core.jackson;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * jackson配置
 */
@Slf4j
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
    }

    // localDateTime 反序列化器
    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        log.debug("[LLL start config jackson]");
        return builder -> {
            builder.createXmlMapper(false);
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
            builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            builder.modules(new JavaTimeModule());
        };
    }

    /**
     * 配置 JacksonMapper
     *
     * @param builder
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {

        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

        // 是否需要排序
        objectMapper
                .registerModule(new Jdk8Module())
                .setTimeZone(TimeZone.getTimeZone("GMT+8"))
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                //将枚举值转换为字符串 如TableStatusEnum.NORMAL 则为正常
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                // 序列化的时候，过滤null属性
                // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
                // Include.Include.ALWAYS 默认
                // Include.NON_DEFAULT 属性为默认值不序列化
                // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
                // Include.NON_NULL 属性为NULL 不序列化
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // 忽略在json字符串中存在，但在java对象中不存在对应属性的情况，防止错误
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 取消默认转换timestamps形式
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                // 允许整数前导为0,eg:"01"形式
                .configure(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature(), true)
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                // 允许出现单引号
                .configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true)
                // 允许出现特殊字符和转义符
                .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
                // 使用驼峰式
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                // 使用bean名称
                .enable(MapperFeature.USE_STD_BEAN_NAMING)
                // 忽略空bean转json的错误
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.configure(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        objectMapper
                .getSerializerProvider()
                .setNullValueSerializer(
                        new JsonSerializer<Object>() {

                            @Override
                            public void serialize(
                                    Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                                    throws IOException {

                                jsonGenerator.writeString(StrUtil.EMPTY);
                            }
                        });

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
