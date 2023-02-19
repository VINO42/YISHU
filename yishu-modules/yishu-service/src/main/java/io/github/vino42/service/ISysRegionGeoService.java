package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.SysRegionGeoEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:19
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 系统行政区地理信息表 服务类
 * =====================================================================================
 */
public interface ISysRegionGeoService extends IService<SysRegionGeoEntity> {
    List<SysRegionGeoEntity> getCities();

    SysRegionGeoEntity getCityCodeByLoLa(String longitude, String latitude);
}
