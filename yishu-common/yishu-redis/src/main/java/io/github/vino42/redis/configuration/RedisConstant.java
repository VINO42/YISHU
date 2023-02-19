package io.github.vino42.redis.configuration;

/**
 * @author VINO
 * @date 2020/4/15 9:59
 * @describe redis 缺省 常量池
 */
public interface RedisConstant {

    /**
     * 默认服务器类型为单机
     */
    String DEFAUL_SERVER_TYPE = "STANDALONE";

    /**
     * 默认端口
     */
    Integer DEFAULT_REDIS_PORT = 6379;

    /**
     * 默认host
     */
    String DEFAULT_REDIS_HOST = "localhost";

    /**
     * 最大空闲
     */
    Integer DEFAULT_MAX_IDLE = 8;

    /**
     * 最小空闲
     */
    Integer DEFAULT_MIN_IDLE = 1;

    /**
     * 最大active
     */
    Integer DEFAULT_MAX_ACTIVE = 200;

    /**
     * TIME_BETWEEN_EVICTION_RUNS
     */
    Long DEFAULT_TIME_BETWEEN_EVICTION_RUNS = 2000L;

    /**
     * 默认 MAX_WAIT_MILLIS
     */
    Long DEFAULT_MAX_WAIT_MILLIS = 5000L;

    /**
     * cluster默认 MAX_REDIRECTS
     */
    Integer DEFAULT_MAX_REDIRECTS = 3;

    /**
     * DEFAULT_COMMAND_TIMEOUT_SENCONDS
     */
    Long DEFAULT_COMMAND_TIMEOUT_SENCONDS = 60L;

    /**
     * DEFAULT_SHUTDOWN_TIMEOUT_SENCONDS
     */
    Long DEFAULT_SHUTDOWN_TIMEOUT_SENCONDS = 100L;

    /**
     * DEFAULT REDIS CACHE TTL
     */
    Long DEFAULT_CACHE_TTL_HOUR = 1L;
}
