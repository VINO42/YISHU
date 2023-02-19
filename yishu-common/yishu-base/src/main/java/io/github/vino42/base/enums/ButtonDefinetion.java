package io.github.vino42.base.enums;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/30 18:23
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public enum ButtonDefinetion implements BaseEnum<Integer> {
    add(1, "添加按钮"),
    batchAdd(2, "批量添加按钮"),
    export(3, "导出"),
    batchDelete(4, "批量导出"),
    status(5, "修改状态");

    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ButtonDefinetion(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取枚举文字表述
     *
     * @return 枚举文字表述
     */
    @Override
    public String getDescription() {
        return desc;
    }

    /**
     * 获取枚举自定义值
     *
     * @return 自定义枚举值
     */
    @Override
    public Integer getValue() {
        return code;
    }

    public static boolean ifContains(String name) {
        ButtonDefinetion buttonDefinetion = ButtonDefinetion.valueOf(name);
        if (buttonDefinetion == null) {
            return false;
        }
        return true;
    }
}
