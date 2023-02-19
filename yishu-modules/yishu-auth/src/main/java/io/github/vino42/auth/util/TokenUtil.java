package io.github.vino42.auth.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;

import static cn.hutool.core.text.CharSequenceUtil.EMPTY;
import static cn.hutool.core.text.StrPool.UNDERLINE;
import static io.github.vino42.base.constants.YiShuConstant.AUTH_CONSTANT.AES_TOKEN_KEY;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/17 22:24
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : token生成工具啊
 * =====================================================================================
 */
public class TokenUtil {
    public static AES aes = SecureUtil.aes(AES_TOKEN_KEY.getBytes(StandardCharsets.UTF_8));

    public static String genToken(String userId) {
        String originData = userId + UNDERLINE + RandomUtil.randomString(8);
        String token = aes.encryptHex(originData, StandardCharsets.UTF_8);
        return token;
    }


    public static String parseToken(String token) {
        String decrypt = aes.decryptStr(token, StandardCharsets.UTF_8);
        String[] arr = decrypt.split(UNDERLINE);
        if (arr.length >= 2) {
            return decrypt.split(UNDERLINE)[0];
        }
        return EMPTY;
    }

}
