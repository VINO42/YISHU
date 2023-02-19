package io.github.vino42.web.controller.wechat;

import com.alicp.jetcache.anno.Cached;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.domain.dto.WechatUserLoginDto;
import io.github.vino42.domain.entity.SysRegionGeoEntity;
import io.github.vino42.service.ISysRegionGeoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * =====================================================================================
 *
 * @Created :   2023/1/3 20:24
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Slf4j
@RestController
@Tag(name = "微信端管理登录模块")
@RequestMapping("/wechat/common")
public class WechatCommonController {
    @Autowired
    ISysRegionGeoService sysRegionGeoService;
    @Value("${miniapp.check.flag:false}")
    private String flag;

    @PostMapping(value = "/check")
    public ServiceResponseResult tableBar() {
        return ResultMapper.ok(Boolean.valueOf(flag));
    }

    @PostMapping(value = "/cities")
    @Cached(name = "ybs:cache:cities:", key = "1", expire = 86400)
    public ServiceResponseResult cities() {
        List<SysRegionGeoEntity> list = sysRegionGeoService.getCities();
        list = list.stream().filter(d -> !d.getName().contains("县")).map(s -> {
            if (s.getName().endsWith("市")) {
                s.setName(s.getName().replace("市", ""));
//                s.setName(s.getName().replace("自治州", ""));
            }
            return s;
        }).collect(Collectors.toList());
        Map<String, List<SysRegionGeoEntity>> stringListMap = assembleHotCities();

        Map<String, List<SysRegionGeoEntity>> alphabetRegion = list.stream().collect(
                Collectors.groupingBy(d -> d.getPinyin().substring(0, 1).toUpperCase())
        );

        stringListMap.putAll(alphabetRegion);

        return ResultMapper.ok(stringListMap);
    }

    private Map<String, List<SysRegionGeoEntity>> assembleHotCities() {
        Map<String, List<SysRegionGeoEntity>> alphabetRegion = Maps.newLinkedHashMap();
        SysRegionGeoEntity contry1 = new SysRegionGeoEntity();
        contry1.setName("全国");
        contry1.setPinyin("-热门城市");
        contry1.setId(-1L);
        SysRegionGeoEntity contry2 = new SysRegionGeoEntity();
        contry2.setName("北京");
        contry2.setPinyin("-热门城市");
        contry2.setId(1101L);
        SysRegionGeoEntity contry3 = new SysRegionGeoEntity();
        contry3.setName("上海");
        contry3.setPinyin("-热门城市");
        contry3.setId(3101L);
        SysRegionGeoEntity contry4 = new SysRegionGeoEntity();
        contry4.setName("广州");
        contry4.setPinyin("-热门城市");
        contry4.setId(4401L);
        SysRegionGeoEntity contry5 = new SysRegionGeoEntity();
        contry5.setName("深圳");
        contry5.setPinyin("-热门城市");
        contry5.setId(4403L);
        SysRegionGeoEntity contry6 = new SysRegionGeoEntity();
        contry6.setName("杭州");
        contry6.setPinyin("-热门城市");
        contry6.setId(3301L);
        SysRegionGeoEntity contry7 = new SysRegionGeoEntity();
        contry7.setName("重庆");
        contry7.setPinyin("-热门城市");
        contry7.setId(5001L);
        SysRegionGeoEntity contry8 = new SysRegionGeoEntity();
        contry8.setName("天津");
        contry8.setPinyin("-热门城市");
        contry8.setId(1201L);
        SysRegionGeoEntity contry9 = new SysRegionGeoEntity();
        contry9.setName("成都");
        contry9.setPinyin("-热门城市");
        contry9.setId(5101L);
        SysRegionGeoEntity contry10 = new SysRegionGeoEntity();
        contry10.setName("武汉");
        contry10.setPinyin("-热门城市");
        contry10.setId(4201L);
        ArrayList<SysRegionGeoEntity> sysRegionGeoEntities = Lists.newArrayList();
        sysRegionGeoEntities.add(contry1);
        sysRegionGeoEntities.add(contry2);
        sysRegionGeoEntities.add(contry3);
        sysRegionGeoEntities.add(contry4);
        sysRegionGeoEntities.add(contry5);
        sysRegionGeoEntities.add(contry6);
        sysRegionGeoEntities.add(contry7);
        sysRegionGeoEntities.add(contry8);
        sysRegionGeoEntities.add(contry9);
        sysRegionGeoEntities.add(contry10);
        alphabetRegion.put("-热门城市", sysRegionGeoEntities);
        return alphabetRegion;
    }

    @PostMapping("/geo")
    public ServiceResponseResult<SysRegionGeoEntity> getRegion(@RequestBody WechatUserLoginDto wechatUserLoginDto) throws WxErrorException {
        SysRegionGeoEntity cityCodeByLoLa = sysRegionGeoService.getCityCodeByLoLa(wechatUserLoginDto.getLongitude(), wechatUserLoginDto.getLatitude());
        if (cityCodeByLoLa.getName().endsWith("市") || cityCodeByLoLa.getName().endsWith("自治州")) {
            cityCodeByLoLa.setName(cityCodeByLoLa.getName().replace("市", ""));
            cityCodeByLoLa.setName(cityCodeByLoLa.getName().replace("自治州", ""));
        }
        return ResultMapper.ok(cityCodeByLoLa);
    }
}
