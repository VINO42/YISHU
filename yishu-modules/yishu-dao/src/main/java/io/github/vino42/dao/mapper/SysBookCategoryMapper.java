package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.entity.SysBookCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:18:28
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 图书分类表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysBookCategoryMapper extends BaseMapper<SysBookCategoryEntity> {

    SysBookCategoryEntity getByCode(@Param("categoryCode") String categoryCode);

}
