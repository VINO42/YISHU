package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.SysBookEntity;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:07
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 图书基础表 服务类
 * =====================================================================================
 */
public interface ISysBookService extends IService<SysBookEntity> {

    SysBookEntity getByIsbn(String isbn);
}
