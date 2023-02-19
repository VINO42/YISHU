package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.RelUserGroupRoleEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:48
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  服务类
 * =====================================================================================
 */
public interface IRelUserGroupRoleService extends IService<RelUserGroupRoleEntity> {

    boolean removeUserGroupRoleIdByUserId(Long groupId);

    List<String> selectUserIdsByUserGroup(Long groupId);

    List<RelUserGroupRoleEntity> selectByRoleIds(List<Long> roleIds);

}
