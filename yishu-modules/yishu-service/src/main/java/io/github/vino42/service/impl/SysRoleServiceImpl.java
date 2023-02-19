package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysRoleEntity;
import io.github.vino42.dao.mapper.SysRoleMapper;
import io.github.vino42.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:32
 * @Compiler :  jdk 11
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {

    @Override
    public List<ResAllocateListDto> getByUserId(String userId) {
        return this.baseMapper.getByUserId(userId);
    }

    @Override
    public List<String> getRolesForUser(Long userId) {
        return this.baseMapper.getRolesForUser(userId);    }

    @Override
    public List<ResAllocateListDto> getByGroupId(String groupId) {
        return this.baseMapper.getByGroupId(groupId);
    }

    @Override
    public SysRoleEntity getRoleByCode(String roleCode) {
        return this.baseMapper.getRoleByCode(roleCode);
    }

    @Override
    public List<String> getRolesByUserGroupForUser(Long userGroupId) {
        return this.baseMapper.getRolesByUserGroupForUser(userGroupId);
    }
}
