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
 * @Created :   2022/10/21 15:34:32
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Copyright : vino
 * @Decription :
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission_group")
public class SysPermissionGroupEntity extends AbstractModel<SysPermissionGroupEntity> implements Serializable {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;
    ;

    /**
     * 权限组名称
     */
    private String permissionGroupName;

    /**
     * 权限组编码
     */
    private String permissionGroupCode;

}
