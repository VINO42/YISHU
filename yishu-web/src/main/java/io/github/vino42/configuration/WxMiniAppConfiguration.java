package io.github.vino42.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedissonConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Configuration
@Slf4j
public class WxMiniAppConfiguration  implements ApplicationRunner {
    @Value("${yishu.miniapp.secret:}")
    private String miniappSecret;
    @Value("${yishu.miniapp.appid:}")
    private String miniappAppId;

    private static Map<String, WxMaMessageRouter> routers = Maps.newHashMap();

    private static Map<String, WxMaService> maServices = Maps.newHashMap();

    private RedissonClient redissonClient;

    @Autowired
    @Lazy
    public WxMiniAppConfiguration(
            RedissonClient redissonClient) {

        this.redissonClient = redissonClient;
    }

    public static Map<String, WxMaMessageRouter> getRouters() {

        return routers;
    }

    public static Map<String, WxMaService> getMaServices() {

        return maServices;
    }


    public WxMaMessageRouter newRouter(WxMaService wxMpService) {

        final WxMaMessageRouter newRouter = new WxMaMessageRouter(wxMpService);

        return newRouter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WxMaRedissonConfigImpl configStorage =
                new WxMaRedissonConfigImpl(redissonClient);
        log.info("[store wxMp:appId::{},config start]", miniappAppId);
        configStorage.setAppid(miniappAppId);
        configStorage.setSecret(miniappSecret);
        configStorage.setToken(EMPTY);
        configStorage.setAesKey(EMPTY);
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(configStorage);
        routers.put(miniappAppId, this.newRouter(service));

        maServices = Maps.newHashMap();
        maServices.put(miniappAppId, service);
    }
}
