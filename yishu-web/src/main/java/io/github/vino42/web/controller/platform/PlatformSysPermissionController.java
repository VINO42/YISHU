package io.github.vino42.web.controller.platform;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.domain.dto.DeleteIdsModel;
import io.github.vino42.domain.entity.SysPermissionEntity;
import io.github.vino42.domain.entity.SysPermissionGroupEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.ISysPermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.ILLEGAL_ARGS;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:28:05
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : 38912428@qq.com
 * @Decription :  控制器
 * =====================================================================================
 */
@RestController
@Tag(name = "后台管理权限管理模块")
@RequestMapping("/platform/sysPermission")
public class PlatformSysPermissionController {

    @Autowired
    private ISysPermissionService sysPermissionService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysPermissionPage(Page<SysPermissionEntity> page, SysPermissionEntity sysPermission) {
        return ResultMapper.ok(sysPermissionService.page(page, Wrappers.query(sysPermission).orderByDesc("create_time")));
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult createSysPermission(@Valid @RequestBody SysPermissionEntity sysPermission, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            return ResultMapper.error(ILLEGAL_ARGS.status, StringUtils.isBlank(defaultMessage) ? ILLEGAL_ARGS.message : defaultMessage);
        }
        return ResultMapper.ok(sysPermissionService.save(sysPermission));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult updateSysPermission(@Valid @RequestBody SysPermissionEntity sysPermission, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            return ResultMapper.error(ILLEGAL_ARGS.status, StringUtils.isBlank(defaultMessage) ? ILLEGAL_ARGS.message : defaultMessage);
        }
        return ResultMapper.ok(sysPermissionService.updateById(sysPermission));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult deleteSysPermission(@RequestBody DeleteIdsModel id) {
        List<Long> ids = id.getId();
        ids.parallelStream().forEach(d -> {
            SysPermissionEntity data = new SysPermissionEntity();
            data.setId(d);
            data.setStatu(TableStatusEnum.DELETED);
            sysPermissionService.updateById(data);
        });
        return ResultMapper.ok();
    }
}
