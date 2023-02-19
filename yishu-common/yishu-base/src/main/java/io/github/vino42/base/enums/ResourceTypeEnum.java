package io.github.vino42.base.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 20:29
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 资源类型 资源类型1接口2菜单3.按钮
 * =====================================================================================
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceTypeEnum implements BaseEnum<Integer> {
    INTERFACE(1, "接口资源"),
    MENU(2, "菜单资源"),
    BUTTON(3, "按钮资源");
    private Integer code;

    private String description;

    ResourceTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取枚举文字表述
     *
     * @return 枚举文字表述
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 获取枚举自定义值
     *
     * @return 自定义枚举值
     */
    @Override
    public Integer getValue() {
        return this.code;
    }
    public static List<ResourceTypeEnum> getResourceTypeEnum() {
        List<ResourceTypeEnum> accountTypeEnums = Arrays.asList(ResourceTypeEnum.values());
        return accountTypeEnums;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
