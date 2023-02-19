package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.dto.SysUserPublishBookRecorDTO;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:18:39
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 用户发布图书记录表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysUserPublishBookRecordMapper extends BaseMapper<SysUserPublishBookRecordEntity> {
    List<SysUserPublishBookRecordEntity> selectByBookId(@Param("bookId") Long bookId);

    List<SysUserPublishBookRecorDTO> getPageList(@Param("start") int start, @Param("size") int size, @Param("data") SysUserPublishBookRecorDTO query);

    int getTotal(@Param("data") SysUserPublishBookRecorDTO sysUserPublishBookRecorDTO);

    SysUserPublishBookRecorDTO getPublishById(@Param("id") Long publishId);


    int getCountByUserIdAndBookId(@Param("userId")Long userId,@Param("id") Long id);

}
