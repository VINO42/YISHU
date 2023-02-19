package io.github.vino42.web.controller.platform;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.domain.dto.DeleteIdsModel;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.RelRolePermissionGroupEntity;
import io.github.vino42.domain.entity.SysPermissionGroupEntity;
import io.github.vino42.domain.entity.SysRoleEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.IRelRolePermissionGroupService;
import io.github.vino42.service.ISysPermissionGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/22 10:29:10
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Copyright :
 * @Decription :  控制器
 * =====================================================================================
 */
@RestController
@Tag(name = "后台管理权限组管理模块")
@RequestMapping("/platform/sysPermissionGroup")
public class PlatformSysPermissionGroupController {

    @Autowired
    private ISysPermissionGroupService sysPermissionGroupService;
    @Autowired
    private IRelRolePermissionGroupService relRolePermissionGroupService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysPermissionGroupPage(Page<SysPermissionGroupEntity> page, SysPermissionGroupEntity sysPermissionGroup) {
        return ResultMapper.ok(sysPermissionGroupService.page(page, Wrappers.query(sysPermissionGroup).orderByDesc("create_time")));
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult createSysPermissionGroup(@Valid @RequestBody SysPermissionGroupEntity sysPermissionGroup, BindingResult bindingResult) {
        if (StrUtil.isBlankOrUndefined(sysPermissionGroup.getPermissionGroupCode())) {
            return ResultMapper.illegalArgument("编码不能为空");
        }
        if (StrUtil.isBlankOrUndefined(sysPermissionGroup.getPermissionGroupName())) {
            return ResultMapper.illegalArgument("名称不能为空");
        }
        String permissionGroupCode = sysPermissionGroup.getPermissionGroupCode().trim();
        SysPermissionGroupEntity byCode = sysPermissionGroupService.getByCode(permissionGroupCode);
        if (byCode != null) {
            return ResultMapper.illegalArgument("权限组编码已经存在");
        }
        sysPermissionGroup.setId(IdGenerator.nextId());
        sysPermissionGroup.setStatu(TableStatusEnum.NORMAL);
        sysPermissionGroup.setPermissionGroupCode(sysPermissionGroup.getPermissionGroupCode().trim());
        sysPermissionGroup.setCreateTime(LocalDateTime.now());
        return ResultMapper.ok(sysPermissionGroupService.save(sysPermissionGroup));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult updateSysPermissionGroup(@Valid @RequestBody SysPermissionGroupEntity sysPermissionGroup) {
        SysPermissionGroupEntity dbData = sysPermissionGroupService.getById(sysPermissionGroup.getId());
        String permissionGroupCode = sysPermissionGroup.getPermissionGroupCode();
        if (StrUtil.isNotBlank(permissionGroupCode)) {
            if (StrUtil.isBlankOrUndefined(sysPermissionGroup.getPermissionGroupName())) {
                return ResultMapper.illegalArgument("名称不能为空");
            }
            SysPermissionGroupEntity old = sysPermissionGroupService.getByCode(permissionGroupCode);
            if (old != null && !sysPermissionGroup.getId().equals(old.getId())) {
                return ResultMapper.illegalArgument("权限组编码已经存在");
            }
            sysPermissionGroup.setPermissionGroupCode(sysPermissionGroup.getPermissionGroupCode().trim());
        }
        if (dbData.getStatu().status != sysPermissionGroup.getStatu().getStatus()) {
            //更新状态
            List<RelRolePermissionGroupEntity> oldDatas = relRolePermissionGroupService.getByGroupIds(Lists.newArrayList(sysPermissionGroup.getId()));
            if (CollUtil.isNotEmpty(oldDatas)) {
                return ResultMapper.error("当前权限组正在使用中,不可禁用");
            }
        }

        return ResultMapper.ok(sysPermissionGroupService.updateById(sysPermissionGroup));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult deleteSysPermissionGroup(@RequestBody DeleteIdsModel id) {
        List<Long> ids = id.getId();
        List<RelRolePermissionGroupEntity> oldDatas = relRolePermissionGroupService.getByGroupIds(ids);
        if (CollUtil.isNotEmpty(oldDatas)) {
            return ResultMapper.error("当前权限组正在使用中,不可删除");
        }
        ids.parallelStream().forEach(d -> {
            SysPermissionGroupEntity data = new SysPermissionGroupEntity();
            data.setId(d);
            data.setStatu(TableStatusEnum.DELETED);
            sysPermissionGroupService.updateById(data);
        });
        return ResultMapper.ok();
    }

    @GetMapping(value = "/getAllPermissionGroupList")
    public ServiceResponseResult<List<ResAllocateListDto>> getAllResourceList() {
        SysPermissionGroupEntity sysUserGroup = new SysPermissionGroupEntity();
        sysUserGroup.setStatu(TableStatusEnum.NORMAL);
        QueryWrapper<SysPermissionGroupEntity> query = Wrappers.query(sysUserGroup);
        List<SysPermissionGroupEntity> list = sysPermissionGroupService.list(query);
        List<ResAllocateListDto> collect = list.stream().map(d -> {
            ResAllocateListDto resAllocateListDto = new ResAllocateListDto();
            resAllocateListDto.setId(String.valueOf(d.getId()));
            resAllocateListDto.setName(d.getPermissionGroupName());
            return resAllocateListDto;
        }).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }
}
