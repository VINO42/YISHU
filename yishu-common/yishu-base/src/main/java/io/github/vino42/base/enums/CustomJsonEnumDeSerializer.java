package io.github.vino42.base.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

public class CustomJsonEnumDeSerializer extends JsonDeserializer<BaseEnum> {

    /**
     * 反序列化的处理
     */
    @Override
    public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonStreamContext parent = jp.getParsingContext().getParent();
        Object currentValue = parent.getCurrentValue();
        String currentName = parent.getCurrentName();
        JsonNode node = jp.getCodec().readTree(jp);
        Class clazz = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        return BaseEnum.getInstance(clazz, node.get("value").asInt());
    }
}