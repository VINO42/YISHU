package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.entity.SysAccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:16:08
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 账号表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysAccountMapper extends BaseMapper<SysAccountEntity> {
    SysAccountEntity getAccountByUserMobile(@Param("userMobile") String userMobile, @Param("accountType") Integer accountType);

    SysAccountEntity getAccountByUnionIdAndOpenIdAndAccountType(@Param("unionId") String unionId, @Param("openId") String openId, @Param("accountType") int accountType);

}
