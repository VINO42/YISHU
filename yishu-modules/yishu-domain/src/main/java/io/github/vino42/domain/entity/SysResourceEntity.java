package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.vino42.base.YiShuVersion;
import io.github.vino42.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/22 16:19:35
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Copyright : vino
 * @Decription :
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_resource")
public class SysResourceEntity extends AbstractModel<SysResourceEntity> implements Serializable {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源路径
     */
    private String frontPath;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 接口路径
     */
    private String url;

    /**
     * 客户端类型
     */
    private Integer platformType;

    /**
     * 资源类型1接口2前端
     * @see io.github.vino42.base.enums.ResourceTypeEnum
     */
    private Integer resourceType;

    private Integer levelCode;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 前端component
     */
    private String component;

    /**
     * 前端资源图标
     */
    private String icon;

    /**
     * 是否连接前端资源0false 1true
     */
    private Boolean isLink;

    /**
     * 是否隐藏前端资源0false 1true
     */
    private Boolean isHide;

    /**
     * 是否连接前端资源0false 1true
     */
    private Boolean isAffix;

    private Boolean isKeepalive;

    /**
     * 前端跳转路径
     */
    private String redirect;

    /**
     * 前端资源名
     */
    private String title;

    /**
     * 前端资源名
     */
    private Boolean isFull;

}
