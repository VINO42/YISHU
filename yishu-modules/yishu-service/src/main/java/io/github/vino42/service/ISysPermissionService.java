package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.SysPermissionEntity;

import java.util.HashSet;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:37
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :  服务类
 * =====================================================================================
 */
public interface ISysPermissionService extends IService<SysPermissionEntity> {
    List<String>  getPermissionsForUser(Long userId);

    List<String> getPermissionsByUserGroupForUser(HashSet<String> userGroupId);
}
