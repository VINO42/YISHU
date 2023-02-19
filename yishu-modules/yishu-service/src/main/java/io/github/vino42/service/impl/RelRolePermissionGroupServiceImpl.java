package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.RelRolePermissionGroupMapper;
import io.github.vino42.domain.entity.RelRolePermissionGroupEntity;
import io.github.vino42.service.IRelRolePermissionGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:45
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    vino
 * @Copyright : vino
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class RelRolePermissionGroupServiceImpl extends ServiceImpl<RelRolePermissionGroupMapper, RelRolePermissionGroupEntity> implements IRelRolePermissionGroupService {

    @Override
    public void removeRolePermissionGroupByRoleId(String roleId) {
        this.baseMapper.removeRolePermissionGroupByRoleId(roleId);
    }

    @Override
    public List<RelRolePermissionGroupEntity> getByGroupIds(List<Long> ids) {
        return this.baseMapper.getByGroupIds(ids);
    }
}
