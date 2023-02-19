package io.github.vino42.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * =====================================================================================
 *
 * @Created :   2023/1/28 0:09
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class SysPublishBook implements Serializable {

    private String isbn;
    /**
     * 备注信息
     */
    private String remark;
}
