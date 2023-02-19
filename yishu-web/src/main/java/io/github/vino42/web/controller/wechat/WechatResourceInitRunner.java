package io.github.vino42.web.controller.wechat;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Sets;
import io.github.vino42.auth.util.ScanPackageUtil;
import io.github.vino42.base.enums.AccountTypeEnum;
import io.github.vino42.base.enums.ResourceTypeEnum;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.domain.entity.RelPermissionGroupPermissionEntity;
import io.github.vino42.domain.entity.RelPermissionResourceEntity;
import io.github.vino42.domain.entity.SysPermissionEntity;
import io.github.vino42.domain.entity.SysResourceEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.IRelPermissionGroupPermissionService;
import io.github.vino42.service.IRelPermissionResourceService;
import io.github.vino42.service.ISysPermissionService;
import io.github.vino42.service.ISysResourceService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/25 20:20
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription : 新增一个页面需要将父级别的页面组件加入到数据库 然后 这里会自动将按钮权限加入到资源表权限表
 * 同时会扫描指定包底下的controller 将接口资源及权限加入到 资源表  权限表
 * =====================================================================================
 */
@Component
public class WechatResourceInitRunner implements ApplicationRunner {
    private final ISysPermissionService sysPermissionService;
    private final IRelPermissionResourceService permissionResourceService;
    private final ISysResourceService sysResourceService;
    private final IRelPermissionGroupPermissionService permissionGroupPermissionService;

    public WechatResourceInitRunner(ISysPermissionService sysPermissionService, IRelPermissionResourceService permissionResourceService, ISysResourceService sysResourceService, IRelPermissionGroupPermissionService permissionGroupPermissionService) {
        this.sysPermissionService = sysPermissionService;
        this.permissionResourceService = permissionResourceService;
        this.sysResourceService = sysResourceService;
        this.permissionGroupPermissionService = permissionGroupPermissionService;
    }


    @Override
    public void run(ApplicationArguments args) {
        String packageName = this.getClass().getPackage().getName();
        Set<SysResourceEntity> sysResourceEntities = ScanPackageUtil.scanRequestMapping(packageName);
        Set<SysPermissionEntity> sysPermissionEntities = Sets.newHashSet();
        Set<RelPermissionResourceEntity> relPermissionResourceEntities = Sets.newHashSet();
        Set<RelPermissionGroupPermissionEntity> relPermissionGroupPermissionEntities = Sets.newHashSet();
        Set<SysResourceEntity> sysResourceEntitiesNew = Sets.newHashSet();
        sysResourceEntities.forEach(resource -> {
            boolean ifExist = sysResourceService.getByResourceUrl(resource.getUrl());
            resource.setResourceType(ResourceTypeEnum.INTERFACE.getCode());
            resource.setPlatformType(AccountTypeEnum.WECHAT.getValue());
            if (!ifExist && StrUtil.isNotBlank(resource.getUrl())) {
                resource.setName(resource.getUrl());
                sysResourceEntitiesNew.add(resource);
                SysPermissionEntity sysPermissionEntity = new SysPermissionEntity();
                sysPermissionEntity.setCreateTime(LocalDateTime.now());
                sysPermissionEntity.setStatu(TableStatusEnum.NORMAL);
                sysPermissionEntity.setId(IdGenerator.nextId());
                sysPermissionEntity.setPerm(StrUtil.isNotBlank(SecureUtil.md5(resource.getUrl())) ? SecureUtil.md5(resource.getUrl()) : SecureUtil.md5(String.valueOf(resource.getId())));
                sysPermissionEntities.add(sysPermissionEntity);

                RelPermissionResourceEntity permissionResourceEntity = new RelPermissionResourceEntity();
                permissionResourceEntity.setId(IdGenerator.nextId());
                permissionResourceEntity.setStatu(TableStatusEnum.NORMAL);
                permissionResourceEntity.setPermissionId(sysPermissionEntity.getId());
                permissionResourceEntity.setResourceId(resource.getId());
                relPermissionResourceEntities.add(permissionResourceEntity);

                RelPermissionGroupPermissionEntity relPermissionGroupPermissionEntity = new RelPermissionGroupPermissionEntity();
                relPermissionGroupPermissionEntity.setId(IdGenerator.nextId());
                relPermissionGroupPermissionEntity.setPermissionId(permissionResourceEntity.getPermissionId());
                relPermissionGroupPermissionEntity.setStatu(TableStatusEnum.NORMAL);
                relPermissionGroupPermissionEntity.setPermissionGroupId(2l);
                relPermissionGroupPermissionEntities.add(relPermissionGroupPermissionEntity);
            }
        });
        sysResourceService.saveOrUpdateBatch(sysResourceEntitiesNew);
        sysPermissionService.saveOrUpdateBatch(sysPermissionEntities);
        permissionResourceService.saveOrUpdateBatch(relPermissionResourceEntities);
        permissionGroupPermissionService.saveOrUpdateBatch(relPermissionGroupPermissionEntities);
    }


}
