package io.github.vino42.base.context;


import io.github.vino42.base.domain.UserSessionDto;

import java.util.Collection;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 10:42
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription :
 * =====================================================================================
 */
public class CasbinAuthentication implements Authentication {

    private final UserSessionDto userSessionDto;

    private Boolean isAuthenticated;

    public CasbinAuthentication(UserSessionDto userSessionDto) {
        this.userSessionDto = userSessionDto;
        this.isAuthenticated = (userSessionDto != null);
    }

    @Override
    public Collection<String> getRoles() {
        return userSessionDto.getRoles();
    }

    @Override
    public Collection<String> getPerms() {
        return userSessionDto.getPerms();
    }

    @Override
    public Collection<String> getResources() {
        return userSessionDto.getResources();
    }

    @Override
    public Object getDetails() {
        return userSessionDto;
    }

    @Override
    public boolean isAuthenticated() {
        return userSessionDto != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }
}
