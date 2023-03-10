package io.github.vino42.redis.lock.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.RandomUtil;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 分布式锁工具类
 *
 * @author VINO
 * @since 1.0.0
 */
public class LockUtil {
    
    /**
     * 获取本机网卡地址
     *
     * @return macAddress
     */
    public static String getLocalMac() {
        
        try {
            InetAddress ia = InetAddress.getLocalHost();
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuffer sb = new StringBuffer();
            if (mac!=null){
                for (int i = 0; i < mac.length; i++) {
                    if (i != 0) {
                        sb.append("-");
                    }
                    // mac[i] & 0xFF 是为了把byte转化为正整数
                    String s = Integer.toHexString(mac[i] & 0xFF);
                    sb.append(s.length() == 1 ? 0 + s : s);
                }
            }else{
                sb.append(RandomUtil.randomString(16));
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            return sb.toString().toUpperCase().replaceAll(StrPool.DASHED, CharSequenceUtil.EMPTY);
        } catch (Exception e) {
            throw new IllegalStateException("getLocalMAC error", e);
        }
    }
    
    /**
     * 获取jvmPId
     *
     * @return jvmPid
     */
    public static String getJvmPid() {
        
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
            return pid;
        }
        throw new IllegalStateException("ManagementFactory error");
    }
}
