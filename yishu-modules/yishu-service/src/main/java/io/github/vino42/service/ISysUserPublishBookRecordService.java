package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.dto.SysUserPublishBookRecorDTO;
import io.github.vino42.domain.dto.WechatUserPublishBookDTO;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:02
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 用户发布图书记录表 服务类
 * =====================================================================================
 */
public interface ISysUserPublishBookRecordService extends IService<SysUserPublishBookRecordEntity> {
    List<SysUserPublishBookRecordEntity> selectByBookId(Long bookId);

    List<SysUserPublishBookRecorDTO> getPageList(int start, int size, SysUserPublishBookRecorDTO query);

    int getTotal(SysUserPublishBookRecorDTO sysUserPublishBookRecorDTO);


    /**
     * 发布图书
     * @param wechatUserPublishBookDTO
     * @return
     */
    boolean publish(WechatUserPublishBookDTO wechatUserPublishBookDTO) throws Exception;

    SysUserPublishBookRecorDTO getPublishById(Long publishId);

    boolean getCountByUserIdAndBookId(Long userId, Long id);
}
