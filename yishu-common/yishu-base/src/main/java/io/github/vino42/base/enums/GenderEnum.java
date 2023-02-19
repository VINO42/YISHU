package io.github.vino42.base.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/23 0:12
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum implements BaseEnum<Integer> {

    UNKOWN("未知", 0),
    MAN("男", 1),
    WOMAN("女", 2);

    private String genderLabel;

    private Integer genderValue;

    public String getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(String genderLabel) {
        this.genderLabel = genderLabel;
    }

    public Integer getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(Integer genderValue) {
        this.genderValue = genderValue;
    }

    GenderEnum(String genderLabel, Integer genderValue) {
        this.genderLabel = genderLabel;
        this.genderValue = genderValue;
    }

    public static List<GenderEnum> getGender() {
        List<GenderEnum> genderEnums = Arrays.asList(GenderEnum.values());
        return genderEnums;
    }


    /**
     * 获取枚举文字表述
     *
     * @return 枚举文字表述
     */
    @Override
    public String getDescription() {
        return this.genderLabel;
    }

    /**
     * 获取枚举自定义值
     *
     * @return 自定义枚举值
     */
    @Override
    public Integer getValue() {
        return genderValue;
    }
}

