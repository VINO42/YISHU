package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import io.github.vino42.auth.service.AuthService;
import io.github.vino42.base.domain.UserSessionDto;
import io.github.vino42.base.enums.AccountTypeEnum;
import io.github.vino42.base.enums.GenderEnum;
import io.github.vino42.base.enums.SysUserGroupEnum;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.dao.mapper.SysAccountMapper;
import io.github.vino42.domain.dto.WechatUserLoginDto;
import io.github.vino42.domain.entity.RelUserUserGroupEntity;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.entity.SysUserEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:18:43
 * @Compiler :  jdk 11
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 账号表 服务实现类
 * =====================================================================================
 */
@Service
@Slf4j
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements ISysAccountService {

    @Autowired
    AuthService authService;
    @Autowired
    ISysRegionGeoService sysRegionGeoService;
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    IRelUserUserGroupService relUserUserGroupService;
    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    ISysPermissionService sysPermissionService;

    @Override
    public SysAccountEntity getAccountByUserMobile(String userMobile, Integer accountType) {
        return this.baseMapper.getAccountByUserMobile(userMobile, accountType);
    }

    @Override
    @Transactional
    public ServiceResponseResult<UserSessionDto> wechatLogin(WechatUserLoginDto wechatUserLoginDto) throws ExecutionException, InterruptedException {
        Long start = System.currentTimeMillis();
        UserSessionDto result = new UserSessionDto();
        String openId = wechatUserLoginDto.getOpenId();
        String unionId = wechatUserLoginDto.getUnionId();
//        SysRegionGeoEntity sysRegionGeoEntity = sysRegionGeoService.getCityCodeByLoLa(wechatUserLoginDto.getLongitude(), wechatUserLoginDto.getLatitude());
//         Future<SysRegionGeoEntity> futures = executorService.submit(()-> sysRegionGeoService.getCityCodeByLoLa(wechatUserLoginDto.getLongitude(), wechatUserLoginDto.getLatitude()));
        Long userId = null;
        log.info("[消耗时间：{}]", System.currentTimeMillis() - start);
        SysAccountEntity dbAccount = this.baseMapper.getAccountByUnionIdAndOpenIdAndAccountType(unionId, openId, AccountTypeEnum.WECHAT.getCode());
        if (dbAccount != null) {
            //登录
            userId = dbAccount.getUserId();
            result.setId(dbAccount.getId());
            result.setUserId(dbAccount.getUserId());
            result.setVersionStamp(System.currentTimeMillis());
            result.setOpenId(wechatUserLoginDto.getOpenId());
            result.setUnionId(wechatUserLoginDto.getUnionId());
            result.setNickName(wechatUserLoginDto.getNickName());
            result.setUserId(userId);
            List<String> roles = sysRoleService.getRolesForUser(userId);
            result.setRoles(Sets.newHashSet(roles));
            List<String> permissionsForUser = sysPermissionService.getPermissionsForUser(userId);
            result.setPerms(Sets.newHashSet(permissionsForUser));
        } else {
            userId = IdGenerator.nextId();
            long accountId = IdGenerator.nextId();
            assembleUserAccountRolePerm(wechatUserLoginDto, userId, accountId, result);
            result.setId(accountId);
            result.setUserId(userId);
            result.setVersionStamp(System.currentTimeMillis());
            result.setOpenId(wechatUserLoginDto.getOpenId());
            result.setUnionId(wechatUserLoginDto.getUnionId());
            result.setNickName(wechatUserLoginDto.getNickName());
            result.setUserId(userId);
        }
        String token = authService.setToken(String.valueOf(userId), result);
        result.setToken(token);
        return ResultMapper.ok(result);
    }

    private void assembleUserAccountRolePerm(WechatUserLoginDto wechatUserLoginDto, long userId, long accountId, UserSessionDto result) {
        //注册
        SysAccountEntity newAccount = new SysAccountEntity();
        newAccount.setId(accountId);
        newAccount.setAvatar(wechatUserLoginDto.getAvatar());
        newAccount.setStatu(TableStatusEnum.NORMAL);
        newAccount.setUserId(userId);
        newAccount.setOpenId(wechatUserLoginDto.getOpenId());
        newAccount.setNickName(wechatUserLoginDto.getNickName());
        newAccount.setUnionId(wechatUserLoginDto.getUnionId());
        newAccount.setAccountType(AccountTypeEnum.WECHAT.getValue());
        newAccount.setCreateBy(userId);
        newAccount.setCreateTime(LocalDateTime.now());
        newAccount.setVersionStamp(System.currentTimeMillis());
        newAccount.setUpdateBy(userId);
        newAccount.setUpdateTime(LocalDateTime.now());
        this.baseMapper.insert(newAccount);
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(userId);
        sysUserEntity.setStatu(TableStatusEnum.NORMAL);
        sysUserEntity.setRealName(wechatUserLoginDto.getNickName());
        sysUserEntity.setVersionStamp(System.currentTimeMillis());
        sysUserEntity.setGender(GenderEnum.UNKOWN.getGenderValue());
        sysUserEntity.setUpdateTime(LocalDateTime.now());
        sysUserEntity.setCreateTime(LocalDateTime.now());
        sysUserEntity.setCreateBy(userId);
        sysUserEntity.setCreateTime(LocalDateTime.now());
        sysUserEntity.setVersionStamp(System.currentTimeMillis());
        sysUserEntity.setUpdateBy(userId);
        sysUserEntity.setUpdateTime(LocalDateTime.now());
        sysUserService.save(sysUserEntity);
        RelUserUserGroupEntity relUserUserGroupEntity = new RelUserUserGroupEntity();
        relUserUserGroupEntity.setId(IdGenerator.nextId());
        relUserUserGroupEntity.setUserId(userId);
        relUserUserGroupEntity.setStatu(TableStatusEnum.NORMAL);
        relUserUserGroupEntity.setUserGroupId(Long.valueOf(SysUserGroupEnum.WECHAT.getId()));
        relUserUserGroupEntity.setVersionStamp(System.currentTimeMillis());
        relUserUserGroupService.save(relUserUserGroupEntity);
        List<String> roles = sysRoleService.getRolesByUserGroupForUser(Long.valueOf(SysUserGroupEnum.WECHAT.getId()));
        HashSet<String> roleSet = Sets.newHashSet(roles);
        result.setRoles(Sets.newHashSet(roles));
        List<String> permissionsForUser = sysPermissionService.getPermissionsByUserGroupForUser(roleSet);
        result.setPerms(Sets.newHashSet(permissionsForUser));
    }
}
