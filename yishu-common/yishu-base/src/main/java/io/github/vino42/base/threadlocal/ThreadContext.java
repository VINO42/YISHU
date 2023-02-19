package io.github.vino42.base.threadlocal;


import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {


    private static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new MyThreadLocal();

    private static Map<String, Object> getContextMap() {
        return THREAD_CONTEXT.get();
    }


    public static void setParameter(String key, Object obj) {
        getContextMap().put(key, obj);
    }

    public static Object getParameter(String key) {
        return getContextMap().getOrDefault(key, null);

    }

    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    public static void evictCache() {
        getContextMap().clear();

    }

    private static class MyThreadLocal extends TransmittableThreadLocal<Map<String, Object>> {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>(8) {
                private static final long serialVersionUID = 3637958959138295593L;

                @Override
                public Object put(String key, Object value) {
                    return super.put(key, value);
                }

                @Override
                public void clear() {
                    super.clear();
                }

                @Override
                public Object get(Object key) {
                    return super.get(key);
                }
            };
        }
    }
}
