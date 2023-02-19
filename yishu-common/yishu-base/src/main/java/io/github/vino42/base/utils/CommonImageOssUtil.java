package io.github.vino42.base.utils;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import io.github.vino42.base.http.OkHttpClient;
import io.github.vino42.base.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

import static cn.hutool.core.text.CharSequenceUtil.EMPTY;

/**
 * =====================================================================================
 *
 * @Created :   2023/1/4 22:05
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Slf4j
public class CommonImageOssUtil {
    private static final String FIVE_EIGHT_REQ_URL = "https://upload.58cdn.com.cn/json";
    private static final String FIVE_EIGHT_PREFIX_URL = "https://pic8.58cdn.com.cn/nowater/webim/big/";

    static OkHttpClient okHttpClient = OkHttpClient.create();

    public static String uploadTo58(String filePath) throws Exception {
        String base64A = Base64Encoder.encode(FileUtil.readBytes(filePath));
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Origin", "chrome-extension://dckaeinoeaogebmhijpkpmacifmpgmcb");
        headers.put("Content-Type", "application/json");
        headers.put("Host", "upload.58cdn.com.cn");

        Map<String, Object> params = Maps.newHashMap();
        params.put("Pic-Size", "0*0");
        params.put("Pic-Encoding", "base64");
        params.put("Pic-Path", "/nowater/webim/big/");
        params.put("Pic-Data", base64A);

        Response post = okHttpClient.post(FIVE_EIGHT_REQ_URL, JSONUtil.toJsonStr(params), headers);
        if (post.isOK()) {
            String s = FIVE_EIGHT_PREFIX_URL + post.bodyString();
            log.info("ossUrl:{}", s);
            return s;
        }
        return EMPTY;
    }

    public static String uploadTo582(MultipartFile file) throws Exception {
        String templeFileName = "./temp/" + file.getOriginalFilename();
        File file1 = new File(templeFileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), file1);

        String base64A = Base64Encoder.encode(FileUtil.readBytes(file1));
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Origin", "chrome-extension://dckaeinoeaogebmhijpkpmacifmpgmcb");
        headers.put("Content-Type", "application/json");
        headers.put("Host", "upload.58cdn.com.cn");

        Map<String, Object> params = Maps.newHashMap();
        params.put("Pic-Size", "0*0");
        params.put("Pic-Encoding", "base64");
        params.put("Pic-Path", "/nowater/webim/big/");
        params.put("Pic-Data", base64A);

        Response post = okHttpClient.post(FIVE_EIGHT_REQ_URL, JSONUtil.toJsonStr(params), headers);
        if (post.isOK()) {
            FileUtil.del(file1);
            String s = FIVE_EIGHT_PREFIX_URL + post.bodyString();
            log.info("ossUrl:{}", s);
            return s;
        }
        return EMPTY;
    }


    public static String genAvatar() throws Exception {
        //?sort=男&format=json
        int i = RandomUtil.randomInt(2);
        String reqUrl = "";
        if (i == 1) {
            reqUrl = "https://api.uomg.com/api/rand.avatar?sort=%E5%8A%A8%E6%BC%AB%E7%94%B7&format=json";
        } else {
            reqUrl = "https://api.uomg.com/api/rand.avatar?sort=%E5%8A%A8%E6%BC%AB%E5%A5%B3&format=json";
        }

        Map<String, String> headers = Maps.newHashMap();

        Response post = okHttpClient.get(reqUrl, headers);
        if (post.isOK()) {
            String s = post.bodyString();
            JSONObject entries = JSONUtil.parseObj(s);
            Integer code = entries.getInt("code");
            String imgurl = entries.getStr("imgurl");
            if (code == 1) {
                log.info("avatar:{}", imgurl);
                return imgurl;
            } else {
                return EMPTY;
            }

        }
        return EMPTY;
    }


    public static String genAvatar2() throws Exception {
        String preUrl = "https://api.multiavatar.com/%s.png";
        //?sort=男&format=json
        String reqUrl = String.format(preUrl, RandomUtil.randomString(16));

        Map<String, String> headers = Maps.newHashMap();

        Response post = okHttpClient.get(reqUrl, headers);
        if (post.isOK()) {
            log.info("avatar:{}", reqUrl);
            return reqUrl;
        }

        return EMPTY;
    }

}
