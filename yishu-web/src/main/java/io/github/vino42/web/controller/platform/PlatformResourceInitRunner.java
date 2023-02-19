package io.github.vino42.web.controller.platform;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Sets;
import io.github.vino42.auth.util.ScanPackageUtil;
import io.github.vino42.base.enums.AccountTypeEnum;
import io.github.vino42.base.enums.ButtonDefinetion;
import io.github.vino42.base.enums.ResourceTypeEnum;
import io.github.vino42.base.utils.BeanMapper;
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
import java.util.Arrays;
import java.util.List;
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
public class PlatformResourceInitRunner implements ApplicationRunner {
    private final ISysPermissionService sysPermissionService;
    private final IRelPermissionResourceService permissionResourceService;
    private final ISysResourceService sysResourceService;
    private final IRelPermissionGroupPermissionService permissionGroupPermissionService;

    public PlatformResourceInitRunner(ISysPermissionService sysPermissionService, IRelPermissionResourceService permissionResourceService, ISysResourceService sysResourceService, IRelPermissionGroupPermissionService permissionGroupPermissionService) {
        this.sysPermissionService = sysPermissionService;
        this.permissionResourceService = permissionResourceService;
        this.sysResourceService = sysResourceService;
        this.permissionGroupPermissionService = permissionGroupPermissionService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String packageName = this.getClass().getPackage().getName();
        Set<SysResourceEntity> sysResourceEntities = ScanPackageUtil.scanRequestMapping(packageName);
        sysResourceEntities.parallelStream().forEach(d -> {
            d.setResourceType(ResourceTypeEnum.INTERFACE.getCode());
            d.setPlatformType(AccountTypeEnum.ADMIN.getValue());
        });
        Set<SysPermissionEntity> sysPermissionEntities = Sets.newHashSet();
        Set<RelPermissionResourceEntity> relPermissionResourceEntities = Sets.newHashSet();
        Set<RelPermissionGroupPermissionEntity> relPermissionGroupPermissionEntities = Sets.newHashSet();
        Set<SysResourceEntity> sysResourceEntitiesNew = Sets.newHashSet();
        List<SysResourceEntity> platformResources = sysResourceService.getResourceByType(ResourceTypeEnum.MENU.getValue());
        platformResources.forEach(d -> {
            if (d.getParentId() == 0 && d.getResourceType() == ResourceTypeEnum.MENU.getValue()) {
                ButtonDefinetion[] values = ButtonDefinetion.values();
                Arrays.asList(values).forEach(e -> {
                    SysResourceEntity sysResourceEntity = BeanMapper.map(d, SysResourceEntity.class);
                    sysResourceEntity.setId(IdGenerator.nextId());
                    sysResourceEntity.setParentId(d.getId());
                    sysResourceEntity.setName(e.name());
                    sysResourceEntity.setTitle(d.getTitle() + e.getDescription());
                    sysResourceEntity.setComponent(d.getName());
                    sysResourceEntity.setLevelCode(2);
                    sysResourceEntity.setResourceType(ResourceTypeEnum.BUTTON.getCode());
                    sysResourceEntity.setPlatformType(AccountTypeEnum.ADMIN.getValue());
                    sysResourceEntity.setIcon(null);
                    boolean ifExit = sysResourceService.selectByParentIdAndComponentAndLevelCodeAndTitle(sysResourceEntity.getParentId(),
                            sysResourceEntity.getComponent(),
                            sysResourceEntity.getLevelCode(),
                            sysResourceEntity.getTitle());
                    if (!ifExit) {
                        SysPermissionEntity sysPermissionEntity = new SysPermissionEntity();
                        sysPermissionEntity.setCreateTime(LocalDateTime.now());
                        sysPermissionEntity.setStatu(TableStatusEnum.NORMAL);
                        sysPermissionEntity.setId(IdGenerator.nextId());
                        sysPermissionEntity.setPerm(StrUtil.isNotBlank(SecureUtil.md5(sysResourceEntity.getUrl())) ? SecureUtil.md5(sysResourceEntity.getUrl()) : SecureUtil.md5(String.valueOf(sysResourceEntity.getId())));
                        sysPermissionEntities.add(sysPermissionEntity);

                        RelPermissionResourceEntity permissionResourceEntity = new RelPermissionResourceEntity();
                        permissionResourceEntity.setId(IdGenerator.nextId());
                        permissionResourceEntity.setStatu(TableStatusEnum.NORMAL);
                        permissionResourceEntity.setPermissionId(sysPermissionEntity.getId());
                        permissionResourceEntity.setResourceId(sysResourceEntity.getId());
                        relPermissionResourceEntities.add(permissionResourceEntity);

                        RelPermissionGroupPermissionEntity relPermissionGroupPermissionEntity = new RelPermissionGroupPermissionEntity();
                        relPermissionGroupPermissionEntity.setId(IdGenerator.nextId());
                        relPermissionGroupPermissionEntity.setPermissionId(permissionResourceEntity.getPermissionId());
                        relPermissionGroupPermissionEntity.setStatu(TableStatusEnum.NORMAL);
                        relPermissionGroupPermissionEntity.setPermissionGroupId(1l);
                        relPermissionGroupPermissionEntities.add(relPermissionGroupPermissionEntity);

                        sysResourceEntitiesNew.add(sysResourceEntity);
                    }
                });

            }
        });
        sysResourceEntities.forEach(resource -> {
            boolean ifExist = sysResourceService.getByResourceUrl(resource.getUrl());
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
                relPermissionGroupPermissionEntity.setPermissionGroupId(1l);
                relPermissionGroupPermissionEntities.add(relPermissionGroupPermissionEntity);
            }
        });
        sysResourceService.saveOrUpdateBatch(sysResourceEntitiesNew);
        sysPermissionService.saveOrUpdateBatch(sysPermissionEntities);
        permissionResourceService.saveOrUpdateBatch(relPermissionResourceEntities);
        permissionGroupPermissionService.saveOrUpdateBatch(relPermissionGroupPermissionEntities);
    }


}
