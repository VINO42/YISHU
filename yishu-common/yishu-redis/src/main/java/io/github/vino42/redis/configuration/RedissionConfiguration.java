package io.github.vino42.redis.configuration;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisOperations;

@Configuration
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@AutoConfigureBefore(RedissonAutoConfiguration.class)
@ConditionalOnBean({YiShuRedissonProperties.class})
public class RedissionConfiguration {

    @Bean(destroyMethod = "shutdown")
    @Primary
    @ConditionalOnMissingBean(RedissonClient.class)
    @ConditionalOnProperty(name = "redisson.server.type", havingValue = "CLUSTER")
    public RedissonClient redissonClusterClient(YiShuRedissonProperties redissonProperties) {

        Config config = new Config();
        String passwd = redissonProperties.getPasswd();
        String username = redissonProperties.getUsername();

        int scanInterval = redissonProperties.getScanInterval();
        // 创建配置
        // 指定使用集群部署方式
        ClusterServersConfig clusterServersConfig =
                config
                        .useClusterServers()
                        // 集群状态扫描间隔时间，单位是毫秒
                        .setScanInterval(scanInterval)
                        .setMasterConnectionMinimumIdleSize(10)
                        .setSlaveConnectionMinimumIdleSize(10);
        if (StringUtils.isNotBlank(passwd)) {
            // clusterServersConfig.setUsername(username);
            clusterServersConfig.setPassword(passwd);
        }
        clusterServersConfig.setConnectTimeout(30000);
        clusterServersConfig.setTimeout(6000);
        final String redisClusterNodesStr = redissonProperties.getRedisClusterNodes();
        String[] redisClusterNodes = redisClusterNodesStr.split(StrUtil.COMMA);
        clusterServersConfig.addNodeAddress(redisClusterNodes);
        RedissonClient redissonClient = Redisson.create(config);
        config.setLockWatchdogTimeout(1000 * 30);
        return redissonClient;
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    @Primary
    @ConditionalOnProperty(name = "redisson.server.type", havingValue = "MASTER_SLAVE", matchIfMissing = true)
    public RedissonClient redissoMasterSlaveClient(YiShuRedissonProperties redissonProperties) {

        Config config = new Config();
        String passwd = redissonProperties.getPasswd();
        String username = redissonProperties.getUsername();

        String masterNodes = redissonProperties.getMasterNodes();
        String slaveNodes = redissonProperties.getSlaveNodes();

        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();

        masterSlaveServersConfig
                .setMasterAddress(masterNodes)
                // 设置redis从节点
                .addSlaveAddress(slaveNodes);
        // 创建客户端(发现这一非常耗时，基本在2秒-4秒左右)
        if (StringUtils.isNotBlank(passwd)) {
            //     masterSlaveServersConfig.setUsername(username);
            masterSlaveServersConfig.setPassword(passwd);
        }
        RedissonClient masterSlaveRedisson = Redisson.create(config);

        return masterSlaveRedisson;
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    @Primary
    @ConditionalOnProperty(name = "redisson.server.type", havingValue = "SENTINEL")
    public RedissonClient redissoSentinelClient(YiShuRedissonProperties redissonProperties) {

        Config config = new Config();
        String passwd = redissonProperties.getPasswd();
        String username = redissonProperties.getUsername();
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
        String sentinelNodes = redissonProperties.getSentinelNodes();
        sentinelServersConfig.addSentinelAddress(sentinelNodes);
        if (StringUtils.isNotBlank(passwd)) {
            sentinelServersConfig.setPassword(passwd);
        }

        RedissonClient sentinelRedissonClient = Redisson.create(config);
        return sentinelRedissonClient;
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    @Primary
    @ConditionalOnProperty(name = "redisson.server.type", havingValue = "STANDALONE")
    public RedissonClient redissonClient(YiShuRedissonProperties redissonProperties) {

        Config config = new Config();
        String passwd = redissonProperties.getPasswd();
        String username = redissonProperties.getUsername();
        String redisSingleNode = redissonProperties.getRedsiSingleNode();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(redisSingleNode);
        if (StringUtils.isNotBlank(passwd)) {
            //     singleServerConfig.setUsername(username);
            singleServerConfig.setPassword(passwd);
        }
        RedissonClient redissonSingleClient = Redisson.create(config);

        return redissonSingleClient;
    }

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }
}
