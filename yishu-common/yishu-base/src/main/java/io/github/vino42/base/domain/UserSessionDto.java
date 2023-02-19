package io.github.vino42.base.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/18 20:56
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public class UserSessionDto implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 微信唯一id
     */
    private String unionId;

    /**
     * 微信openId;
     */
    private String openId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;

    private String realName;
    /**
     * 城市id
     */
    private String regionId;
    /**
     * 用户id
     */
    private Long userId;


    private Long versionStamp;

    private String token;

    private Set<String> roles;

    private Set<String> perms;

    private Set<String> resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVersionStamp() {
        return versionStamp;
    }

    public void setVersionStamp(Long versionStamp) {
        this.versionStamp = versionStamp;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    public Set<String> getResources() {
        return resources;
    }

    public void setResources(Set<String> resources) {
        this.resources = resources;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "UserSessionDto{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", unionId='" + unionId + '\'' +
                ", openId='" + openId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", regionId='" + regionId + '\'' +
                ", userId=" + userId +
                ", versionStamp=" + versionStamp +
                ", token='" + token + '\'' +
                ", roles=" + roles +
                ", perms=" + perms +
                ", resources=" + resources +
                '}';
    }
}
