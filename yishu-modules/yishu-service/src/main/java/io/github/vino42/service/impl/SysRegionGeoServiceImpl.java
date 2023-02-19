package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.SysRegionGeoMapper;
import io.github.vino42.domain.entity.SysRegionGeoEntity;
import io.github.vino42.service.ISysRegionGeoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:19
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 系统行政区地理信息表 服务实现类
 * =====================================================================================
 */
@Service
public class SysRegionGeoServiceImpl extends ServiceImpl<SysRegionGeoMapper, SysRegionGeoEntity> implements ISysRegionGeoService {

    @Override
    public List<SysRegionGeoEntity> getCities() {
        return this.baseMapper.getCities();
    }

    @Override
    public SysRegionGeoEntity getCityCodeByLoLa(String longitude, String latitude) {
        return this.baseMapper.getCityCodeByLoLa(longitude, latitude);
    }
}
