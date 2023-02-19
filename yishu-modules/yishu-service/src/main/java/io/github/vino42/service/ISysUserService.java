package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.dto.CasbinRoleResourceModel;
import io.github.vino42.domain.dto.CasbinUserResourcePermissionModel;
import io.github.vino42.domain.dto.CasbinUserRoleModel;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysUserEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:28
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 用户表 服务类
 * =====================================================================================
 */
public interface ISysUserService extends IService<SysUserEntity> {
    List<CasbinUserResourcePermissionModel> getCasbinUserResourcePermissionModel();

    List<CasbinUserRoleModel> getCasbinUserRoleModel();

    List<CasbinRoleResourceModel> getCasbinRoleResourceModel();

    SysUserEntity  getUserByUserMobile(String userMobile);

    List<ResAllocateListDto>  getByUserId(String userId);
}
