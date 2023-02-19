package io.github.vino42.base.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static cn.hutool.core.text.StrPool.COMMA;

/**
 * =====================================================================================
 *
 * @Created :   2021/7/8 15:45
 * @Compiler :   jdk 8
 * @Author :   VINO
 * @Description :  获取真实ip ip工具类
 * <p>
 * =====================================================================================
 */
public class IpUtil {
    private static final String UNKNOWN = "unknown";


    /**
     * @description: http请求 真实ip获取
     * @author: VINO
     * @Param request:
     * @return: java.lang.String
     * @time: 2021/7/8
     **/
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String localhost = "127.0.0.1";
        if (ip.contains(COMMA)) {
            ip = ip.split(COMMA)[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

}
