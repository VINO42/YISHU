package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.RelBookBookCategoryMapper;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.RelBookBookCategoryEntity;
import io.github.vino42.service.IRelBookBookCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:14:48
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 图书与图书分类关系映射表 服务实现类
 * =====================================================================================
 */
@Service
public class RelBookBookCategoryServiceImpl extends ServiceImpl<RelBookBookCategoryMapper, RelBookBookCategoryEntity> implements IRelBookBookCategoryService {


    @Override
    public List<RelBookBookCategoryEntity> selectByCategoryId(List<Long> ids) {
        return this.baseMapper.selectByCategoryId(ids);
    }

    @Override
    public List<ResAllocateListDto> getBookCategorisByBookId(String bookId) {
        return this.baseMapper.getBookCategorisByBookId(bookId);
    }

    @Override
    public int removeByBookId(Long bookId) {
        return this.baseMapper.removeByBookId(bookId);
    }

    @Override
    public List<String> getCategoryIdsByBookId(Long bookId) {
        return this.baseMapper.getCategoryIdsByBookId(bookId);
    }
}
