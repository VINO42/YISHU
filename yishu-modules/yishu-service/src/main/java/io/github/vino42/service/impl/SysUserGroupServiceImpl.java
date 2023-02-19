package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.SysUserGroupMapper;
import io.github.vino42.domain.entity.SysUserGroupEntity;
import io.github.vino42.service.ISysUserGroupService;
import org.springframework.stereotype.Service;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:34
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    vino
 * @Copyright : vino
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroupEntity> implements ISysUserGroupService {

    @Override
    public SysUserGroupEntity getByCode(String code) {
        return this.baseMapper.getByCode(code);
    }
}
