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
import io.github.vino42.domain.entity.RelBookBookCategoryEntity;
import io.github.vino42.domain.entity.SysBookCategoryEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.IRelBookBookCategoryService;
import io.github.vino42.service.ISysBookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:21:52
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : vino42
 * @Decription : 图书分类表 控制器
 * =====================================================================================
 */
@RestController
@RequestMapping("/platform/bookCategory")
public class PlatformSysBookCategoryController {

    @Autowired
    private ISysBookCategoryService sysBookCategoryTableService;
    @Autowired
    private IRelBookBookCategoryService relBookBookCategoryService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage> getSysBookCategoryEntityPage(Page<SysBookCategoryEntity> page, SysBookCategoryEntity sysBookCategoryTable) {
        return ResultMapper.ok(sysBookCategoryTableService.page(page, Wrappers.query(sysBookCategoryTable).orderByDesc("create_time")));
    }

    /**
     * 获取所有可用的图书分类
     *
     * @return
     */
    @GetMapping(value = "/getAllCategories")
    public ServiceResponseResult<List<ResAllocateListDto>> getAllCategories() {
        SysBookCategoryEntity bookCategory = new SysBookCategoryEntity();
        bookCategory.setStatu(TableStatusEnum.NORMAL);
        QueryWrapper<SysBookCategoryEntity> query = Wrappers.query(bookCategory);
        List<SysBookCategoryEntity> list = sysBookCategoryTableService.list(query);
        List<ResAllocateListDto> collect = list.stream().map(d -> {
            ResAllocateListDto resAllocateListDto = new ResAllocateListDto();
            resAllocateListDto.setId(String.valueOf(d.getId()));
            resAllocateListDto.setName(d.getCategoryName());
            return resAllocateListDto;
        }).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }

    /**
     * 根据图书id获取当前图书的分类
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/getBookCategorisByBookId")
    public ServiceResponseResult getBookCategorisByBookId(@RequestParam String userId) {
        List<ResAllocateListDto> list = relBookBookCategoryService.getBookCategorisByBookId(userId);
        List<String> collect = list.stream().map(d -> d.getId()).collect(Collectors.toList());
        return ResultMapper.ok(collect);
    }

    @PostMapping(value = "/add")
    public ServiceResponseResult create(@Valid @RequestBody SysBookCategoryEntity sysBookCategoryTable) {
        String categoryCode = sysBookCategoryTable.getCategoryCode().trim();
        SysBookCategoryEntity old = sysBookCategoryTableService.getByCode(categoryCode);
        if (old != null) {
            return ResultMapper.illegalArgument("该图书类别编码已经存在");
        }
        sysBookCategoryTable.setCategoryCode(sysBookCategoryTable.getCategoryCode().trim());
        sysBookCategoryTable.setId(IdGenerator.nextId());
        sysBookCategoryTable.setStatu(TableStatusEnum.NORMAL);
        sysBookCategoryTable.setCreateTime(LocalDateTime.now());
        return ResultMapper.ok(sysBookCategoryTableService.save(sysBookCategoryTable));
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult update(@Valid @RequestBody SysBookCategoryEntity sysBookCategoryTable) {
        SysBookCategoryEntity dbData = sysBookCategoryTableService.getById(sysBookCategoryTable.getId());
        String categoryCode = sysBookCategoryTable.getCategoryCode();
        if (StrUtil.isNotBlank(categoryCode)) {
            sysBookCategoryTable.setCategoryCode(sysBookCategoryTable.getCategoryCode().trim());
            SysBookCategoryEntity old = sysBookCategoryTableService.getByCode(categoryCode);
            if (old != null && !old.getId().equals(sysBookCategoryTable.getId())) {
                return ResultMapper.illegalArgument("该图书类别编码已经存在");
            }
        }
        if (dbData.getStatu().status != sysBookCategoryTable.getStatu().getStatus()) {
            //更新状态
            List<RelBookBookCategoryEntity> oldDatas = relBookBookCategoryService.selectByCategoryId(Lists.newArrayList(sysBookCategoryTable.getId()));
            if (CollUtil.isNotEmpty(oldDatas)) {
                return ResultMapper.error("当前图书类别正在使用中,不可禁用");
            }
        }
        sysBookCategoryTable.setUpdateTime(LocalDateTime.now());
        return ResultMapper.ok(sysBookCategoryTableService.updateById(sysBookCategoryTable));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult delete(@RequestBody DeleteIdsModel id) {

        List<Long> categoryIds = id.getId();
        List<RelBookBookCategoryEntity> oldDatas = relBookBookCategoryService.selectByCategoryId(categoryIds);
        if (CollUtil.isNotEmpty(oldDatas)) {
            return ResultMapper.error("当前图书类别正在使用中,不可删除");
        }
        categoryIds.parallelStream().forEach(d -> {
            SysBookCategoryEntity data = new SysBookCategoryEntity();
            data.setId(d);
            data.setStatu(TableStatusEnum.DELETED);
            sysBookCategoryTableService.updateById(data);
        });
        return ResultMapper.ok(true);
    }
}
