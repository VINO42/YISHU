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
import io.github.vino42.domain.entity.RelUserUserGroupEntity;
import io.github.vino42.domain.entity.SysUserGroupEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.IRelUserUserGroupService;
import io.github.vino42.service.ISysUserGroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/22 10:28:43
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Copyright :
 * @Decription :  控制器
 * =====================================================================================
 */
@RestController
@Tag(name = "后台管理用户组管理模块")
@RequestMapping("/platform/sysUserGroup")
public class PlatformSysUserGroupController {
    @Autowired
    private IRelUserUserGroupService relUserUserGroupService;
    @Autowired
    private ISysUserGroupService sysUserGroupService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysUserGroupPage(Page<SysUserGroupEntity> page, SysUserGroupEntity sysUserGroup) {
        return ResultMapper.ok(sysUserGroupService.page(page, Wrappers.query(sysUserGroup).orderByDesc("create_time")));
    }

    @GetMapping(value = "/getAllocateUserGroupList")
    public ServiceResponseResult<List<ResAllocateListDto>> getAllocateUserGroupList() {
        SysUserGroupEntity sysUserGroup = new SysUserGroupEntity();
        sysUserGroup.setStatu(TableStatusEnum.NORMAL);
        QueryWrapper<SysUserGroupEntity> query = Wrappers.query(sysUserGroup);
        List<SysUserGroupEntity> list = sysUserGroupService.list(query);
        List<ResAllocateListDto> collect = list.stream().map(d -> {
            ResAllocateListDto resAllocateListDto = new ResAllocateListDto();
            resAllocateListDto.setId(String.valueOf(d.getId()));
            resAllocateListDto.setName(d.getUserGroupName());
            return resAllocateListDto;
        }).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }

    @PostMapping(value = "/add")
    public ServiceResponseResult createSysUserGroup(@Valid @RequestBody SysUserGroupEntity sysUserGroup) {
        if (StrUtil.isBlankOrUndefined(sysUserGroup.getUserGroupCode())) {
            return ResultMapper.illegalArgument("编码不能为空");
        }
        if (StrUtil.isBlankOrUndefined(sysUserGroup.getUserGroupName())) {
            return ResultMapper.illegalArgument("名称不能为空");
        }
        sysUserGroup.setUserGroupCode(sysUserGroup.getUserGroupCode().trim());
        SysUserGroupEntity sysUserGroupEntity = sysUserGroupService.getByCode(sysUserGroup.getUserGroupCode());
        if (sysUserGroupEntity != null) {
            return ResultMapper.illegalArgument("用户组编码已经存在");
        }
        sysUserGroup.setId(IdGenerator.nextId());
        sysUserGroup.setStatu(TableStatusEnum.NORMAL);
        sysUserGroup.setVersionStamp(System.currentTimeMillis());
        return ResultMapper.ok(sysUserGroupService.save(sysUserGroup));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult updateSysUserGroup(@Valid @RequestBody SysUserGroupEntity sysUserGroup) {
        SysUserGroupEntity dbData = sysUserGroupService.getById(sysUserGroup.getId());
        if (StrUtil.isNotBlank(sysUserGroup.getUserGroupCode())) {
            if (StrUtil.isBlankOrUndefined(sysUserGroup.getUserGroupName())) {
                return ResultMapper.illegalArgument("名称不能为空");
            }
            sysUserGroup.setUserGroupCode(sysUserGroup.getUserGroupCode().trim());
            SysUserGroupEntity old = sysUserGroupService.getByCode(sysUserGroup.getUserGroupCode());
            if (old != null && !old.getId().equals(sysUserGroup.getId())) {
                return ResultMapper.illegalArgument("用户组编码已经存在");
            }
        }

        if (dbData.getStatu().status != sysUserGroup.getStatu().getStatus()) {
            //更新状态
            List<RelUserUserGroupEntity> oldDatas = relUserUserGroupService.selectByGroupIds(Lists.newArrayList(sysUserGroup.getId()));
            if (CollUtil.isNotEmpty(oldDatas)) {
                return ResultMapper.error("当前用户组正在使用中,不可禁用");
            }
        }

        return ResultMapper.ok(sysUserGroupService.updateById(sysUserGroup));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult deleteSysUserGroup(@RequestBody DeleteIdsModel id) {
        List<Long> groupIds = id.getId();
        List<RelUserUserGroupEntity> oldDatas = relUserUserGroupService.selectByGroupIds(groupIds);
        if (CollUtil.isNotEmpty(oldDatas)) {
            return ResultMapper.error("当前用户组正在使用中,不可删除");
        }
        groupIds.parallelStream().forEach(d -> {
            SysUserGroupEntity sysUserGroupEntity = new SysUserGroupEntity();
            sysUserGroupEntity.setId(d);
            sysUserGroupEntity.setStatu(TableStatusEnum.DELETED);
            sysUserGroupService.updateById(sysUserGroupEntity);
        });
        return ResultMapper.ok();
    }
}
