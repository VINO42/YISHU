package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.dto.*;
import io.github.vino42.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:15:57
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 用户表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
    List<CasbinUserResourcePermissionModel> getCasbinUserResourcePermissionModel();

    List<CasbinUserRoleModel> getCasbinUserRoleModel();

    List<CasbinRoleResourceModel> getCasbinRoleResourceModel();

    List<CasbinUserGroupPermGroupModel> getCasbinUserGroupPermGroupModel();


    SysUserEntity getUserByUserMobile(@Param("userMobile") String userMobile);


    List<ResAllocateListDto> getByUserId(@Param("userId") String userId);

}
