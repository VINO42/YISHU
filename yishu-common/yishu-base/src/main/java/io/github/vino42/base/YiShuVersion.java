package io.github.vino42.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/14 16:05
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright :
 * @Decription : 定义版本号基础类
 * =====================================================================================
 */
public class YiShuVersion {
    private static final Log logger = LogFactory.getLog(YiShuVersion.class);

    public static final long SERIAL_VERSION_UID = 1L;

    static final String MIN_SPRING_VERSION = getYiShuVersion();


    public static String getYiShuVersion() {
        Properties properties = new Properties();
        try {
            properties.load(YiShuVersion.class.getClassLoader().getResourceAsStream("META-INF/yishu.versions"));
        } catch (IOException | NullPointerException e) {
            return null;
        }
        return properties.getProperty("io.github.vino42:yishu.version");
    }
}
