package io.github.vino42.base.http;



import io.github.vino42.base.utils.StringBuilderHolder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * =====================================================================================
 *
 * @Filename :   StringMap.java
 * @Description :   StringMap
 * @Version :   3.0
 * @Created :   2017年10月26日 14:16:49
 * @Compiler :   jdk 1.8
 * =====================================================================================
 */
public final class StringMap {
    /**
     * 针对于Content-type：application/json,text,html...等请求
     */
    private final static String STRING_TYPE_PREFIX = "prefix_";
    private Map<String, Object> map;

    public StringMap() {
        this(new HashMap<String, Object>());
    }

    public StringMap(Map<String, Object> map) {
        this.map = map;
    }

    public StringMap put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public StringMap putNotEmpty(String key, String value) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            map.put(key, value);
        }
        return this;
    }

    public StringMap putNotNull(String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
        return this;
    }

    public StringMap putWhen(String key, Object val, boolean when) {
        if (when) {
            map.put(key, val);
        }
        return this;
    }

    public StringMap putAll(Map<String, Object> map) {
        this.map.putAll(map);
        return this;
    }

    public StringMap putAll(StringMap map) {
        this.map.putAll(map.map);
        return this;
    }

    public void forEach(Consumer imp) {
        for (Map.Entry<String, Object> i : map.entrySet()) {
            imp.accept(i.getKey(), i.getValue());
        }
    }

    public int size() {
        return map.size();
    }

    public Map<String, Object> map() {
        return this.map;
    }

    public Object get(String key) {
        return map.get(key);
    }

    public String formString() {
        final StringBuilder b = new StringBuilder();
        forEach(new Consumer() {
            private boolean notStart = false;

            @Override
            public void accept(String key, Object value) {
                if (notStart) {
                    b.append("&");
                }
                try {
                    b.append(URLEncoder.encode(key, "UTF-8")).append('=')
                            .append(URLEncoder.encode(value.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError(e);
                }
                notStart = true;
            }
        });
        return b.toString();
    }

    public String toRawString() {
        StringBuilder rawString = StringBuilderHolder.getGlobal();
        rawString.append("{");
        map.forEach((k, v) -> {
            String value = v.toString();
            boolean typeStrFlag = value.contains(STRING_TYPE_PREFIX);
            rawString.append("\"")
                    .append(k)
                    .append("\"")
                    .append(":")
                    .append(typeStrFlag ? "\"" : "")
                    .append(typeStrFlag ? value.substring(STRING_TYPE_PREFIX.length()) : v)
                    .append(typeStrFlag ? "\"" : "")
                    .append(",");
        });
        rawString.deleteCharAt(rawString.toString().length() - 1);
        rawString.append("}");
        return rawString.toString();
    }

    public interface Consumer {
        void accept(String key, Object value);
    }
}

