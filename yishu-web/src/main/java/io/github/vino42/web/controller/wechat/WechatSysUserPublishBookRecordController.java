package io.github.vino42.web.controller.wechat;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.SecurityUtils;
import io.github.vino42.domain.dto.SysPublishBook;
import io.github.vino42.domain.dto.SysUserPublishBookRecorDTO;
import io.github.vino42.domain.dto.WechatUserPublishBookDTO;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.service.ISysUserPublishBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
@RequestMapping("/wechat/userPublishBookRecord")
public class WechatSysUserPublishBookRecordController {

    @Autowired
    private ISysUserPublishBookRecordService sysUserPublishBookRecordService;
    @Autowired
    private SensitiveWordBs sensitiveWordBs;

    /**
     * 首页列表查询 图书发布 或者查看当前用户的发布
     *
     * @param sysUserPublishBookRecorDTO
     * @return
     */
    @PostMapping(value = "/page")
    public ServiceResponseResult<IPage<SysUserPublishBookRecorDTO>> getSysUserPublishBookRecordEntityPage(@RequestParam("current") int current, @RequestParam("size") int size, @RequestBody SysUserPublishBookRecorDTO sysUserPublishBookRecorDTO) {
        Page result = new Page(current, size);
        if (sysUserPublishBookRecorDTO.getRegionId() != null && sysUserPublishBookRecorDTO.getRegionId() == -1L) {
            sysUserPublishBookRecorDTO.setRegionId(null);
        }
        int start = PageUtil.getStart(Convert.toInt(result.getCurrent() - 1), Convert.toInt(size));
        List<SysUserPublishBookRecorDTO> data = sysUserPublishBookRecordService.getPageList(start, Convert.toInt(size), sysUserPublishBookRecorDTO);
        int total = sysUserPublishBookRecordService.getTotal(sysUserPublishBookRecorDTO);
        data.stream().forEach(d -> {
            if (d.getRegionName().endsWith("市")) {
                d.setRegionName(d.getRegionName().replace("市", ""));
            }
            if (LocalDateTimeUtil.isSameDay(d.getPublishDate(), DateUtil.toLocalDateTime(new Date()))) {
                d.setDayBefor(DateUtil.format(d.getPublishDate(), "HH:mm"));
            } else {

                LocalDateTime publishDate = d.getPublishDate();
                Date pdate = Date.from(publishDate.atZone(ZoneId.systemDefault()).toInstant());
                Date today = new Date();
                long dayInterval = DateUtil.betweenDay(pdate, today, true);
                long weekInterval = DateUtil.betweenWeek(pdate, today, true);
                long monthInterval = DateUtil.betweenMonth(pdate, today, true);
                long yearInterval = DateUtil.betweenYear(pdate, today, true);
                if (yearInterval > 0) {
                    d.setDayBefor(yearInterval + "年前");
                } else if (monthInterval > 0) {
                    d.setDayBefor(yearInterval + "月前");
                } else if (weekInterval > 0) {
                    d.setDayBefor(weekInterval + "周前");
                } else if (dayInterval > 0) {
                    d.setDayBefor(dayInterval + "天前");
                }
            }
        });
        result.setRecords(data);
        result.setTotal(total);
        result.setPages(PageUtil.totalPage(total, Convert.toInt(size)));
        result.setCurrent(current);
        result.setSize(size);
        return ResultMapper.ok(result);
    }


    @PostMapping(value = "/getUserPublisedBooks")
    public ServiceResponseResult<IPage<SysUserPublishBookRecorDTO>> getUserPublisedBooks(@RequestParam("current") int current, @RequestParam("size") int size) {
        SysUserPublishBookRecorDTO sysUserPublishBookRecorDTO = new SysUserPublishBookRecorDTO();
        Long userId = SecurityUtils.getUserId();
        sysUserPublishBookRecorDTO.setUserId(userId);
        Page result = new Page(current, size);
        int start = PageUtil.getStart(Convert.toInt(result.getCurrent() - 1), Convert.toInt(size));
        List<SysUserPublishBookRecorDTO> data = sysUserPublishBookRecordService.getPageList(start, Convert.toInt(size), sysUserPublishBookRecorDTO);
        int total = sysUserPublishBookRecordService.getTotal(sysUserPublishBookRecorDTO);
        data.stream().forEach(d -> {
            if (d.getRegionName().endsWith("市")) {
                d.setRegionName(d.getRegionName().replace("市", ""));
            }
            if (LocalDateTimeUtil.isSameDay(d.getPublishDate(), DateUtil.toLocalDateTime(new Date()))) {
                d.setDayBefor(DateUtil.format(d.getPublishDate(), "HH:mm"));
            } else {

                LocalDateTime publishDate = d.getPublishDate();
                Date pdate = Date.from(publishDate.atZone(ZoneId.systemDefault()).toInstant());
                Date today = new Date();
                long dayInterval = DateUtil.betweenDay(pdate, today, true);
                long weekInterval = DateUtil.betweenWeek(pdate, today, true);
                long monthInterval = DateUtil.betweenMonth(pdate, today, true);
                long yearInterval = DateUtil.betweenYear(pdate, today, true);
                if (yearInterval > 0) {
                    d.setDayBefor(yearInterval + "年前");
                } else if (monthInterval > 0) {
                    d.setDayBefor(yearInterval + "月前");
                } else if (weekInterval > 0) {
                    d.setDayBefor(weekInterval + "周前");
                } else if (dayInterval > 0) {
                    d.setDayBefor(dayInterval + "天前");
                }
            }
        });
        result.setRecords(data);
        result.setTotal(total);
        result.setPages(PageUtil.totalPage(total, Convert.toInt(size)));
        result.setCurrent(current);
        result.setSize(size);
        return ResultMapper.ok(result);
    }

    /**
     * 查看发布记录详情
     *
     * @param publishId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getPublishById")
    @Cached(name = "ybs:cache:wechat_userPublishBookRecord_getPublishById:", key = "#publishId", expire = 86400, cacheType = CacheType.REMOTE)
    public ServiceResponseResult<SysUserPublishBookRecorDTO> getPublishById(@RequestParam("id") Long publishId) throws Exception {
        SysUserPublishBookRecorDTO result = sysUserPublishBookRecordService.getPublishById(publishId);
        if (result.getRegionName().endsWith("市")) {
            result.setRegionName(result.getRegionName().replace("市", ""));
        }
        if (LocalDateTimeUtil.isSameDay(result.getPublishDate(), DateUtil.toLocalDateTime(new Date()))) {
            result.setDayBefor(DateUtil.format(result.getPublishDate(), "HH:mm"));
        } else {
            LocalDateTime publishDate = result.getPublishDate();
            Date pdate = Date.from(publishDate.atZone(ZoneId.systemDefault()).toInstant());
            Date today = new Date();
            long dayInterval = DateUtil.betweenDay(pdate, today, true);
            long weekInterval = DateUtil.betweenWeek(pdate, today, true);
            long monthInterval = DateUtil.betweenMonth(pdate, today, true);
            long yearInterval = DateUtil.betweenYear(pdate, today, true);
            if (yearInterval > 0) {
                result.setDayBefor(yearInterval + "年前");
            } else if (monthInterval > 0) {
                result.setDayBefor(yearInterval + "月前");
            } else if (weekInterval > 0) {
                result.setDayBefor(weekInterval + "周前");
            } else if (dayInterval > 0) {
                result.setDayBefor(dayInterval + "天前");
            }
        }
        return ResultMapper.ok(result);
    }

    /**
     * 发布图书
     *
     * @param sysUserPublishBookRecord
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/publish")
    public ServiceResponseResult publish(@Valid @RequestBody WechatUserPublishBookDTO sysUserPublishBookRecord) throws Exception {
        List<SysPublishBook> books = sysUserPublishBookRecord.getBooks();
        if (CollectionUtil.isEmpty(books)) {
            return ResultMapper.illegalArgument("请添加图书");
        }
        String contract = sysUserPublishBookRecord.getContract();
        if (StrUtil.isBlankOrUndefined(contract)) {
            return ResultMapper.illegalArgument("请填写联系方式");
        }
        if (books.size() > 5) {
            return ResultMapper.illegalArgument("每次最多发布5本图书");
        }
        Long userId = SecurityUtils.getUserId();
        sysUserPublishBookRecord.setUserId(userId);
        for (SysPublishBook book : books) {
            boolean contains = sensitiveWordBs.contains(book.getRemark());
            if (contains) {
                return ResultMapper.illegalArgument("发布备注中包含非法词汇");
            }
            if (StrUtil.isBlankOrUndefined(book.getRemark())) {
                book.setRemark("联系我时请说明是在同城易书小程序看到的,谢谢!");
            }
        }
        return ResultMapper.ok(sysUserPublishBookRecordService.publish(sysUserPublishBookRecord));
    }

    /**
     * 删除发布
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/delete")
    public ServiceResponseResult delete(@RequestParam Long id) {
        SysUserPublishBookRecordEntity data = new SysUserPublishBookRecordEntity();
        data.setId(id);
        data.setStatu(TableStatusEnum.DELETED);
        SysUserPublishBookRecordEntity byId = sysUserPublishBookRecordService.getById(id);
        Long userId = SecurityUtils.getUserId();
        if (NumberUtil.equals(byId.getUserId(), userId)) {
            sysUserPublishBookRecordService.updateById(data);
        }
        return ResultMapper.ok();
    }
}
