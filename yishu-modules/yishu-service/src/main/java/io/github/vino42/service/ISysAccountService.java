package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.base.domain.UserSessionDto;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.domain.dto.WechatUserLoginDto;
import io.github.vino42.domain.entity.SysAccountEntity;

import java.util.concurrent.ExecutionException;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:43
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 账号表 服务类
 * =====================================================================================
 */
public interface ISysAccountService extends IService<SysAccountEntity> {
    SysAccountEntity getAccountByUserMobile(String userMobile, Integer accountType);

    ServiceResponseResult<UserSessionDto> wechatLogin(WechatUserLoginDto wechatUserLoginDto) throws ExecutionException, InterruptedException;


}
