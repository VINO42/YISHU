package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.SysPermissionGroupMapper;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysPermissionGroupEntity;
import io.github.vino42.service.ISysPermissionGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:37
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    vino
 * @Copyright : vino
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class SysPermissionGroupServiceImpl extends ServiceImpl<SysPermissionGroupMapper, SysPermissionGroupEntity> implements ISysPermissionGroupService {

    @Override
    public List<ResAllocateListDto> getPermissionGroupList(String userId) {
        return this.baseMapper.getPermissionGroupList(userId);
    }

    @Override
    public SysPermissionGroupEntity getByCode(String groupCode) {
        return this.baseMapper.getByCode(groupCode);
    }
}
