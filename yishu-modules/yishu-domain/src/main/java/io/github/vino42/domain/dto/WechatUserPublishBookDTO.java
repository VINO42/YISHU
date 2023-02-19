package io.github.vino42.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2023/1/8 17:53
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 微信端发布图书实体封装
 * =====================================================================================
 */
@Data
public class WechatUserPublishBookDTO implements Serializable {


    private Long userId;

    private Long regionId;
    private String bookName;


    private Long categoryId;

    /**
     * 联系方式
     */
    private String contract;

    private List<SysPublishBook> books;
}
