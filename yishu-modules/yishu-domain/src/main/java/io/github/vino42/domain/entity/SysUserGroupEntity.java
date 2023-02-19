package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.vino42.base.YiShuVersion;
import io.github.vino42.domain.AbstractModel;
import io.github.vino42.enums.TableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:30:14
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Copyright : vino
 * @Decription :
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_group")
public class SysUserGroupEntity extends AbstractModel<SysUserGroupEntity> implements Serializable {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;

    /**
     * 组名称
     */
    private String userGroupName;

    /**
     * 组编码
     */
    private String userGroupCode;

}
