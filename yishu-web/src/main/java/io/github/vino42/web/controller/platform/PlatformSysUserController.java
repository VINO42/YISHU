package io.github.vino42.web.controller.platform;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.auth.service.AuthService;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.domain.dto.AlocateUserGroupOrRoleDTO;
import io.github.vino42.domain.dto.DeleteIdsModel;
import io.github.vino42.domain.dto.ResAllocateListDto;
import io.github.vino42.domain.entity.RelRolePermissionGroupEntity;
import io.github.vino42.domain.entity.RelUserGroupRoleEntity;
import io.github.vino42.domain.entity.RelUserUserGroupEntity;
import io.github.vino42.domain.entity.SysUserEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.vino42.base.constants.YiShuConstant.PHONE_CAN_SEND_SMS_REGEX;
import static io.github.vino42.base.response.ServiceResponseCodeEnum.ILLEGAL_ARGS;
import static io.github.vino42.base.response.ServiceResponseCodeEnum.USER_ALREADY_EXIST;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/16 23:26:13
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Copyright : 38912428@qq.com
 * @Decription : 用户表 控制器
 * =====================================================================================
 */
@RestController
@Tag(name = "后台管理用户管理模块")
@RequestMapping("/platform/sysUser")
public class PlatformSysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IRelUserUserGroupService relUserUserGroupService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysResourceService resourceService;
    @Autowired
    private IRelUserGroupRoleService relUserGroupRoleService;
    @Autowired
    private IRelRolePermissionGroupService relRolePermissionGroupService;

    @Autowired
    private AuthService authService;
    @Autowired
    private ISysPermissionGroupService sysPermissionGroupService;

    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysUserPage(Page<SysUserEntity> page, SysUserEntity sysUser) {
        return ResultMapper.ok(sysUserService.page(page, Wrappers.query(sysUser).orderByDesc("create_time")));
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult createSysUser(@Valid @RequestBody SysUserEntity sysUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
            return ResultMapper.error(ILLEGAL_ARGS.status, StringUtils.isBlank(defaultMessage) ? ILLEGAL_ARGS.message : defaultMessage);
        }
        SysUserEntity userByUserMobile = sysUserService.getUserByUserMobile(sysUser.getMobile());
        if (userByUserMobile != null) {
            return ResultMapper.error(USER_ALREADY_EXIST);
        }
        String mobile = sysUser.getMobile();
        boolean isMobile = Validator.isMatchRegex(PHONE_CAN_SEND_SMS_REGEX, mobile);
        if (!isMobile) {
            return ResultMapper.illegalArgument("请输入正确格式的手机号");
        }
        return ResultMapper.ok(sysUserService.save(sysUser));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult updateSysUser(@Valid @RequestBody SysUserEntity sysUser) {

        SysUserEntity oldData = sysUserService.getById(sysUser.getId());
        if (oldData.getStatu().status == sysUser.getStatu().getStatus()) {
            SysUserEntity userByUserMobile = sysUserService.getUserByUserMobile(sysUser.getMobile());
            if (userByUserMobile != null && !userByUserMobile.getId().equals(sysUser.getId())) {
                return ResultMapper.error(USER_ALREADY_EXIST);
            }
            String mobile = sysUser.getMobile();
            boolean isMobile = Validator.isMatchRegex(PHONE_CAN_SEND_SMS_REGEX, mobile);
            if (!isMobile) {
                return ResultMapper.illegalArgument("请输入正确格式的手机号");
            }
        }
        return ResultMapper.ok(sysUserService.updateById(sysUser));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult deleteSysUser(@RequestBody DeleteIdsModel id) {
        List<String> ids = id.getId().stream().map(d -> String.valueOf(id.getId())).collect(Collectors.toList());
        boolean b = sysUserService.removeBatchByIds(id.getId());
        if (b) {
            authService.delTokens(ids);
        }
        return ResultMapper.ok(b);
    }


    @PostMapping(value = "/alocateUserGroup")
    public ServiceResponseResult alocateUserGroup(@RequestBody AlocateUserGroupOrRoleDTO alocateUserGroupDTO) {
        if (CollUtil.isEmpty(alocateUserGroupDTO.getIds())) {
            return ResultMapper.illegalArgument("请选择用户组");
        }
        List<String> groupIds = alocateUserGroupDTO.getIds();
        relUserUserGroupService.removeUserGroupByUserId(Long.valueOf(alocateUserGroupDTO.getUserId()));
        List<RelUserUserGroupEntity> list = groupIds.stream().map(groupId -> {
            RelUserUserGroupEntity userUserGroupEntity = new RelUserUserGroupEntity();
            userUserGroupEntity.setUserGroupId(Long.valueOf(groupId));
            userUserGroupEntity.setUserId(Long.valueOf(alocateUserGroupDTO.getUserId()));
            userUserGroupEntity.setStatu(TableStatusEnum.NORMAL);
            userUserGroupEntity.setId(IdGenerator.nextId());
            return userUserGroupEntity;
        }).collect(Collectors.toList());
        boolean b = relUserUserGroupService.saveBatch(list);
        return ResultMapper.ok(b);
    }

    @PostMapping(value = "/alocateUserRole")
    public ServiceResponseResult alocateUserRole(@RequestBody AlocateUserGroupOrRoleDTO alocateUserRoleDTO) {
        if (CollUtil.isEmpty(alocateUserRoleDTO.getIds())) {
            return ResultMapper.illegalArgument("请选择角色组");
        }
        //将原来的关联关系删除
        //将新的关联关系加入
        //将原来的用户全部下线
        List<String> roleIds = alocateUserRoleDTO.getIds();
        List<String> userIds = relUserGroupRoleService.selectUserIdsByUserGroup(alocateUserRoleDTO.getUserId());
        relUserGroupRoleService.removeUserGroupRoleIdByUserId(alocateUserRoleDTO.getUserId());
        List<RelUserGroupRoleEntity> list = roleIds.stream().map(roleId -> {
            RelUserGroupRoleEntity userUserGroupEntity = new RelUserGroupRoleEntity();
            userUserGroupEntity.setRoleId(Long.valueOf(roleId));
            userUserGroupEntity.setUserGroupId(Long.valueOf(alocateUserRoleDTO.getUserId()));
            userUserGroupEntity.setStatu(TableStatusEnum.NORMAL);
            userUserGroupEntity.setId(IdGenerator.nextId());
            return userUserGroupEntity;
        }).collect(Collectors.toList());
        boolean b = relUserGroupRoleService.saveBatch(list);
        boolean b1 = authService.delTokens(userIds);
        return ResultMapper.ok(b & b1);
    }

    @PostMapping(value = "/alocatRolePermissionGroup")
    public ServiceResponseResult alocatRolePermissionGroup(@RequestBody AlocateUserGroupOrRoleDTO alocateUserRoleDTO) {
        if (CollUtil.isEmpty(alocateUserRoleDTO.getIds())) {
            return ResultMapper.illegalArgument("请选择资源组");
        }
        //将原来的关联关系删除
        //将新的关联关系加入
        //将原来的用户全部下线
        List<String> permissionGroupIds = alocateUserRoleDTO.getIds();
        List<String> userIds = relUserGroupRoleService.selectUserIdsByUserGroup(alocateUserRoleDTO.getUserId());
        relRolePermissionGroupService.removeRolePermissionGroupByRoleId(String.valueOf(alocateUserRoleDTO.getUserId()));
        List<RelRolePermissionGroupEntity> list = permissionGroupIds.stream().map(permissionGroupId -> {
            RelRolePermissionGroupEntity userUserGroupEntity = new RelRolePermissionGroupEntity();
            userUserGroupEntity.setRoleId(Long.valueOf(alocateUserRoleDTO.getUserId()));
            userUserGroupEntity.setPermissionGroupId(Long.valueOf(permissionGroupId));
            userUserGroupEntity.setStatu(TableStatusEnum.NORMAL);
            userUserGroupEntity.setId(IdGenerator.nextId());
            return userUserGroupEntity;
        }).collect(Collectors.toList());
        boolean b = relRolePermissionGroupService.saveBatch(list);
        boolean b1 = authService.delTokens(userIds);
        return ResultMapper.ok(b & b1);
    }


    @GetMapping(value = "/getUserGroupIdList")
    public ServiceResponseResult getUserGroupIdList(@RequestParam String userId) {
        List<ResAllocateListDto> list = sysUserService.getByUserId(userId);
        List<String> collect = list.stream().map(d -> d.getId()).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }

    @GetMapping(value = "/getUserRoleIdList")
    public ServiceResponseResult getUserRoleIdList(@RequestParam String userId) {
        List<ResAllocateListDto> list = roleService.getByGroupId(userId);
        List<String> collect = list.stream().map(d -> d.getId()).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }

    @GetMapping(value = "/getResourceIdList")
    public ServiceResponseResult getResourceIdList(@RequestParam String userId) {
        List<ResAllocateListDto> list = resourceService.getReouscesByRoleId(userId);
        List<String> collect = list.stream().map(d -> d.getId()).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }

    @GetMapping(value = "/getRolePermissionGroupList")
    public ServiceResponseResult getRolePermissionGroupList(@RequestParam String userId) {
        List<ResAllocateListDto> list = sysPermissionGroupService.getPermissionGroupList(userId);
        List<String> collect = list.stream().map(d -> d.getId()).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }
}
