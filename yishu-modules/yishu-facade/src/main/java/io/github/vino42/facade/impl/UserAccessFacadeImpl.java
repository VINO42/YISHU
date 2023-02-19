package io.github.vino42.facade.impl;

import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.facade.UserAccessFacade;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/18 20:35
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Service
public class UserAccessFacadeImpl implements UserAccessFacade {
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysAccountService sysAccountService;

    @Override
    public SysAccountEntity getUserByUserMobile(String userMobile, int accountType) {
        SysAccountEntity sysUserEntity = sysAccountService.getAccountByUserMobile(userMobile, accountType);
        return sysUserEntity;
    }
}
