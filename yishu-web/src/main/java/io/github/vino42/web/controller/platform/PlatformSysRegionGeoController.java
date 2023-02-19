package io.github.vino42.web.controller.platform;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.domain.entity.SysRegionGeoEntity;
import io.github.vino42.service.ISysRegionGeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:22:02
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : vino42
 * @Decription : 系统行政区地理信息表 控制器
 * =====================================================================================
 */
@RestController
@RequestMapping("/platform/regionGeo")
public class PlatformSysRegionGeoController {

    @Autowired
    private ISysRegionGeoService sysRegionGeoService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysRegionGeoEntityPage(Page<SysRegionGeoEntity> page, SysRegionGeoEntity sysRegionGeo) {
        return ResultMapper.ok(sysRegionGeoService.page(page, Wrappers.query(sysRegionGeo).orderByDesc("create_time")));
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult create(@Valid @RequestBody SysRegionGeoEntity sysRegionGeo, BindingResult bindingResult) {
        return ResultMapper.ok(sysRegionGeoService.save(sysRegionGeo));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult update(@Valid @RequestBody SysRegionGeoEntity sysRegionGeo, BindingResult bindingResult) {
        return ResultMapper.ok(sysRegionGeoService.updateById(sysRegionGeo));
    }

}
