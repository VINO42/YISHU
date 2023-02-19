package io.github.vino42.web.controller.platform;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.domain.dto.DeleteIdsModel;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.SysPermissionGroupEntity;
import io.github.vino42.domain.entity.SysResourceEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.ISysResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.ILLEGAL_ARGS;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:28:00
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : 38912428@qq.com
 * @Decription :  控制器
 * =====================================================================================
 */
@RestController
@RequestMapping("/platform/sysResource")
@Tag(name = "后台管理资源管理模块")
public class PlatformSysResourceController {

    @Autowired
    private ISysResourceService sysResourceService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysResourcePage(Page<SysResourceEntity> page, SysResourceEntity sysResource) {
        return ResultMapper.ok(sysResourceService.page(page, Wrappers.query(sysResource).orderByDesc("create_time")));
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult createSysResource(@Valid @RequestBody SysResourceEntity sysResource, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            return ResultMapper.error(ILLEGAL_ARGS.status, StringUtils.isBlank(defaultMessage) ? ILLEGAL_ARGS.message : defaultMessage);
        }
        return ResultMapper.ok(sysResourceService.save(sysResource));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult updateSysResource(@Valid @RequestBody SysResourceEntity sysResource, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            return ResultMapper.error(ILLEGAL_ARGS.status, StringUtils.isBlank(defaultMessage) ? ILLEGAL_ARGS.message : defaultMessage);
        }
        return ResultMapper.ok(sysResourceService.updateById(sysResource));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult deleteSysResource(@RequestBody DeleteIdsModel id) {
        List<Long> ids = id.getId();
        ids.parallelStream().forEach(d -> {
            SysResourceEntity data = new SysResourceEntity();
            data.setId(d);
            data.setStatu(TableStatusEnum.DELETED);
            sysResourceService.updateById(data);
        });
        return ResultMapper.ok();
    }

    @GetMapping(value = "/getAllResourceList")
    public ServiceResponseResult<List<ResAllocateListDto>> getAllResourceList() {
        SysResourceEntity sysUserGroup = new SysResourceEntity();
        sysUserGroup.setStatu(TableStatusEnum.NORMAL);
        QueryWrapper<SysResourceEntity> query = Wrappers.query(sysUserGroup);
        List<SysResourceEntity> list = sysResourceService.list(query);
        List<ResAllocateListDto> collect = list.stream().map(d -> {
            ResAllocateListDto resAllocateListDto = new ResAllocateListDto();
            resAllocateListDto.setId(String.valueOf(d.getId()));
            resAllocateListDto.setName(d.getName());
            return resAllocateListDto;
        }).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }
}
