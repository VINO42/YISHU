package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.SysPermissionMapper;
import io.github.vino42.domain.entity.SysPermissionEntity;
import io.github.vino42.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:37
 * @Compiler :  jdk 11
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> implements ISysPermissionService {

    @Override
    public List<String> getPermissionsForUser(Long userId) {
        return this.baseMapper.getPermissionsForUser(userId);
    }

    @Override
    public List<String> getPermissionsByUserGroupForUser(HashSet<String> userGroupId) {
        return this.baseMapper.getPermissionsByUserGroupForUser(userGroupId);
    }
}
