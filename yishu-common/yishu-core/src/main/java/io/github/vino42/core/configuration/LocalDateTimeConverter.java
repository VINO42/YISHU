package io.github.vino42.core.configuration;

import cn.hutool.core.date.DatePattern;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeConverter {

    /**
     * 因为 Spring 默认不支持将 String 类型的请求参数转换为 LocalDateTime 类型，所以我们需要自定义 converter 「转换器」完整整个转换过程
     */
    public static class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String s) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN, Locale.CHINESE);
            return LocalDateTime.parse(s, dateTimeFormatter);
        }
    }
}

