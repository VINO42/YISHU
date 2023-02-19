package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.entity.RelRolePermissionGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:42:42
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface RelRolePermissionGroupMapper extends BaseMapper<RelRolePermissionGroupEntity> {
    void removeRolePermissionGroupByRoleId(@Param("roleId") String roleId);

    List<RelRolePermissionGroupEntity> getByGroupIds(@Param("groupIds") List<Long> ids);

}
