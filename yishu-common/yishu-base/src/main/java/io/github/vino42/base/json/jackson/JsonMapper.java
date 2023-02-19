package io.github.vino42.base.json.jackson;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.netty.util.concurrent.FastThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JsonMapper {
    /**
     * 属性为 空（“”） 或者为 NULL 都不序列化
     */
    private static final FastThreadLocal<JsonMapper> mFastThreadLocalNnEmptyMapper =
            new FastThreadLocal<JsonMapper>() {
                @Override
                protected JsonMapper initialValue() throws Exception {
                    return new JsonMapper(Include.NON_EMPTY);
                }
            };
    /**
     * 默认
     */
    private static final FastThreadLocal<JsonMapper> mFastThreadLocalAlwaysMapper =
            new FastThreadLocal<JsonMapper>() {
                @Override
                protected JsonMapper initialValue() throws Exception {
                    return new JsonMapper(Include.ALWAYS);
                }
            };
    /**
     * 属性为默认值不序列化
     */
    private static final FastThreadLocal<JsonMapper> mFastThreadLocalNonDefaultMapper =
            new FastThreadLocal<JsonMapper>() {
                @Override
                protected JsonMapper initialValue() throws Exception {
                    return new JsonMapper(Include.NON_DEFAULT);
                }
            };
    private Logger logger = LoggerFactory.getLogger(JsonMapper.class);
    private static ObjectMapper mapper;

    public JsonMapper() {
        this(null);
    }

    public JsonMapper(Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }

        mapper.registerModule(new Jdk8Module());
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        mapper.registerModule(new JavaTimeModule());
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 取消默认转换timestamps形式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 允许不带引号的字段名称
        mapper.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
        // 允许单引号
        mapper.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
        // allow int startWith 0
        mapper.configure(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature(), true);
        // 允许字符串存在转义字符：\r \n \t
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 排除空值字段
        mapper.setSerializationInclusion(Include.NON_NULL);
        // 使用驼峰式
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        // 使用bean名称
        mapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
        // 所有日期格式都统一为固定格式
        mapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 字段保留，将null值转为""
        mapper
                .getSerializerProvider()
                .setNullValueSerializer(
                        new JsonSerializer<Object>() {

                            @Override
                            public void serialize(
                                    Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                                    throws IOException {

                                jsonGenerator.writeString("");
                            }
                        });
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static JsonMapper nonEmptyMapper() {
        return mFastThreadLocalNnEmptyMapper.get();
    }

    /**
     * 创建只输出可为Null或者Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     *
     * @return
     */
    public static JsonMapper alwaysMapper() {
        return mFastThreadLocalAlwaysMapper.get();
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static JsonMapper nonDefaultMapper() {
        return mFastThreadLocalNonDefaultMapper.get();
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object, boolean format) {
        if (object == null) {
            return "";
        }
        if (object instanceof Number) {
            return object.toString();
        }
        if (object instanceof String) {
            return (String) object;
        }
        try {
            if (format) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            }
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }

    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
     *
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
     *
     * @see # createCollectionType(Class, Class...)
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * Converts a JsonNode to a Java value
     *
     * @param <A>   the type of the return value.
     * @param json  Json value to convert.
     * @param clazz Expected Java value type.
     * @return the return value.
     */
    public <A> A fromJson(JsonNode json, Class<A> clazz) {
        try {
            return mapper.treeToValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造Collection类型.
     */
    public JavaType contructCollectionType(Class<? extends Collection> collectionClass,
                                           Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型.
     */
    public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass,
                                    Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    public void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum, 為False時時使用Enum的name()函數來讀寫Enum, 默認為False. 注意本函數一定要在Mapper創建後,
     * 所有的讀寫動作之前調用.
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Creates a new empty ObjectNode.
     *
     * @return new empty ObjectNode.
     */
    public ObjectNode newObject() {
        return getMapper().createObjectNode();
    }

    /**
     * Creates a new empty ArrayNode.
     *
     * @return a new empty ArrayNode.
     */
    public ArrayNode newArray() {
        return getMapper().createArrayNode();
    }

    /**
     * Parses a String representing a json, and return it as a JsonNode.
     *
     * @param src the JSON string.
     * @return the JSON node.
     */
    public JsonNode parse(String src) {
        if (StringUtils.isBlank(src)) {
            return null;
        }
        try {
            return getMapper().readTree(src);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * 对象转换为json字符串
     *
     * @param o 要转换的对象
     */
    public String toJson(Object o) {
        return toJson(o, false);
    }


    /**
     * 字符串转换为指定对象
     *
     * @param json json字符串
     * @param cls  目标对象
     */
    public <T> T toObject(String json, Class<T> cls) {
        if (StringUtils.isBlank(json) || cls == null) {
            return null;
        }
        try {
            return mapper.readValue(json, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为指定对象，并增加泛型转义
     * 例如：List<Integer> test = toObject(jsonStr, List.class, Integer.class);
     *
     * @param json             json字符串
     * @param parametrized     目标对象
     * @param parameterClasses 泛型对象
     */
    public <T> T toObject(String json, Class<?> parametrized, Class<?>... parameterClasses) {
        if (StringUtils.isBlank(json) || parametrized == null) {
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转换为指定对象
     *
     * @param json          json字符串
     * @param typeReference 目标对象类型
     */
    public <T> T toObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(json) || typeReference == null) {
            return null;
        }
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 对象转换为map对象
     *
     * @param o 要转换的对象
     */
    public <K, V> Map<K, V> toMap(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return toObject((String) o, Map.class);
        }
        return mapper.convertValue(o, Map.class);
    }

    /**
     * json字符串转换为list对象
     *
     * @param json json字符串
     */
    public <T> List<T> toList(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json字符串转换为list对象，并指定元素类型
     *
     * @param json json字符串
     * @param cls  list的元素类型
     */
    public <T> List<T> toList(String json, Class<T> cls) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, cls);
            return mapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
