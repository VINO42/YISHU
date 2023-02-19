package io.github.vino42.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DataItemStatus implements IEnum<Integer> {

    /**
     * 数据条目已启用
     */
    ENABLE(0, "启用"),
    /**
     * 数据条目被启用
     */
    FORBIDDEN(1, "禁用"),
    /**
     * 数据条目被锁定
     */
    LOCKING(2, "锁定"),
    /**
     * 数据条目已过期
     */
    EXPIRED(3, "过期");

    private final Integer value;
    private final String description;

    private static final Map<Integer, DataItemStatus> INDEX_MAP = new HashMap<>();
    private static final List<Map<String, Object>> JSON_STRUCTURE = new ArrayList<>();

    static {
        for (DataItemStatus dataItemStatus : DataItemStatus.values()) {
            INDEX_MAP.put(dataItemStatus.getValue(), dataItemStatus);
            JSON_STRUCTURE.add(dataItemStatus.getValue(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", dataItemStatus.getValue())
                            .put("key", dataItemStatus.name())
                            .put("text", dataItemStatus.getDescription())
                            .build());
        }
    }

    DataItemStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 不加@JsonValue，转换的时候转换出完整的对象。
     * 加了@JsonValue，只会显示相应的属性的值
     * <p>
     * 不使用@JsonValue @JsonDeserializer类里面要做相应的处理
     *
     * @return Enum枚举值
     */
    @JsonValue
    @Override
    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return this.description;
    }

    public static DataItemStatus get(Integer index) {
        return INDEX_MAP.get(index);
    }

    public static List<Map<String, Object>> getPreprocessedJsonStructure() {
        return JSON_STRUCTURE;
    }
}
