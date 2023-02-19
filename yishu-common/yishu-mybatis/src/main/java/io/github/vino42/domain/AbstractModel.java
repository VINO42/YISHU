package io.github.vino42.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.github.vino42.enums.TableStatusEnum;

import java.time.LocalDateTime;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/27 2:08
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class AbstractModel<S extends AbstractModel> extends Model {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;
    /**
     * 状态
     */
    @TableField("statu")
    private TableStatusEnum statu;

    @TableField(exist = false)
    private Integer displayStatus;

    public Integer getDisplayStatus() {
        return statu == null ? null : statu.getStatus();
    }

    public void setDisplayStatus(Integer displayStatus) {
        this.displayStatus = displayStatus;
        this.statu = TableStatusEnum.getByStatus(displayStatus);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableStatusEnum getStatu() {
        return statu;
    }

    public void setStatu(TableStatusEnum statu) {
        this.displayStatus = statu.getStatus();
        this.statu = statu;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getVersionStamp() {
        return versionStamp;
    }

    public void setVersionStamp(Long versionStamp) {
        this.versionStamp = versionStamp;
    }

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;


    /**
     * 创建者名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String createName;


    /**
     * 创建者id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;


    /**
     * 创建者名称
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;


    /**
     * 版本号
     */

    @Version
    private Long versionStamp;
}
