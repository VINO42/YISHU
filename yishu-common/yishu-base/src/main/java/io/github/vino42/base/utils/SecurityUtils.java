package io.github.vino42.base.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import io.github.vino42.base.context.Authentication;
import io.github.vino42.base.context.YiShuSecurityContextHolder;
import io.github.vino42.base.domain.UserSessionDto;
import io.github.vino42.base.exceptions.SystemInternalException;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Set;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.AUTH_401_EXPIRED;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 11:41
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription :
 * =====================================================================================
 */
@UtilityClass
public class SecurityUtils {
    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {

        Authentication authentication = YiShuSecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication;
    }

    /**
     * 获取用户
     */
    public UserSessionDto getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getDetails();
        if (principal instanceof UserSessionDto) {
            return (UserSessionDto) principal;
        }
        return null;
    }

    public Long getUserId() {

        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        UserSessionDto user = getUser();
        if (user == null) {
            throw new SystemInternalException(AUTH_401_EXPIRED);
        }
        return user.getUserId();
    }

    public String getRealName() {

        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        UserSessionDto user = getUser();
        if (user == null) {
            throw new SystemInternalException(AUTH_401_EXPIRED);
        }
        return StrUtil.isBlankOrUndefined(user.getRealName()) ? user.getMobile() : user.getRealName();
    }

    public Long getAccountId() {

        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        UserSessionDto user = getUser();
        if (user == null) {
            throw new SystemInternalException(AUTH_401_EXPIRED);
        }
        return user.getId();
    }


    public Set<String> getPerms() {
        Authentication authentication = getAuthentication();
        Collection<String> perms = authentication.getPerms();
        return Sets.newHashSet(perms);
    }

    public Set<String> getResources() {
        Authentication authentication = getAuthentication();
        Collection<String> resources = authentication.getPerms();
        return Sets.newHashSet(resources);
    }

}
