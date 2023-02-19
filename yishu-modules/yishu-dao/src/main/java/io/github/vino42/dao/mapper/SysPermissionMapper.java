package io.github.vino42.dao.mapper;

import io.github.vino42.domain.entity.SysPermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:16:06
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :  Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermissionEntity> {
    List<String> getPermissionsForUser(@Param("userId") Long userId);
     List<String> getPermissionsByUserGroupForUser(@Param("userGroupIds") HashSet<String> userGroupId);

}
