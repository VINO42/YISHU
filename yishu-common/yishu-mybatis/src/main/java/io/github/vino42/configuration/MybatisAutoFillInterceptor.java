package io.github.vino42.configuration;

import cn.hutool.core.collection.CollectionUtil;
import io.github.vino42.base.utils.SecurityUtils;
import io.github.vino42.domain.AbstractModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: MybatisInterceptor
 * @Description:mybatis拦截器，自动注入创建人、创建时间、修改人、修改时间
 * @Copyright:
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisAutoFillInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            // 获取 SQL
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            // 获取参数
            Object parameter[] = invocation.getArgs();

            if (parameter == null) {
                return invocation.proceed();
            }
            Object object = parameter[1];
            if (SqlCommandType.INSERT.equals(sqlCommandType)) { //判断类型为INSERT
                if (object instanceof AbstractModel) {
                    handleInsert(object);
                    return invocation.proceed();
                }
                if (object instanceof MapperMethod.ParamMap) {
                    Object oj = ((MapperMethod.ParamMap) object).get("param1");
                    handleInsert(oj);
                }
                return invocation.proceed();

            }
            if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                if (object instanceof AbstractModel) {
                    handleUpdate(object);
                    return invocation.proceed();
                }
                if (object instanceof MapperMethod.ParamMap) {
                    Object oj = ((MapperMethod.ParamMap) object).get("param1");

                    handleUpdate(oj);
                }
                return invocation.proceed();
            }
            return invocation.proceed();
        }

    }

    private void handleUpdate(Object object) {
        if (object instanceof AbstractModel) {
            ((AbstractModel) object).setUpdateBy(SecurityUtils.getUserId());
            ((AbstractModel) object).setUpdateName(SecurityUtils.getRealName());
            ((AbstractModel) object).setUpdateTime(LocalDateTime.now());
//            ((AbstractModel) object).setVersionStamp(System.currentTimeMillis());
        }
    }

    private void handleInsert(Object object) {
        if (object instanceof MapperMethod.ParamMap) {
            List<?> list = (List<?>) ((MapperMethod.ParamMap) object).get("list");
            if (CollectionUtil.isNotEmpty(list)) {
                list.forEach(item -> inserFill(item));
            }
        } else {
            inserFill(object);
        }
    }

    private void inserFill(Object object) {
        if (object instanceof AbstractModel) {
            ((AbstractModel) object).setCreateBy(SecurityUtils.getUserId());
            ((AbstractModel) object).setUpdateBy(SecurityUtils.getUserId());
            ((AbstractModel) object).setCreateName(SecurityUtils.getRealName());
            ((AbstractModel) object).setUpdateName(SecurityUtils.getRealName());
            ((AbstractModel) object).setUpdateTime(LocalDateTime.now());
            ((AbstractModel) object).setCreateTime(LocalDateTime.now());
            ((AbstractModel) object).setVersionStamp(System.currentTimeMillis());
        }
    }

    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}