package io.github.vino42.redis.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author VINO
 */
@Data
@Configuration
public class YiShuRedissonProperties {

    /**
     * 集群模式 STANDALONE,CLUSTER,SENTINEL 重要参数
     */
    @Value("${redisson.server.type:STANDALONE}")
    private String serverType;

    @Value("${spring.redisson.standalone.node:redis://127.0.0.1:6379}")
    private String redsiSingleNode;

    /**
     * 集群节点列表
     */
    @Value("${spring.redisson.cluster.nodes:}")
    private String redisClusterNodes;

    /**
     * redisson 集群扫描
     */
    @Value("${spring.redisson.cluster.scanInterval:3000}")
    private int scanInterval;

    /**
     * 主从主节点列表 redis://127.0.0.1:6379, rediss://127.0.0.1:6379,
     */
    @Value("${spring.redisson.master_slave.masterNodes:}")
    private String masterNodes;

    /**
     * 主从从节点列表
     */
    @Value("${spring.redisson.master_slave.slaveNodes:}")
    private String slaveNodes;

    /**
     * 哨兵模式节点列表
     */
    @Value("${spring.redisson.sentinel.nodes:}")
    private String sentinelNodes;

    @Value("${spring.redisson.passwd:}")
    private String passwd;

    @Value("${spring.redisson.username:root}")
    private String username;
}
