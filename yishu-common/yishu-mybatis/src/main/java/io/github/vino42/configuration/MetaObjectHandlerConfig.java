package io.github.vino42.configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.vino42.base.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;


/**
 * 填充字段 更新填充更新时间字段 插入填充更新时间和生成时间
 */
//@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", Long.class, SecurityUtils.getUserId());
        this.strictInsertFill(metaObject, "createName", String.class, SecurityUtils.getRealName());
        this.strictInsertFill(metaObject, "updateBy", Long.class, SecurityUtils.getUserId());
        this.strictInsertFill(metaObject, "updateName", String.class, SecurityUtils.getRealName());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateName", String.class, SecurityUtils.getRealName());
        this.strictInsertFill(metaObject, "updateBy", Long.class, SecurityUtils.getUserId());
    }


}
