package io.github.vino42.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.vino42.base.exceptions.IllegalIsbnException;
import io.github.vino42.base.utils.IdGenerator;
import io.github.vino42.book.BookDetailInfoCommonService;
import io.github.vino42.dao.mapper.SysUserPublishBookRecordMapper;
import io.github.vino42.domain.BookDeatilsByIsbnDto;
import io.github.vino42.domain.dto.SysPublishBook;
import io.github.vino42.domain.dto.SysUserPublishBookRecorDTO;
import io.github.vino42.domain.dto.WechatUserPublishBookDTO;
import io.github.vino42.domain.entity.RelBookBookCategoryEntity;
import io.github.vino42.domain.entity.SysBookEntity;
import io.github.vino42.domain.entity.SysUserPublishBookRecordEntity;
import io.github.vino42.enums.TableStatusEnum;
import io.github.vino42.minio.service.ObjectService;
import io.github.vino42.service.IRelBookBookCategoryService;
import io.github.vino42.service.ISysBookService;
import io.github.vino42.service.ISysUserPublishBookRecordService;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import static io.github.vino42.base.constants.YiShuConstant.OssConstants.IMG_BUCKET_NAME;
import static io.github.vino42.base.constants.YiShuConstant.SymbolConstants.SLASH;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * =====================================================================================
 *
 * @Created :   2022/12/31 21:15:02
 * @Compiler :  jdk 8
 * @Email : 38912428@qq.com
 * @Author :    VINO
 * @Copyright : VINO
 * @Decription : 用户发布图书记录表 服务实现类
 * =====================================================================================
 */
@Service
public class SysUserPublishBookRecordServiceImpl extends ServiceImpl<SysUserPublishBookRecordMapper, SysUserPublishBookRecordEntity> implements ISysUserPublishBookRecordService {

    @Autowired
    BookDetailInfoCommonService bookDetailInfoCommonService;
    @Autowired
    ISysBookService sysBookService;
    @Autowired
    IRelBookBookCategoryService relBookBookCategoryService;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Value("${yishu.oss.minio.displayendpoint:https://wukuaiba.com}")
    private String displayUrlPrefix;
    @Autowired
    ObjectService objectService;
    @Autowired
    ISysBookService bookService;

    @Override
    public List<SysUserPublishBookRecordEntity> selectByBookId(Long bookId) {
        return this.baseMapper.selectByBookId(bookId);
    }

    @Override
    public List<SysUserPublishBookRecorDTO> getPageList(int start, int size, SysUserPublishBookRecorDTO query) {
        return this.baseMapper.getPageList(start, size, query);
    }

    @Override
    public int getTotal(SysUserPublishBookRecorDTO sysUserPublishBookRecorDTO) {
        return this.baseMapper.getTotal(sysUserPublishBookRecorDTO);
    }

    @Override
    @Transactional
    public boolean publish(WechatUserPublishBookDTO wechatUserPublishBookDTO) throws Exception {
        List<SysPublishBook> books = wechatUserPublishBookDTO.getBooks();
        books.forEach(data -> {
            String isbn = data.getIsbn();
            BookDeatilsByIsbnDto bookDeatilsByIsbnDto = null;
            try {
                bookDeatilsByIsbnDto = bookDetailInfoCommonService.getByISBNFromDouBan(isbn);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bookDeatilsByIsbnDto == null) {
                throw new IllegalIsbnException();
            }

            SysBookEntity sysBookEntity = sysBookService.getByIsbn(isbn);
            if (sysBookEntity != null) {
                boolean historyPublish = this.getCountByUserIdAndBookId(wechatUserPublishBookDTO.getUserId(), sysBookEntity.getId());
                if (historyPublish) {
                    //发布过了就不进行重新发了
                    return;
                }
                //插入图书分类
//                assembleCategoryAndInert(sysBookEntity, wechatUserPublishBookDTO, sysBookEntity.getId());
                //拼装发布记录
                assembleBookPublishRecordAndInert(data, sysBookEntity, wechatUserPublishBookDTO, sysBookEntity.getId());

            } else {
                //拼装图书

                SysBookEntity book = assembleBookAndInert(bookDeatilsByIsbnDto);
                //插入图书分类
//                assembleCategoryAndInert(sysBookEntity, wechatUserPublishBookDTO, bookId);
                //拼装发布记录 插入
                assembleBookPublishRecordAndInert(data, sysBookEntity, wechatUserPublishBookDTO, book.getId());
            }
        });
        return true;
    }

    @Override
    public SysUserPublishBookRecorDTO getPublishById(Long publishId) {
        return this.baseMapper.getPublishById(publishId);
    }

    @Override
    public boolean getCountByUserIdAndBookId(Long userId, Long id) {
        return this.baseMapper.getCountByUserIdAndBookId(userId, id) > 0;
    }

    private void assembleBookPublishRecordAndInert(SysPublishBook data, SysBookEntity sysBookEntity, WechatUserPublishBookDTO wechatUserPublishBookDTO, Long bookId) {
        List<SysUserPublishBookRecordEntity> sysUserPublishBookRecordEntities = this.baseMapper.selectByBookId(bookId == null ? sysBookEntity.getId() : bookId);
        if (CollUtil.isEmpty(sysUserPublishBookRecordEntities)) {
            SysUserPublishBookRecordEntity publish = new SysUserPublishBookRecordEntity();
            publish.setBookId(bookId == null ? sysBookEntity.getId() : bookId);
            publish.setId(IdGenerator.nextId());
            publish.setPublishDate(LocalDateTime.now());
            publish.setUserId(wechatUserPublishBookDTO.getUserId());
            publish.setStatu(TableStatusEnum.NORMAL);
            publish.setRegionId(wechatUserPublishBookDTO.getRegionId());
            publish.setContract(wechatUserPublishBookDTO.getContract());
            publish.setRemark(data.getRemark());
            publish.setVersionStamp(System.currentTimeMillis());
            this.baseMapper.insert(publish);
        }
    }

    private SysBookEntity assembleBookAndInert(BookDeatilsByIsbnDto bookDeatilsByIsbnDto) {
        SysBookEntity sysBookEntity = new SysBookEntity();
        sysBookEntity.setId(IdGenerator.nextId());
        sysBookEntity.setStatu(TableStatusEnum.NORMAL);
        sysBookEntity.setBookIntro(bookDeatilsByIsbnDto.getBook_intro());
        sysBookEntity.setIsbn(bookDeatilsByIsbnDto.getIsbn());
        sysBookEntity.setAuthor(bookDeatilsByIsbnDto.getAuthor().get(0));
        sysBookEntity.setTitle(bookDeatilsByIsbnDto.getTitle());
        sysBookEntity.setPic(bookDeatilsByIsbnDto.getCover_url());
        sysBookEntity.setPublish(bookDeatilsByIsbnDto.getPublish());
        String coverUrl = bookDeatilsByIsbnDto.getCover_url();
        String replace = coverUrl.replace("https://", EMPTY);
        String fileName = replace.substring(replace.lastIndexOf("/"));
        String ossUrl = displayUrlPrefix + SLASH + IMG_BUCKET_NAME + fileName;

        String fileName1 = replace.substring(replace.lastIndexOf("/") + 1);
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(coverUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        File file = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            // 得到网络返回的输入流
            is = conn.getInputStream();
            file = FileUtil.newFile("./img/" + fileName1);
            FileUtil.writeFromStream(is, file);
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(fileName)
                    .bucket(IMG_BUCKET_NAME)
                    .stream(FileUtil.getInputStream(file), file.length(), -1).build();
            ObjectWriteResponse objectWriteResponse = objectService.putObject(objectArgs);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        sysBookEntity.setPic(ossUrl);
        sysBookService.save(sysBookEntity);
        return sysBookEntity;
    }

    private void assembleCategoryAndInert(SysBookEntity sysBookEntity, WechatUserPublishBookDTO wechatUserPublishBookDTO, Long bookId) {
        List<String> categoryIds = relBookBookCategoryService.getCategoryIdsByBookId(bookId);
        if (!categoryIds.contains(wechatUserPublishBookDTO.getCategoryId())) {
            RelBookBookCategoryEntity bookCategoryEntity = new RelBookBookCategoryEntity();
            bookCategoryEntity.setStatu(TableStatusEnum.NORMAL);
            bookCategoryEntity.setId(IdGenerator.nextId());
            bookCategoryEntity.setBookId(bookId);
            bookCategoryEntity.setCategoryId(wechatUserPublishBookDTO.getCategoryId());
            relBookBookCategoryService.save(bookCategoryEntity);
        }
    }
}
