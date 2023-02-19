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
 * @Decription :
 * =====================================================================================
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AccountTypeEnum implements BaseEnum<Integer> {
    ADMIN(1, "后台管理账号类型"),
    WECHAT(2, "微信小程序账号类型");
    private Integer code;

    private String description;

    AccountTypeEnum(Integer code, String description) {
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
    public static List<AccountTypeEnum> getAccountTypeEnum() {
        List<AccountTypeEnum> accountTypeEnums = Arrays.asList(AccountTypeEnum.values());
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
