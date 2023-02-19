package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.entity.SysUserGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:42:18
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysUserGroupMapper extends BaseMapper<SysUserGroupEntity> {
    SysUserGroupEntity getByCode(@Param("code") String code);
}
