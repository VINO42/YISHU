package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.RelUserGroupRoleMapper;
import io.github.vino42.domain.entity.RelUserGroupRoleEntity;
import io.github.vino42.service.IRelUserGroupRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:48
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    vino
 * @Copyright : vino
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class RelUserGroupRoleServiceImpl extends ServiceImpl<RelUserGroupRoleMapper, RelUserGroupRoleEntity> implements IRelUserGroupRoleService {

    @Override
    @Transactional
    public boolean removeUserGroupRoleIdByUserId(Long userId) {
        return this.baseMapper.removeUserGroupRoleIdByUserId(userId) > 0;
    }

    @Override
    public List<String> selectUserIdsByUserGroup(Long groupId) {
        return this.baseMapper.selectUserIdsByUserGroup(groupId);
    }

    @Override
    public List<RelUserGroupRoleEntity> selectByRoleIds(List<Long> roleIds) {
        return this.baseMapper.selectByRoleIds(roleIds);
    }
}
