package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.domain.dto.CasbinRoleResourceModel;
import io.github.vino42.domain.dto.CasbinUserResourcePermissionModel;
import io.github.vino42.domain.dto.CasbinUserRoleModel;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysUserEntity;
import io.github.vino42.dao.mapper.SysUserMapper;
import io.github.vino42.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:28
 * @Compiler :  jdk 11
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 用户表 服务实现类
 * =====================================================================================
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {

    @Override
    public List<CasbinUserResourcePermissionModel> getCasbinUserResourcePermissionModel() {
        return this.baseMapper.getCasbinUserResourcePermissionModel();
    }

    @Override
    public List<CasbinUserRoleModel> getCasbinUserRoleModel() {
        return this.baseMapper.getCasbinUserRoleModel();
    }

    @Override
    public List<CasbinRoleResourceModel> getCasbinRoleResourceModel() {
        return this.baseMapper.getCasbinRoleResourceModel();
    }

    @Override
    public SysUserEntity getUserByUserMobile(String userMobile) {
        return this.baseMapper.getUserByUserMobile(userMobile);
    }

    @Override
    public List<ResAllocateListDto> getByUserId(String userId) {
        return this.baseMapper.getByUserId(userId);
    }
}
