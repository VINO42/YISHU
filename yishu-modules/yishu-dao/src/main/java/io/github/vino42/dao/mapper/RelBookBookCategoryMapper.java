package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.RelBookBookCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:18:23
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 图书与图书分类关系映射表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface RelBookBookCategoryMapper extends BaseMapper<RelBookBookCategoryEntity> {
    List<RelBookBookCategoryEntity> selectByCategoryId(@Param("ids") List<Long> ids);

    List<ResAllocateListDto> getBookCategorisByBookId(@Param("bookId") String bookId);

    int removeByBookId(@Param("bookId") Long bookId);

    List<String> getCategoryIdsByBookId(@Param("bookId") Long bookId);

}
