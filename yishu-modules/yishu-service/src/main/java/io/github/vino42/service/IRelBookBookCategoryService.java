package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.RelBookBookCategoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:14:48
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 图书与图书分类关系映射表 服务类
 * =====================================================================================
 */
public interface IRelBookBookCategoryService extends IService<RelBookBookCategoryEntity> {
    List<RelBookBookCategoryEntity> selectByCategoryId(List<Long> ids);

    List<ResAllocateListDto> getBookCategorisByBookId(String bookId);

    int removeByBookId( Long bookId);

    List<String> getCategoryIdsByBookId(Long bookId);
}
