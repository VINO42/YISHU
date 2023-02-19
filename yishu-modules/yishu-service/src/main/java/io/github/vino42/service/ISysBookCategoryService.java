package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.SysBookCategoryEntity;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:11
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 图书分类表 服务类
 * =====================================================================================
 */
public interface ISysBookCategoryService extends IService<SysBookCategoryEntity> {
    SysBookCategoryEntity getByCode(String categoryCode);

}
