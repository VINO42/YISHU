package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.SysResourceMapper;
import io.github.vino42.domain.dto.ButtonPerms;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.dto.ResourceTreeDto;
import io.github.vino42.domain.entity.SysResourceEntity;
import io.github.vino42.service.ISysResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:34
 * @Compiler :  jdk 11
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResourceEntity> implements ISysResourceService {

    @Override
    public List<ResourceTreeDto> getPlatformResources() {
        return this.baseMapper.getPlatformResources();
    }

    @Override
    public boolean getByResourceUrl(String url) {
        return this.baseMapper.getByResourceUrl(url) > 0;
    }

    @Override
    public List<SysResourceEntity> getResourceByType(Integer type) {
        return this.baseMapper.getResourceByType(type);
    }

    @Override
    public List<ButtonPerms> getButtonPerms(Long userId) {
        return this.baseMapper.getButtonPerms(userId);
    }

    @Override
    public boolean selectByParentIdAndComponentAndLevelCodeAndTitle(Long parentId, String component, int levelCode, String title) {
        return this.baseMapper.selectByParentIdAndComponentAndLevelCodeAndTitle(parentId, component, levelCode, title) > 0;
    }

    @Override
    public List<ResAllocateListDto> getReouscesByRoleId(String roleId) {
        return this.baseMapper.getReouscesByRoleId(roleId);
    }
}
