package io.github.vino42.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.github.vino42.domain.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:35
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 系统行政区地理信息表
 * =====================================================================================
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_region_geo")
public class SysRegionGeoEntity extends AbstractModel<SysRegionGeoEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级id
     */
    private Long pid;

    /**
     * 层级深度
     */
    private String deep;

    /**
     * 名称
     */
    private String name;

    /**
     * 区域全称
     */
    private String extPath;

    /**
     * geo数据中心点
     */
    private String geo;

    /**
     * GeoGraphics 图元
     */
    private String polygon;

    /**
     * 拼音
     */
    private String pinyin;


}
