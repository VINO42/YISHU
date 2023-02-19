package io.github.vino42.auth.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.domain.entity.SysResourceEntity;
import io.github.vino42.enums.TableStatusEnum;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/25 15:29
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : ©38912428@qq.com
 * @Decription : scan package to get requestMapping
 * =====================================================================================
 */

public class ScanPackageUtil {


    public static Set<SysResourceEntity> scanRequestMapping(String packageBasePath) {
        Set<Class<?>> packageClass = ClassUtil.scanPackage(packageBasePath);
        Set<SysResourceEntity> resourceSets = Sets.newHashSet();
        packageClass.forEach(controllerClaz -> {
            try {
                Class ctrClaz = Class.forName(controllerClaz.getName());
                if (ctrClaz.getAnnotation(RestController.class) != null || ctrClaz.getAnnotation(Controller.class) != null) {
                    String[] classPaths = new String[]{};
                    RequestMapping clazRequestMapping = (RequestMapping) ctrClaz.getAnnotation(RequestMapping.class);
                    if (clazRequestMapping != null) {
                        classPaths = AnnotationUtils.getAnnotation(ctrClaz, RequestMapping.class).value();
                    }
                    if (classPaths.length > 0) {
                        Arrays.asList(classPaths).stream().forEach(firstPath -> {
                            Method[] methods = ctrClaz.getMethods();
                            for (int i = 0; i < methods.length; i++) {
                                Method method = methods[i];
                                String uri = EMPTY;
                                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                                if (getMapping != null) {
                                    uri = firstPath + AnnotationUtils.getAnnotation(method, GetMapping.class).value()[0];
                                }
                                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                                if (postMapping != null) {
                                    uri = firstPath + AnnotationUtils.getAnnotation(method, PostMapping.class).value()[0];
                                }

                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                if (requestMapping != null) {
                                    uri = firstPath + AnnotationUtils.getAnnotation(method, RequestMapping.class).value()[0];
                                }
                                if (StrUtil.isNotBlank(uri)) {
                                    SysResourceEntity resourceEntity = new SysResourceEntity();
                                    resourceEntity.setId(IdGenerator.nextId());
                                    resourceEntity.setStatu(TableStatusEnum.NORMAL);
                                    resourceEntity.setResourceType(1);
                                    resourceEntity.setVersionStamp(System.currentTimeMillis());
                                    //保存到数据库
                                    resourceEntity.setUrl(uri);
                                    resourceEntity.setLevelCode(1);
                                    resourceEntity.setParentId(0L);
                                    resourceSets.add(resourceEntity);
                                }

                            }
                        });
                    } else {
                        Method[] methods = ctrClaz.getMethods();
                        for (int i = 0; i < methods.length; i++) {
                            Method method = methods[i];
                            String uri = EMPTY;
                            GetMapping getMapping = method.getAnnotation(GetMapping.class);
                            if (getMapping != null) {
                                uri = AnnotationUtils.getAnnotation(method, GetMapping.class).value()[0];
                            }
                            PostMapping postMapping = method.getAnnotation(PostMapping.class);
                            if (postMapping != null) {
                                uri = AnnotationUtils.getAnnotation(method, PostMapping.class).value()[0];
                            }

                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            if (requestMapping != null) {
                                uri = AnnotationUtils.getAnnotation(method, RequestMapping.class).value()[0];
                            }
                            if (StrUtil.isNotBlank(uri)) {
                                SysResourceEntity resourceEntity = new SysResourceEntity();
                                resourceEntity.setId(IdGenerator.nextId());
                                resourceEntity.setStatu(TableStatusEnum.NORMAL);
                                resourceEntity.setResourceType(1);
                                resourceEntity.setVersionStamp(System.currentTimeMillis());
                                //保存到数据库
                                resourceEntity.setUrl(uri);
                                resourceEntity.setLevelCode(1);
                                resourceEntity.setParentId(0L);
                                resourceSets.add(resourceEntity);
                            }
                        }

                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


        return resourceSets;
    }
}
