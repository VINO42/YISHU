package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.vino42.base.YiShuVersion;
import io.github.vino42.base.domain.Entity;
import io.github.vino42.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:01:51
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 用户表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUserEntity extends AbstractModel<SysUserEntity> implements Entity {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;
    ;


    /**
     * 用户编号
     */
    private String code;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String mobile;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 1男2女0保密
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 证件
     */
    private String idCard;

    /**
     * 证件类型
     */
    private String idCardType;

    /**
     * 联系地址
     */
    private String addr;

    private Integer provinceCode;

    private String cityCode;

    private String regionCode;

    private String province;

    private String city;

    private String region;

}
