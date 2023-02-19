package io.github.vino42.web.controller.platform;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.vino42.auth.service.AuthService;
import io.github.vino42.auth.util.TokenUtil;
import io.github.vino42.base.constants.YiShuConstant;
import io.github.vino42.base.domain.UserSessionDto;
import io.github.vino42.base.enums.AccountTypeEnum;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.BeanMapper;
import io.github.vino42.base.utils.SecurityUtils;
import io.github.vino42.base.utils.WebUtils;
import io.github.vino42.domain.dto.ButtonPerms;
import io.github.vino42.domain.dto.ResourceTreeDto;
import io.github.vino42.domain.dto.UserInfoDto;
import io.github.vino42.domain.dto.UserLoginDto;
import io.github.vino42.domain.entity.SysAccountEntity;
import io.github.vino42.domain.entity.SysUserEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.facade.UserAccessFacade;
import io.github.vino42.service.ISysPermissionService;
import io.github.vino42.service.ISysResourceService;
import io.github.vino42.service.ISysRoleService;
import io.github.vino42.service.ISysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.*;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/18 19:57
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Slf4j
@RestController
@Tag(name = "后台管理登录模块")
@RequestMapping("/platform")
public class PlatformAuthController {

    @Autowired
    UserAccessFacade userAccessFacade;
    @Autowired
    AuthService authService;
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysRoleService sysRoleService;
    @Autowired
    ISysPermissionService sysPermissionService;
    @Autowired
    ISysResourceService sysResourceService;


    @PostMapping(value = "/login")
    public ServiceResponseResult login(@Valid @RequestBody UserLoginDto userLoginDto) {
        SysAccountEntity dbAccount = userAccessFacade.getUserByUserMobile(userLoginDto.getUsername(), AccountTypeEnum.ADMIN.getValue());
        if (dbAccount == null) {
            return ResultMapper.error(USER_NOT_EXIST);
        }
        if (TableStatusEnum.BLOCKED.status == dbAccount.getStatu().status) {
            return ResultMapper.error(USER_ALREADY_EXPIRED);
        }
        if (TableStatusEnum.FROZEN.status == dbAccount.getStatu().status) {
            return ResultMapper.error(USER_ALREADY_FROZEN);
        }
        if (!AuthService.matches(userLoginDto.getPassword(), dbAccount.getPasswd())) {
            return ResultMapper.error(PASSWORD_NOT_FIT);
        }
        UserSessionDto result = BeanMapper.map(dbAccount, UserSessionDto.class);
        List<String> roles = sysRoleService.getRolesForUser(dbAccount.getUserId());
        result.setRoles(Sets.newHashSet(roles));
        List<String> permissionsForUser = sysPermissionService.getPermissionsForUser(dbAccount.getUserId());
        result.setPerms(Sets.newHashSet(permissionsForUser));
        String token = authService.setToken(String.valueOf(dbAccount.getUserId()), result);
        result.setToken(token);
        return ResultMapper.ok(result);
    }

    @PostMapping(value = "/logOut")
    public ServiceResponseResult logout() {
        String token = WebUtils.getToken();
        authService.delToken(token);
        return ResultMapper.ok();

    }

    @GetMapping(value = "/userInfo")
    public ServiceResponseResult userInfo() {
        String tokenHeader = WebUtils.getHeader(YiShuConstant.RequestHeaders.REQUEST_HEADER_TOKEN_HEADER);
        String userId = TokenUtil.parseToken(WebUtils.getHeader(YiShuConstant.RequestHeaders.REQUEST_HEADER_TOKEN_HEADER));
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        UserInfoDto userInfoDto = BeanMapper.map(sysUserEntity, UserInfoDto.class);
        userInfoDto.setRoles(authService.auth(tokenHeader).getRoles());
        userInfoDto.getRoles().add("admin");
        return ResultMapper.ok(userInfoDto);
    }

    /**
     * 登录后获取该用户拥有的资源列表返回给前端进行加载
     *
     * @return
     */
    @GetMapping(value = "/resources")
    public ServiceResponseResult resources() {
        List<ResourceTreeDto> platformResources = sysResourceService.getPlatformResources();
        TreeNodeConfig config = new TreeNodeConfig();
        //config可以配置属性字段名和排序等等
        List<Tree<String>> treeNodes = TreeUtil.build(platformResources, "0", config, (object, tree) -> {
            tree.setId(object.getId());//必填属性
            tree.setParentId(object.getParentId());//必填属性
            tree.setName(object.getName());
            // 扩展属性 ...
            tree.putExtra("path", object.getPath());
            tree.putExtra("levelCode", object.getLevelCode());
            tree.putExtra("component", object.getComponent());
            tree.putExtra("meta", object.getMeta());
            tree.putExtra("sort", object.getSort());
        });
        CollUtil.sort(treeNodes, new Comparator<Tree<String>>() {
            @Override
            public int compare(Tree<String> o1, Tree<String> o2) {
                return Convert.toInt(o1.getOrDefault("sort", -1)) - Convert.toInt(o2.getOrDefault("sort", -1));
            }
        });
        log.info(JSONUtil.toJsonStr(treeNodes));
        return ResultMapper.ok(treeNodes);
    }

    @GetMapping(value = "/buttons")
    public ServiceResponseResult buttons() {
        Long userId = SecurityUtils.getUserId();
        List<ButtonPerms> buttonPerms = sysResourceService.getButtonPerms(userId);

        Map<String, Map<String, Boolean>> map = Maps.newHashMap();
        buttonPerms.forEach(d -> {
            if (map.containsKey(d.getComponent())) {
                Map<String, Boolean> stringBooleanMap = map.get(d.getComponent());
                stringBooleanMap.put(d.getButton(), true);
            } else {
                Map<String, Boolean> map1 = Maps.newHashMap();
                String button = d.getButton();
                map1.put(button, true);
                map.put(d.getComponent(), map1);
            }
        });

        return ResultMapper.ok(map);
    }

    @PostMapping(value = "/test2")
    public ServiceResponseResult test2() {
        return ResultMapper.ok();
    }
}
