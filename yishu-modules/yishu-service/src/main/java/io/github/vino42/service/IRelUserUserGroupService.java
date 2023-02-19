package io.github.vino42.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.vino42.domain.entity.RelUserUserGroupEntity;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:42
 * @Compiler :  jdk 8
 * @Author :    vino
 * @Email : 38912428@qq.com
 * @Copyright : vino
 * @Decription :  服务类
 * =====================================================================================
 */
public interface IRelUserUserGroupService extends IService<RelUserUserGroupEntity> {
    boolean removeUserGroupByUserId(Long userId);

    List<RelUserUserGroupEntity> selectByGroupIds(List<Long> groupIds);

}
