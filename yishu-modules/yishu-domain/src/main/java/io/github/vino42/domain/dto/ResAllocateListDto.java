package io.github.vino42.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2022/11/14 21:11
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 用户组返回
 * =====================================================================================
 */
@Data
public class ResAllocateListDto implements Serializable {

    private String id;

    private String name;
}
