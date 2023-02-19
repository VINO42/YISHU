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
 * @Created :   2022/10/16 23:02:43
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 账号表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_account")
public class SysAccountEntity extends AbstractModel<SysAccountEntity> implements Entity {

    private static final long serialVersionUID = YiShuVersion.SERIAL_VERSION_UID;
    ;


    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 微信唯一id
     */
    private String unionId;

    private String openId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户id
     */
    private Long userId;


    private Integer accountType;

}
