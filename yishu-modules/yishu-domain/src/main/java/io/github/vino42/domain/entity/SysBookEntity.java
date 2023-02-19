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
 * @Created :   2022/12/31 21:12:10
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 图书基础表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_book_table")
public class SysBookEntity  extends AbstractModel<SysBookEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 豆瓣链接地址
     */
    private String doubanUrl;

    /**
     * 译者 多个用逗号分割
     */
    private String translator;

    /**
     * 作者 多个用逗号分割
     */
    private String author;

    /**
     * isbn码 国际标准书号
     */
    private String isbn;

    /**
     * 出版社
     */
    private String publish;


}
