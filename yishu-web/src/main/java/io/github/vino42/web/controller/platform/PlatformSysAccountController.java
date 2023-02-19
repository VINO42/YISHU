package io.github.vino42.web.controller.platform;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.auth.service.AuthService;
import io.github.vino42.base.constants.YiShuConstant;
import io.github.vino42.base.enums.AccountTypeEnum;
import io.github.vino42.base.enums.GenderEnum;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.domain.dto.DeleteIdsModel;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.entity.SysUserEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.facade.UserAccessFacade;
import io.github.vino42.service.ISysAccountService;
import io.github.vino42.service.ISysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.vino42.base.constants.YiShuConstant.PHONE_CAN_SEND_SMS_REGEX;
import static io.github.vino42.base.constants.YiShuConstant.STRONG_PASSWORD_REGEX;
import static io.github.vino42.base.response.ServiceResponseCodeEnum.ACCOUNT_EXISTED;
import static io.github.vino42.base.response.ServiceResponseCodeEnum.ILLEGAL_ARGS;
import static io.github.vino42.base.utils.CommonImageOssUtil.genAvatar2;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:28:07
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : 38912428@qq.com
 * @Decription : 账号表 控制器
 * =====================================================================================
 */
@RestController
@Tag(name = "后台管理账号管理模块")
@RequestMapping("/platform/sysAccount")
public class PlatformSysAccountController {

    @Autowired
    private ISysAccountService sysAccountService;
    @Autowired
    UserAccessFacade userAccessFacade;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private AuthService authService;

    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysAccountPage(Page<SysAccountEntity> page, SysAccountEntity sysAccount) {
        Page<SysAccountEntity> page1 = sysAccountService.page(page, Wrappers.query(sysAccount).orderByDesc("create_time"));
        page1.getRecords().forEach(d -> d.setPasswd("********"));
        return ResultMapper.ok(page1);
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult createSysAccount(@Valid @RequestBody SysAccountEntity sysAccount, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            return ResultMapper.error(ILLEGAL_ARGS.status, StringUtils.isBlank(defaultMessage) ? ILLEGAL_ARGS.message : defaultMessage);
        }
        String mobile = sysAccount.getMobile();
        boolean isMobile = Validator.isMatchRegex(PHONE_CAN_SEND_SMS_REGEX, mobile);
        if (!isMobile) {
            return ResultMapper.illegalArgument("请输入正确格式的手机号");
        }
        String passwd = sysAccount.getPasswd();
        if (!Validator.isMatchRegex(STRONG_PASSWORD_REGEX, passwd)) {
            return ResultMapper.illegalArgument("密码必须包含大小写字母和数字 特殊字符的组合");
        }
        SysAccountEntity dbAccount = userAccessFacade.getUserByUserMobile(sysAccount.getMobile(), AccountTypeEnum.ADMIN.getValue());
        if (dbAccount != null) {
            return ResultMapper.error(ACCOUNT_EXISTED);
        }
        String avatar = genAvatar2();
        sysAccount.setAvatar(avatar);
        sysAccount.setId(IdGenerator.nextId());
        sysAccount.setStatu(TableStatusEnum.NORMAL);
        sysAccount.setUserId(IdGenerator.nextId());
        sysAccount.setPasswd(AuthService.encode(SecureUtil.md5(sysAccount.getPasswd())));
        sysAccount.setAccountType(AccountTypeEnum.ADMIN.getValue());
        SysUserEntity sysUserEntity = assemebleUser(sysAccount);
        sysUserService.save(sysUserEntity);
        return ResultMapper.ok(sysAccountService.save(sysAccount));
    }

    private SysUserEntity assemebleUser(SysAccountEntity sysAccount) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(sysAccount.getUserId());
        sysUserEntity.setStatu(TableStatusEnum.NORMAL);
        sysUserEntity.setMobile(sysAccount.getMobile());
        sysUserEntity.setGender(GenderEnum.UNKOWN.getGenderValue());
        sysUserEntity.setRealName(YiShuConstant.NOT_AVALIABLE);
        return sysUserEntity;
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult updateSysAccount(@Valid @RequestBody SysAccountEntity sysAccount) {
        SysAccountEntity oldData = sysAccountService.getById(sysAccount.getId());
        boolean ifNeedUserReLogin = false;

        if (oldData.getStatu().status == sysAccount.getStatu().status) {
            SysAccountEntity accountByUserMobile = sysAccountService.getAccountByUserMobile(sysAccount.getMobile(), AccountTypeEnum.ADMIN.getValue());
            if (accountByUserMobile != null && !accountByUserMobile.getId().equals(sysAccount.getId())) {
                return ResultMapper.error(ACCOUNT_EXISTED);
            }
            if (sysAccount.getAccountType() == AccountTypeEnum.ADMIN.getValue()) {
                String mobile = sysAccount.getMobile();
                boolean isMobile = Validator.isMatchRegex(PHONE_CAN_SEND_SMS_REGEX, mobile);
                if (!isMobile) {
                    return ResultMapper.illegalArgument("请输入正确格式的手机号");
                }
                if ("********".equals(sysAccount.getPasswd())) {
                    sysAccount.setPasswd(null);
                } else {
                    String passwd = sysAccount.getPasswd();
                    if (!Validator.isMatchRegex(STRONG_PASSWORD_REGEX, passwd)) {
                        return ResultMapper.illegalArgument("密码必须包含大小写字母和数字 特殊字符的组合");
                    }
                    String md5Passwd = SecureUtil.md5(sysAccount.getPasswd());
                    String encodePasswd = AuthService.encode(md5Passwd);
                    sysAccount.setPasswd(encodePasswd);
                    ifNeedUserReLogin = true;
                }
            }
        } else {
            ifNeedUserReLogin = true;
        }
        boolean updateResult = sysAccountService.updateById(sysAccount);
        if (updateResult && ifNeedUserReLogin && sysAccount.getAccountType() == AccountTypeEnum.ADMIN.getValue()) {
            authService.delTokenByUserId(String.valueOf(sysAccount.getUserId()));
        }
        return ResultMapper.ok(updateResult);
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult deleteSysAccount(@RequestBody DeleteIdsModel id) {
        List<String> collect = id.getId().stream().map(d -> {
            SysAccountEntity sysAccountEntity = new SysAccountEntity();
            sysAccountEntity.setId(d);
            sysAccountEntity.setStatu(TableStatusEnum.DELETED);
            boolean b = sysAccountService.updateById(sysAccountEntity);
            return sysAccountEntity;
        }).map(d -> String.valueOf(d.getUserId())).collect(Collectors.toList());
        authService.delTokens(collect);
        return ResultMapper.ok(true);
    }


}
