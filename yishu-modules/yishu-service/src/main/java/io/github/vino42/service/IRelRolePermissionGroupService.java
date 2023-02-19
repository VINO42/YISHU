package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.RelRolePermissionGroupEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:45
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  服务类
 * =====================================================================================
 */
public interface IRelRolePermissionGroupService extends IService<RelRolePermissionGroupEntity> {
    void removeRolePermissionGroupByRoleId(String roleId);

    List<RelRolePermissionGroupEntity> getByGroupIds(List<Long> ids);

}
