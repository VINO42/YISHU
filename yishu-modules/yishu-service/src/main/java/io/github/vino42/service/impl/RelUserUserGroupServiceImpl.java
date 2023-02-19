package io.github.vino42.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.dao.mapper.RelUserUserGroupMapper;
import io.github.vino42.domain.entity.RelUserUserGroupEntity;
import io.github.vino42.service.IRelUserUserGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/21 15:58:42
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    vino
 * @Copyright : vino
 * @Decription :  服务实现类
 * =====================================================================================
 */
@Service
public class RelUserUserGroupServiceImpl extends ServiceImpl<RelUserUserGroupMapper, RelUserUserGroupEntity> implements IRelUserUserGroupService {

    @Override
    public boolean removeUserGroupByUserId(Long userId) {
        return this.baseMapper.removeUserGroupByUserId(userId);
    }

    @Override
    public List<RelUserUserGroupEntity> selectByGroupIds(List<Long> groupIds) {
        return this.baseMapper.selectByGroupIds(groupIds);
    }
}
