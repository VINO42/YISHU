package io.github.vino42.domain.dto;

import lombok.Data;

/**
 * =====================================================================================
 *
 * @Created :   2023/1/8 18:47
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 微信小程序登录实体封装
 * =====================================================================================
 */
@Data
public class WechatUserLoginDto {
    /**
     * 微信unionId
     */
    private String unionId;
    /**
     * 微信openId;
     */
    private String openId;
    /**
     * 微信头像
     */
    private String avatar;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;

    /**
     * 昵称
     */
    private String nickName;
}
