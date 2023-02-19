package io.github.vino42.base.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * =====================================================================================
 *
 * @Created :   2022/11/8 20:02
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public enum ResourcePlatformTypeEnum implements EnumValue<Integer>, EnumDescription {
    PLATFORM(1, "平台资源类型"),
    MINIAPP(2, "小程序资源类型");

    public int type;
    public String desc;

    ResourcePlatformTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    /**
     * 获取枚举自定义值
     *
     * @return 自定义枚举值
     */
    @Override
    public Integer getValue() {
        return this.type;
    }

    /**
     * 获取枚举文字表述
     *
     * @return 枚举文字表述
     */
    @Override
    public String getDescription() {
        return this.desc;
    }

    public static ResourcePlatformTypeEnum getByValue(Integer type) {
        ResourcePlatformTypeEnum[] values = ResourcePlatformTypeEnum.values();
        Optional<ResourcePlatformTypeEnum> first = Arrays.asList(values).stream().filter(d -> d.getValue() == type).findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            return null;
        }
    }
}
