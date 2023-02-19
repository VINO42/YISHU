package io.github.vino42.web.controller.platform;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.domain.dto.DeleteIdsModel;
import io.github.vino42.domain.dto.SysUserPublishBookRecorDTO;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.ISysUserPublishBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:21:59
 * @Compiler :  jdk 8
 * @Author :    VINO
 * @Copyright : vino42
 * @Decription : 用户发布图书记录表 控制器
 * =====================================================================================
 */
@RestController
@RequestMapping("/platform/userPublishBookRecord")
public class PlatformSysUserPublishBookRecordController {

    @Autowired
    private ISysUserPublishBookRecordService sysUserPublishBookRecordService;


    @GetMapping(value = "/page")
    public ServiceResponseResult<IPage<SysUserPublishBookRecorDTO>> getSysUserPublishBookRecordEntityPage(Page<SysUserPublishBookRecorDTO> page, SysUserPublishBookRecorDTO sysUserPublishBookRecorDTO) {
        Page result = new Page(page.getCurrent(),page.getSize(),page.getTotal());
        int start = PageUtil.getStart(Convert.toInt(result.offset()), Convert.toInt(page.getSize()));
        List<SysUserPublishBookRecorDTO> data = sysUserPublishBookRecordService.getPageList(start, Convert.toInt(page.getSize()), sysUserPublishBookRecorDTO);
        int total = sysUserPublishBookRecordService.getTotal(sysUserPublishBookRecorDTO);
        result.setRecords(data);
        result.setTotal(total);
        result.setPages(PageUtil.totalPage(total, Convert.toInt(page.getSize())));
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        return ResultMapper.ok(result);
    }


    @PostMapping(value = "/update")
    public ServiceResponseResult update(@Valid @RequestBody SysUserPublishBookRecordEntity sysUserPublishBookRecordTable, BindingResult bindingResult) {
        return ResultMapper.ok(sysUserPublishBookRecordService.updateById(sysUserPublishBookRecordTable));
    }


    @PostMapping(value = "/delete")
    public ServiceResponseResult delete(@RequestBody DeleteIdsModel id) {
        id.getId().parallelStream().forEach(d -> {
            SysUserPublishBookRecordEntity data = new SysUserPublishBookRecordEntity();
            data.setId(d);
            data.setStatu(TableStatusEnum.DELETED);
            sysUserPublishBookRecordService.updateById(data);
        });
        return ResultMapper.ok();
    }
}
