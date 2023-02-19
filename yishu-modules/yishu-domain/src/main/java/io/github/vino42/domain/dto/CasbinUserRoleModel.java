package io.github.vino42.domain.dto;

import java.io.Serializable;

import static io.github.vino42.base.constants.YiShuConstant.AUTH_CONSTANT.CASBIN_USER_PREFIX;

/**
 * =====================================================================================
 *
 * @Created :   2021/11/20 19:37
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :  g, alice, data_group_admin
 * =====================================================================================
 */

public class CasbinUserRoleModel implements Serializable {

    private String g = "g";

    private String userId;

    private String userGroup;

    public void setG(String g) {
        this.g = g;
    }

    public void setUserId(String userId) {
        this.userId = CASBIN_USER_PREFIX + userId;
    }

    public String getG() {
        return g;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
