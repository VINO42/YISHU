package io.github.vino42.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public abstract class DefaultSysEntity extends BaseSysEntity {

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
