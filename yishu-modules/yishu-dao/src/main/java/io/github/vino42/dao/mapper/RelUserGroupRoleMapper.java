package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.entity.RelUserGroupRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:42:38
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface RelUserGroupRoleMapper extends BaseMapper<RelUserGroupRoleEntity> {
    int removeUserGroupRoleIdByUserId(@Param("groupId") Long groupId);

    List<String> selectUserIdsByUserGroup(@Param("groupId") Long groupId);

     List<RelUserGroupRoleEntity> selectByRoleIds(@Param("roleIds")List<Long> roleIds);
}
