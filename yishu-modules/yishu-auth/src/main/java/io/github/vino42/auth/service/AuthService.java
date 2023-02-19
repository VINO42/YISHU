package io.github.vino42.auth.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import io.github.vino42.auth.util.TokenUtil;
import io.github.vino42.base.context.Authentication;
import io.github.vino42.base.context.CasbinAuthentication;
import io.github.vino42.base.context.YiShuSecurityContextHolder;
import io.github.vino42.base.domain.UserSessionDto;
import io.github.vino42.base.json.jackson.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.github.vino42.base.constants.YiShuConstant.AUTH_CONSTANT.DEFAULT_TOKEN_EXPIRE;
import static io.github.vino42.base.constants.YiShuConstant.AUTH_CONSTANT.REDIS_TOKEN_CACHE_KEY;


/**
 * =====================================================================================
 *
 * @Created :   2022/10/17 16:49
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright :
 * @Decription :
 * =====================================================================================
 */
@Component
public class AuthService {

    private static String SALT = "$2a$10$v4.FUyEjfD13501wWn24Su";
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 鉴权认证
     *
     * @param token
     * @return
     */
    public UserSessionDto auth(String token) {
        if (StrUtil.isBlankOrUndefined(token)) {
            return null;
        }
        //当前请求Token已经失效 但是Hash中的token还存在 那么需要进行判断并清除Hash中的Token
        if (!stringRedisTemplate.hasKey(token)) {
            String userId = TokenUtil.parseToken(token);
            //KickOut功能中 token认证时候 先从Hash中获取Token 如果存在 并且当前请求Token与Hash中Token一致 那么就删除
            Object ifUserTokensContextHas = stringRedisTemplate.opsForHash().get(REDIS_TOKEN_CACHE_KEY, userId);
            if (ifUserTokensContextHas != null) {
                String userTokenInContext = (String) ifUserTokensContextHas;
                if (token.equals(userTokenInContext)) {
                    //如果 token失效了 那么删除 redis hash中的token
                    stringRedisTemplate.opsForHash().delete(REDIS_TOKEN_CACHE_KEY, userId);
                }
            }
            return null;
        }
        Object o = stringRedisTemplate.opsForValue().get(token);
        if (ObjectUtils.isEmpty(o)) {
            return null;
        }
        String sessionInfo = String.valueOf(o);
        UserSessionDto userSesssionDTO = JacksonUtils.toObject(sessionInfo, UserSessionDto.class);
        //重置Token失效时间 实现Token续期
        stringRedisTemplate.expire(token, DEFAULT_TOKEN_EXPIRE, TimeUnit.SECONDS);
        return userSesssionDTO;
    }

    /**
     * 设置Token
     *
     * @param userId                 用户id
     * @param userLoginResponseModel 要存入redis中的数据实体
     * @return
     */
    public String setToken(String userId, UserSessionDto userLoginResponseModel) {
        String token = TokenUtil.genToken(userId);
        //KickOut逻辑
        Object oldToken = stringRedisTemplate.opsForHash().get(REDIS_TOKEN_CACHE_KEY, userId);
        if (oldToken != null) {
            //将老Token删除 然后将新的Token放入其中
            stringRedisTemplate.delete(oldToken.toString());
            stringRedisTemplate.opsForHash().delete(REDIS_TOKEN_CACHE_KEY, oldToken);
        }
        stringRedisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(userLoginResponseModel), DEFAULT_TOKEN_EXPIRE, TimeUnit.SECONDS);
        stringRedisTemplate.opsForHash().put(REDIS_TOKEN_CACHE_KEY, userId, token);
        Authentication authResult = new CasbinAuthentication(userLoginResponseModel);
        YiShuSecurityContextHolder.getContext().setAuthentication(authResult);
        return token;
    }

    /**
     * 根据Token删除Redis中的Token
     *
     * @param token
     * @return
     */
    public boolean delToken(String token) {
        Boolean delete = stringRedisTemplate.delete(token);
        String userId = TokenUtil.parseToken(token);
        stringRedisTemplate.opsForHash().delete(REDIS_TOKEN_CACHE_KEY, userId);
        YiShuSecurityContextHolder.clearContext();
        return delete;
    }

    public boolean delTokenByUserId(String userId) {
        Object oldToken = stringRedisTemplate.opsForHash().get(REDIS_TOKEN_CACHE_KEY, userId);
        if (oldToken != null) {
            stringRedisTemplate.delete(oldToken.toString());
            stringRedisTemplate.opsForHash().delete(REDIS_TOKEN_CACHE_KEY, oldToken);
        }
        return true;
    }

    /**
     * 批量删除用户Token
     *
     * @param userIds 用户id集合
     * @return
     */
    public boolean delTokens(List<String> userIds) {
        for (String userId : userIds) {
            Object oldToken = stringRedisTemplate.opsForHash().get(REDIS_TOKEN_CACHE_KEY, userId);
            if (oldToken != null) {
                stringRedisTemplate.delete(oldToken.toString());
                stringRedisTemplate.opsForHash().delete(REDIS_TOKEN_CACHE_KEY, userId);
            }
        }

        return true;
    }

    /**
     * 校验密码是否正确
     *
     * @param inputPasswd
     * @param dbPassword
     * @return
     */
    public static boolean matches(String inputPasswd, String dbPassword) {
        return dbPassword.equals(encode(inputPasswd));
    }

    /**
     * 将前端传入的md5密码进行BCrypt编码传入后端数据库
     *
     * @param rawPassword
     * @return
     */
    public static String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        return BCrypt.hashpw(rawPassword.toString(), SALT);
    }
}
