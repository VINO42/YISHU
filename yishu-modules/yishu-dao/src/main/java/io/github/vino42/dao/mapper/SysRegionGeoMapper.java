package io.github.vino42.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.vino42.domain.entity.SysRegionGeoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:18:35
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 系统行政区地理信息表 Mapper 接口
 * =====================================================================================
 */
@Mapper
public interface SysRegionGeoMapper extends BaseMapper<SysRegionGeoEntity> {
    List<SysRegionGeoEntity> getCities();

    SysRegionGeoEntity getCityCodeByLoLa(@Param("longitude") String longitude, @Param("latitude") String latitude);


}
