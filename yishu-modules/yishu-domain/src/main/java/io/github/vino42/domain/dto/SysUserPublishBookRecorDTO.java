package io.github.vino42.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.vino42.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:12:16
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 用户发布图书记录表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserPublishBookRecorDTO extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 微信唯一id
     */
    private String unionId;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 图书名称
     */
    private String title;

    /**
     * 简介描述
     */
    private String bookIntro;
    /**
     * 封面图
     */
    private String pic;
    /**
     * isbn码 国际标准书号
     */
    private String isbn;
    /**
     * 联系方式
     */
    private String contract;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 图书主键
     */
    private Long bookId;

    /**
     * 图书发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime publishDate;

    /**
     * 几天前
     */
    private String dayBefor;

    /**
     * 地区标识
     */
    private Long regionId;

    /**
     * 地区名称
     */
    private String regionName;
}
