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
 * @Created :   2022/10/21 15:34:41
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Copyright : vino
 * @Decription :
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rel_user_group_role")
public class RelUserGroupRoleEntity extends AbstractModel<RelUserGroupRoleEntity> implements Serializable {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;
    ;


    /**
     * 组id
     */
    private Long userGroupId;

    /**
     * 角色id
     */
    private Long roleId;


}
