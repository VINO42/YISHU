package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.SysBookMapper;
import io.github.vino42.domain.entity.SysBookEntity;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import io.github.vino42.service.ISysBookService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:07
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 图书基础表 服务实现类
 * =====================================================================================
 */
@Service
public class SysBookServiceImpl extends ServiceImpl<SysBookMapper, SysBookEntity> implements ISysBookService {


    @Override
    public SysBookEntity getByIsbn(String isbn) {
        return this.baseMapper.getByIsbn(isbn);
    }
}
