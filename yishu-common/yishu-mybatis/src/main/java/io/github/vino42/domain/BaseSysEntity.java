package io.github.vino42.domain;


import com.baomidou.mybatisplus.annotation.TableField;

public abstract class BaseSysEntity extends BaseEntity {
    /**
     * 数据状态
     */
    @TableField(value = "status")
    private DataItemStatus status = DataItemStatus.ENABLE;
    /**
     * 版本号
     */
    @TableField(value = "dataVersion")
    private Long dataVersion = 0L;

    /**
     * 角色描述,UI界面显示使用
     */
    @TableField(value = "description")
    private String description;

    public DataItemStatus getStatus() {
        return status;
    }

    public void setStatus(DataItemStatus status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }
}
