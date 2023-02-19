package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:16:01
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :  Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    List<ResAllocateListDto> getByUserId(@Param("userId") String userId);

    List<String> getRolesForUser(@Param("userId") Long userId);

    List<ResAllocateListDto> getByGroupId(@Param("groupId") String groupId);

    SysRoleEntity getRoleByCode(@Param("roleCode") String roleCode);

    List<String> getRolesByUserGroupForUser(@Param("userGroupId") Long userGroupId);


}
