package io.github.vino42.domain.dto;

import io.github.vino42.enums.TableStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 0:50
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class UserInfoDto implements Serializable {
    private Long id;

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
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 1男2女0保密
     */
    private Integer sex;

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

    /**
     * 用户状态
     */
    private TableStatusEnum statu;

    private Integer displayStatus;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */
    private Long createBy;

    /**
     * 创建者名称
     */
    private String createName;

    /**
     * 创建者id
     */
    private Long updateBy;

    /**
     * 创建者名称
     */
    private String updateName;

    private Long versionStamp;

    private Set<String> roles;
}
