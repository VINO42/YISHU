package io.github.vino42.dao.mapper;

import io.github.vino42.domain.entity.SysBookEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:18:32
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 图书基础表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysBookMapper extends BaseMapper<SysBookEntity> {
    SysBookEntity getByIsbn(@Param("isbn") String isbn);

}
