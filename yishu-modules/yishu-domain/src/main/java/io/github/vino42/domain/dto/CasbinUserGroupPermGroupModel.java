package io.github.vino42.domain.dto;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 16:23
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription : p, data_group_admin, data_group, write
 * =====================================================================================
 */
public class CasbinUserGroupPermGroupModel implements Serializable {

    private String pType = "p";

    private String userGroup;

    private String permissionGroup;

    private String permission;
}
