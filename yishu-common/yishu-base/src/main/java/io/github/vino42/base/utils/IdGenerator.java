package io.github.vino42.base.utils;

/**
 * 基于容器hostname做machineid 的SnowFlake id生成器
 *
 * @author VINO
 * @date 2020年4月18日10:27:13
 * @see "https://chenyongjun.vip/articles/153"
 */

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.HostInfo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@UtilityClass
public class IdGenerator {
    private static Snowflake snowflake = IdUtil.getSnowflake();


    private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerator.class);

    /**
     * 开始时间, 1970-01-01. 时间戳位上并不存储实际的时间, 而是当前时间与开始时间的差值(秒)
     */
    private static final long START_TIMESTAMP =
            LocalDate.of(2020, 1, 1).atStartOfDay(ZoneId.of("Z")).toEpochSecond();

    /**
     * ID结构: 32位时间戳(秒) + 10位机器 + 16位序列号
     */
    private static final long TIMESTAMP_BIT = 32;

    private static final long MACHINE_BIT = 10;

    private static final long SEQUENCE_BIT = 16;

    /**
     * 最大机器号
     */
    private static final long MAX_MACHINE = 1 << MACHINE_BIT - 1;

    /**
     * 最大序列号
     */
    private static final long MAX_SEQUENCE = 1 << SEQUENCE_BIT - 1;

    /**
     * 机器位和时间戳左移位数.
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;

    private static final long TIMESTAMP_LEFT = MACHINE_BIT + SEQUENCE_BIT;

    /**
     * 机器标识
     */
    private static long machineId = 1;

    private static boolean machineInitialized = false;

    /**
     * 序列号
     */
    private static long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private static long lastTimestamp = -1L;

    static {
        initMachineId();
    }

    /**
     * 获取分布式ID
     *
     * @return
     */
    public static long nextId() {

        return nextId(getNowSeconds());
    }

    public static long nextSnowFlakeId() {
        long l = snowflake.nextId();
        String substring = StrUtil.toString(l).substring(1, 19);
        return Convert.toLong(substring);
    }


    /**
     * 获取分布式ID
     *
     * @return
     */
    public static String nextIdStr() {

        return String.valueOf(nextId(getNowSeconds()));
    }

    private static synchronized long nextId(long nowSeconds) {

        long currentTimestamp = nowSeconds;
        // 当前时间异常, 自动校准
        if (currentTimestamp < lastTimestamp) {
            currentTimestamp = lastTimestamp;
        }
        // 相同秒内，序列号自增
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一秒的序列数已经达到最大, 获取下5秒的ID
            if (sequence == 0L) {
                return nextId(nowSeconds + 5);
            }
        } else {
            // 不同秒内，序列号置重置为0, 重新计数
            sequence = 0L;
        }
        lastTimestamp = currentTimestamp;
        // 当前时间与开始时间的差值
        long timeDelta = currentTimestamp - START_TIMESTAMP;
        // 各段结构拼接("|"操作)成ID
        return timeDelta << TIMESTAMP_LEFT | machineId << MACHINE_LEFT | sequence;
    }

    private static long getNowSeconds() {

        return System.currentTimeMillis() / 1000;
    }

    /**
     * 通过hostname初始化机器ID, 将hostname各字符累加并对machineId最大值取余
     *
     * <p>虚拟主机: 可手工设置, eg:prod1
     *
     * <p>普通容器: 默认容器ID为HOSTNAME, eg: b88598ebca38
     *
     * <p>K8s Pod: 默认Pod ID为HOSTNAME, eg: user-service-433cf-5f6d48c7f4-8fclh
     *
     * @return
     */
    private static void initMachineId() {

        try {
            if (!machineInitialized) {
                String hostname = new HostInfo().getName();
                char[] hostnameChars = hostname.toCharArray();
                int sumOfHostname = 0;
                for (int i = 0, length = hostnameChars.length; i < length; i++) {
                    sumOfHostname += hostnameChars[i];
                }
                machineId = sumOfHostname % MAX_MACHINE;
                LOGGER.debug(
                        "====> Init machineId. hostname[" + hostname + "], machineId[" + machineId + "]");
            }
        } finally {
            machineInitialized = true;
        }
    }

    /**
     * 覆盖默认machineID
     *
     * @param mid machineId
     */
    public static void setMachineId(long mid) {

        if (mid > 0) {
            machineId = mid;
        }
    }

}
