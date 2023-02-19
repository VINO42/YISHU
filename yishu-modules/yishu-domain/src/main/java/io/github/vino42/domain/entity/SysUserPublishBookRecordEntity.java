package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.github.vino42.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
@TableName("sys_user_publish_book_record_table")
public class SysUserPublishBookRecordEntity  extends AbstractModel<SysUserPublishBookRecordEntity> implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户主键
     */
    private Long userId;

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
    private LocalDateTime publishDate;


    /**
     * 地区标识
     */
    private Long regionId;

}
