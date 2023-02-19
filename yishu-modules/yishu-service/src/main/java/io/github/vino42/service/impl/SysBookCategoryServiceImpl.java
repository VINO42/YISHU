package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.SysBookCategoryMapper;
import io.github.vino42.domain.entity.SysBookCategoryEntity;
import io.github.vino42.service.ISysBookCategoryService;
import org.springframework.stereotype.Service;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:11
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 图书分类表 服务实现类
 * =====================================================================================
 */
@Service
public class SysBookCategoryServiceImpl extends ServiceImpl<SysBookCategoryMapper, SysBookCategoryEntity> implements ISysBookCategoryService {

    @Override
    public SysBookCategoryEntity getByCode(String categoryCode) {
        return this.baseMapper.getByCode(categoryCode);
    }
}
