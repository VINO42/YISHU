package io.github.vino42.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.vino42.base.enums.BaseEnum;
import io.github.vino42.base.enums.CustomJsonEnumDeSerializer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 22:53
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = CustomJsonEnumDeSerializer.class)
public enum TableStatusEnum implements IEnum<Integer>, BaseEnum<Integer> {
    /**
     * 数据正常状态
     */
    NORMAL(1, "正常"),
    /**
     * 数据禁用状态
     */
    BLOCKED(0, "禁用"),
    /**
     * 数据删除状态
     */
    DELETED(2, "删除"),
    /**
     * 数据冻结状态
     */
    FROZEN(3, "冻结");

    @EnumValue
    public int status;

    public String desc;


    TableStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }


    public static List<TableStatusEnum> getTableStatus() {
        List<TableStatusEnum> genderEnums = Arrays.asList(TableStatusEnum.values());
        return genderEnums;
    }

    public static TableStatusEnum getByStatus(Integer status) {
        List<TableStatusEnum> genderEnums = Arrays.asList(TableStatusEnum.values());
        Optional<TableStatusEnum> first = genderEnums.stream().filter(d -> d.getStatus() == status).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
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
     * 枚举数据库存储值
     */
    @Override
    public Integer getValue() {
        return this.status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
