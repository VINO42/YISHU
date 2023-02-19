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
import io.github.vino42.domain.entity.RelUserGroupRoleEntity;
import io.github.vino42.domain.entity.SysRoleEntity;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.IRelUserGroupRoleService;
import io.github.vino42.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:27:57
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : 38912428@qq.com
 * @Decription :  控制器
 * =====================================================================================
 */
@RestController
@Tag(name = "后台管理角色管理模块")
@RequestMapping("/platform/sysRole")
public class PlatformSysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private IRelUserGroupRoleService relUserGroupRoleService;

    @GetMapping(value = "/page")
    @Parameter(name = "page", description = "分页参数")
    @Operation(summary = "后台管理角色列表")
    public ServiceResponseResult<IPage> getSysRolePage(Page<SysRoleEntity> page, SysRoleEntity sysRole) {
        return ResultMapper.ok(sysRoleService.page(page, Wrappers.query(sysRole).orderByDesc("create_time")));
    }

    @GetMapping(value = "/getAllocateUserRoleList")
    public ServiceResponseResult<List<ResAllocateListDto>> getAllocateUserRoleList() {
        SysRoleEntity sysUserGroup = new SysRoleEntity();
        sysUserGroup.setStatu(TableStatusEnum.NORMAL);
        QueryWrapper<SysRoleEntity> query = Wrappers.query(sysUserGroup);
        List<SysRoleEntity> list = sysRoleService.list(query);
        List<ResAllocateListDto> collect = list.stream().map(d -> {
            ResAllocateListDto resAllocateListDto = new ResAllocateListDto();
            resAllocateListDto.setId(String.valueOf(d.getId()));
            resAllocateListDto.setName(d.getRoleName());
            return resAllocateListDto;
        }).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }

    @PostMapping(value = "/add")
    public ServiceResponseResult createSysRole(@Valid @RequestBody SysRoleEntity sysRole) {
        if (StrUtil.isBlankOrUndefined(sysRole.getRoleCode())) {
            return ResultMapper.illegalArgument("编码不能为空");
        }
        if (StrUtil.isBlankOrUndefined(sysRole.getRoleName())) {
            return ResultMapper.illegalArgument("名称不能为空");
        }
        SysRoleEntity roleEntity = sysRoleService.getRoleByCode(sysRole.getRoleCode().trim());
        if (roleEntity != null) {
            return ResultMapper.illegalArgument("角色编码已经存在");
        }
        sysRole.setStatu(TableStatusEnum.NORMAL);
        sysRole.setId(IdGenerator.nextId());
        sysRole.setRoleCode(sysRole.getRoleCode().trim());
        sysRole.setCreateTime(LocalDateTime.now());
        return ResultMapper.ok(sysRoleService.save(sysRole));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult updateSysRole(@Valid @RequestBody SysRoleEntity sysRole) {
        SysRoleEntity dbData = sysRoleService.getById(sysRole.getId());
        if (StrUtil.isNotBlank(sysRole.getRoleCode())) {
            if (StrUtil.isBlankOrUndefined(sysRole.getRoleName())) {
                return ResultMapper.illegalArgument("名称不能为空");
            }
            sysRole.setRoleCode(sysRole.getRoleCode().trim());
            SysRoleEntity old = sysRoleService.getRoleByCode(sysRole.getRoleCode());
            if (old != null && !sysRole.getId().equals(old.getId())) {
                return ResultMapper.illegalArgument("角色编码已经存在");
            }
        }

        if (dbData.getStatu().status != sysRole.getStatu().getStatus()) {
            //更新状态
            List<RelUserGroupRoleEntity> oldDatas = relUserGroupRoleService.selectByRoleIds(Lists.newArrayList(sysRole.getId()));
            if (CollUtil.isNotEmpty(oldDatas)) {
                return ResultMapper.error("当前角色正在使用中,不可禁用");
            }
        }

        return ResultMapper.ok(sysRoleService.updateById(sysRole));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult deleteSysRole(@RequestBody DeleteIdsModel id) {
        List<Long> roleIds = id.getId();
        List<RelUserGroupRoleEntity> oldDatas = relUserGroupRoleService.selectByRoleIds(roleIds);
        if (CollUtil.isNotEmpty(oldDatas)) {
            return ResultMapper.error("当前角色正在使用中,不可删除");
        }
        roleIds.parallelStream().forEach(d -> {
            SysRoleEntity data = new SysRoleEntity();
            data.setId(d);
            data.setStatu(TableStatusEnum.DELETED);
            sysRoleService.updateById(data);
        });
        return ResultMapper.ok();
    }
}
