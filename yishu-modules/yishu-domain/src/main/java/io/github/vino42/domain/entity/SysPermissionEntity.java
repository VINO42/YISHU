package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.vino42.base.YiShuVersion;
import io.github.vino42.base.domain.Entity;
import io.github.vino42.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:02:40
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermissionEntity extends AbstractModel<SysPermissionEntity> implements Entity {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;
    ;


    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String perm;

}
