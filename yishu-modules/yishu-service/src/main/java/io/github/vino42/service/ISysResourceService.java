package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.dto.ButtonPerms;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.dto.ResourceTreeDto;
import io.github.vino42.domain.entity.SysResourceEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:34
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :  服务类
 * =====================================================================================
 */
public interface ISysResourceService extends IService<SysResourceEntity> {
    List<ResourceTreeDto> getPlatformResources();

    boolean getByResourceUrl(String url);

    List<SysResourceEntity> getResourceByType(Integer type);

    List<ButtonPerms> getButtonPerms(Long userId);

    boolean selectByParentIdAndComponentAndLevelCodeAndTitle(Long parentId,
                                                             String component,
                                                             int levelCode,
                                                             String title);

    List<ResAllocateListDto> getReouscesByRoleId(String roleId);


}
