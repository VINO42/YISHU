package io.github.vino42.base.enums;

/**
 * <p>Description: 基础枚举定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/3/26 16:52
 */
public interface BaseEnum<T> extends EnumValue<T>, EnumDescription {


    static <T extends BaseEnum> T getInstance(Class<T> clazz, int code) {
        T[] constants = clazz.getEnumConstants();

        for (T t : constants) {
            if (t.getValue().equals(code)) {
                return t;
            }
        }
        return null;
    }
}
