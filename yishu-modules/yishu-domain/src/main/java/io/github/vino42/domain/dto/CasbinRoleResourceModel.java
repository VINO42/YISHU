package io.github.vino42.domain.dto;

import java.io.Serializable;


/**
 * =====================================================================================
 *
 * @Created :   2021/11/20 19:37
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */

public class CasbinRoleResourceModel implements Serializable {


    private String g = "g2";

    private String resourcePath;

    private String permissionGroup;

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }
}
