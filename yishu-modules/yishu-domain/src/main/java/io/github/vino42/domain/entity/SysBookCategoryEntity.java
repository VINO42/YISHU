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
 * @Created :   2022/12/31 21:10:44
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 图书分类表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_book_category_table")
public class SysBookCategoryEntity  extends AbstractModel<SysBookCategoryEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图书类别名称
     */
    private String categoryName;

    /**
     * 图书类别编码
     */
    private String categoryCode;

    /**
     * 中图分类法编码
     */
    private String clsCode;

    /**
     * 父级类别编码
     */
    private String parentCode;

}
