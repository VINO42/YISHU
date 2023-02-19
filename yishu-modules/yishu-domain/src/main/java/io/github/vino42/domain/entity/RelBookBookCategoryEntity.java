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
 * @Created :   2022/12/31 21:12:04
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 图书与图书分类关系映射表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rel_book_book_category")
public class RelBookBookCategoryEntity extends AbstractModel<RelBookBookCategoryEntity> implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 图书主键
     */
    private Long bookId;

    /**
     * 图书分类主键
     */
    private Long categoryId;


}
