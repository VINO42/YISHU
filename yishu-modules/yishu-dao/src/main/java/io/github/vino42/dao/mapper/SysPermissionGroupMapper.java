package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysPermissionGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:42:32
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysPermissionGroupMapper extends BaseMapper<SysPermissionGroupEntity> {
    List<ResAllocateListDto> getPermissionGroupList(@Param("roleId") String roleId);

    SysPermissionGroupEntity getByCode(@Param("groupCode") String groupCode);

}
