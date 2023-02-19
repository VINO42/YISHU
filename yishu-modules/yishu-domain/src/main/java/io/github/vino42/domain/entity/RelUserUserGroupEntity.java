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
 * @Created :   2022/10/21 15:30:41
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Copyright : vino
 * @Decription :
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rel_user_user_group")
public class RelUserUserGroupEntity extends AbstractModel<RelUserUserGroupEntity> implements Serializable {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;


    private Long userId;

    private Long userGroupId;

}
