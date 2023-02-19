package io.github.vino42.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 20:54
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Data
public class BookDeatilsByIsbnDto implements Serializable {
    /**
     * 图书名
     */
    private String title;
    /**
     * 子名称
     */
    private String subtitle;
    /**
     *
     */
    private String original_title;
    /**
     * 豆瓣id
     */
    private String id;
    /**
     * isbn
     */
    private String isbn;
    /**
     * 图书作者
     */
    private List<String> author;
    /**
     * 译者
     */
    private List<String> translator;
    /**
     * 出版社
     */
    private String publish;
    /**
     *
     */
    private String producer;
    /**
     * 出版日期
     */
    private String publishDate;
    /**
     * 页数
     */
    private String pages;
    /**
     * 价格
     */
    private String price;
    /**
     * 分类
     */
    private String series;
    /**
     * 图书描述
     */
    private String book_intro;
    /**
     * 译者描述
     */
    private String author_intro;
    /**
     * 分类
     */
    private List<String> catalog;
    /**
     *
     */
    private List<String> original_texts;
    /**
     *
     */
    private List<String> labels;
    /**
     * 封面图
     */
    private String cover_url;
    /**
     * 豆瓣地址
     */
    private String url;
    /**
     * 中图书分类法编码
     */
    private String clcCode;


}
