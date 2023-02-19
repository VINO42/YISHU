package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysRoleEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:32
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :  服务类
 * =====================================================================================
 */
public interface ISysRoleService extends IService<SysRoleEntity> {
    List<ResAllocateListDto> getByUserId(String userId);

    List<String> getRolesForUser(Long userId);

    List<ResAllocateListDto> getByGroupId(String groupId);

    SysRoleEntity  getRoleByCode(String roleCode);


    List<String> getRolesByUserGroupForUser(Long userGroupId);
}
