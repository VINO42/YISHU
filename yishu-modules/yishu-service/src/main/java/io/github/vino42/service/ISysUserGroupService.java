package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.SysPermissionGroupEntity;
import io.github.vino42.domain.entity.SysUserGroupEntity;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:34
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  服务类
 * =====================================================================================
 */
public interface ISysUserGroupService extends IService<SysUserGroupEntity> {
    SysUserGroupEntity getByCode(String code);

}
