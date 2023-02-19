package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysPermissionGroupEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:37
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  服务类
 * =====================================================================================
 */
public interface ISysPermissionGroupService extends IService<SysPermissionGroupEntity> {
    List<ResAllocateListDto> getPermissionGroupList(String userId);

    SysPermissionGroupEntity getByCode(String groupCode);

}
