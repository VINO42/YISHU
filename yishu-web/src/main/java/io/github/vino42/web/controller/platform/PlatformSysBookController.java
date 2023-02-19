package io.github.vino42.web.controller.platform;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.domain.dto.AlocateUserGroupOrRoleDTO;
import io.github.vino42.domain.dto.DeleteIdsModel;
import io.github.vino42.domain.entity.RelBookBookCategoryEntity;
import io.github.vino42.domain.entity.SysBookEntity;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.IRelBookBookCategoryService;
import io.github.vino42.service.ISysBookService;
import io.github.vino42.service.ISysUserPublishBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:21:55
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : vino42
 * @Decription : 图书基础表 控制器
 * =====================================================================================
 */
@RestController
@RequestMapping("/platform/book")
public class PlatformSysBookController {

    @Autowired
    private ISysBookService sysBookTableService;
    @Autowired
    private ISysUserPublishBookRecordService sysUserPublishBookRecordService;

    @Autowired
    private IRelBookBookCategoryService relBookBookCategoryService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysBookEntityPage(Page<SysBookEntity> page, SysBookEntity sysBookTable) {
        return ResultMapper.ok(sysBookTableService.page(page, Wrappers.query(sysBookTable).orderByDesc("create_time")));
    }


    @PostMapping(value = "/add")
    public ServiceResponseResult create(@Valid @RequestBody SysBookEntity sysBookTable) {
        sysBookTable.setId(IdGenerator.nextId());
        sysBookTable.setStatu(TableStatusEnum.NORMAL);
        return ResultMapper.ok(sysBookTableService.save(sysBookTable));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult update(@Valid @RequestBody SysBookEntity sysBookTable) {

        SysBookEntity dbData = sysBookTableService.getById(sysBookTable.getId());
        if (dbData.getStatu().status != sysBookTable.getStatu().getStatus()) {
            //更新状态
            List<SysUserPublishBookRecordEntity> oldDatas = sysUserPublishBookRecordService.selectByBookId(sysBookTable.getId());
            if (CollUtil.isNotEmpty(oldDatas)) {
                return ResultMapper.error("当前图书使用用中,不可禁用");
            }
        }
        sysBookTable.setUpdateTime(LocalDateTime.now());
        return ResultMapper.ok(sysBookTableService.updateById(sysBookTable));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult delete(@RequestBody DeleteIdsModel id) {
        List<Long> ids = id.getId();
        ids.parallelStream().forEach(d -> {
            SysBookEntity data = new SysBookEntity();
            data.setId(d);
            data.setStatu(TableStatusEnum.DELETED);
            sysBookTableService.updateById(data);
        });
        return ResultMapper.ok();
    }

    /**
     * 给图书分配图书类别
     *
     * @param alocateUserRoleDTO
     * @return
     */
    @PostMapping(value = "/alocateBookBookCategory")
    public ServiceResponseResult alocateBookBookCategory(@RequestBody AlocateUserGroupOrRoleDTO alocateUserRoleDTO) {
        if (CollUtil.isEmpty(alocateUserRoleDTO.getIds())) {
            return ResultMapper.illegalArgument("请选择图书分类");
        }
        List<String> categoryIds = alocateUserRoleDTO.getIds();
        relBookBookCategoryService.removeByBookId(alocateUserRoleDTO.getUserId());
        List<RelBookBookCategoryEntity> list = categoryIds.stream().map(categoryId -> {
            RelBookBookCategoryEntity relBookBookCategoryEntity = new RelBookBookCategoryEntity();
            relBookBookCategoryEntity.setBookId(Long.valueOf(alocateUserRoleDTO.getUserId()));
            relBookBookCategoryEntity.setCategoryId(Long.valueOf(categoryId));
            relBookBookCategoryEntity.setStatu(TableStatusEnum.NORMAL);
            relBookBookCategoryEntity.setId(IdGenerator.nextId());
            return relBookBookCategoryEntity;
        }).collect(Collectors.toList());
        boolean b = relBookBookCategoryService.saveBatch(list);
        return ResultMapper.ok(b);

    }
}
