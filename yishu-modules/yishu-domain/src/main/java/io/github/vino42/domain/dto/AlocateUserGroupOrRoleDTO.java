package io.github.vino42.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/11/20 21:58
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class AlocateUserGroupOrRoleDTO implements Serializable {
    private List<String> ids;

    private Long userId;
}
