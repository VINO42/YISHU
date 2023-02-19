package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.dto.ButtonPerms;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.dto.ResourceTreeDto;
import io.github.vino42.domain.entity.SysResourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:16:04
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :  Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResourceEntity> {
    List<ResourceTreeDto> getPlatformResources();

    int getByResourceUrl(@Param("url") String url);

    List<SysResourceEntity> getResourceByType(@Param("type") Integer type);

    List<ButtonPerms> getButtonPerms(@Param("userId") Long userId);

    int selectByParentIdAndComponentAndLevelCodeAndTitle(@Param("parentId") Long parentId,
                                                             @Param("component") String component,
                                                             @Param("levelCode") int levelCode,
                                                             @Param("title") String title);

    List<ResAllocateListDto> getReouscesByRoleId(@Param("roleId") String roleId);
}
