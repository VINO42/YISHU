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
 * @Decription : 系统用户组 1 后台管理 2微信用户组
 * =====================================================================================
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SysUserGroupEnum implements BaseEnum<Integer> {

    ADMIN("后台管理用户组", 1),
    WECHAT("微信小程序用户组", 2);

    private String desc;

    private Integer id;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc= desc;;
    }

    public Integer getId() {
        return id;
    }

    public void setid(Integer id) {
        this.id = id;
    }

    SysUserGroupEnum(String desc, Integer id) {
        this.desc = desc;
        this.id = id;
    }

    public static List<SysUserGroupEnum> getGroup() {
        List<SysUserGroupEnum> genderEnums = Arrays.asList(SysUserGroupEnum.values());
        return genderEnums;
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

    /**
     * 获取枚举自定义值
     *
     * @return 自定义枚举值
     */
    @Override
    public Integer getValue() {
        return id;
    }
}

