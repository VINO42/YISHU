package io.github.vino42.book;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.vino42.base.http.OkHttpClient;
import io.github.vino42.base.http.Response;
import io.github.vino42.base.json.jackson.JsonMapper;
import io.github.vino42.domain.BookDeatilsByIsbnDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static cn.hutool.core.text.CharSequenceUtil.EMPTY;
import static io.github.vino42.constants.BookBaseConstant.BOOK_INFO.SEARCH;
import static io.github.vino42.constants.BookBaseConstant.BOOK_INFO.SEARCH_BY_ISBN;
import static io.github.vino42.constants.BookBaseConstant.LOCAL_DOUBAN_BOOK_INFO_BASE_URL;
import static io.github.vino42.constants.BookBaseConstant.OPEN_BOOK_BASE_API;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/15 13:42
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@Component
public class BookDetailInfoCommonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookDetailInfoCommonService.class);
    private final static OkHttpClient OK_HTTP_CLIENT = OkHttpClient.create();
    private final static JsonMapper jsonMapper = JsonMapper.alwaysMapper();
    /**
     * https://github.com/VINO42/douban-book-api
     * sudo docker run -d \
     * --name douban-book-api-1 \
     * -p 3000:3000 \
     * -v /CACHE_DIR:/cache \
     * acdzh/douban-book-api
     */
    /**
     * @param isbn
     * @return
     * @throws Exception
     */
    public BookDeatilsByIsbnDto getByISBNFromDouBan(String isbn) throws Exception {
        if (StrUtil.isBlankOrUndefined(isbn)) {
            return null;
        }
        String requestUrl = String.format(LOCAL_DOUBAN_BOOK_INFO_BASE_URL + SEARCH_BY_ISBN, isbn);
        Response response = OK_HTTP_CLIENT.get(requestUrl);
        if (response.isOK() && response.isJson()) {
            String responseBodyStr = response.bodyString();
            JSONObject entries = JSONUtil.parseObj(responseBodyStr);
            String data = entries.getStr("data", EMPTY);
            if (StrUtil.isNotBlank(data)) {
                BookDeatilsByIsbnDto bookDeatilsByIsbnDto = jsonMapper.toObject(data, BookDeatilsByIsbnDto.class);
                bookDeatilsByIsbnDto.setCover_url(bookDeatilsByIsbnDto.getCover_url().replace("/subject/l/","/subject/s/"));
//                if (bookDeatilsByIsbnDto != null) {
//                    bookDeatilsByIsbnDto = getClcCode(isbn, bookDeatilsByIsbnDto);
                return bookDeatilsByIsbnDto;
//                }
            }
        }
        return null;
    }

    public BookDeatilsByIsbnDto getByBookNameFromDouBan(String name) throws Exception {
        if (StrUtil.isBlankOrUndefined(name)) {
            return null;
        }
        String requestUrl = String.format(LOCAL_DOUBAN_BOOK_INFO_BASE_URL + SEARCH, name);
        Response response = OK_HTTP_CLIENT.get(requestUrl);
        if (response.isOK() && response.isJson()) {
            String responseBodyStr = response.bodyString();
            JSONObject entries = JSONUtil.parseObj(responseBodyStr);
            String data = entries.getStr("data", EMPTY);
            if (StrUtil.isNotBlank(data)) {
                BookDeatilsByIsbnDto bookDeatilsByIsbnDto = jsonMapper.toObject(data, BookDeatilsByIsbnDto.class);
                if (bookDeatilsByIsbnDto != null) {
                    bookDeatilsByIsbnDto = getClcCode(bookDeatilsByIsbnDto.getIsbn(), bookDeatilsByIsbnDto);
                    return bookDeatilsByIsbnDto;
                }
            }
        }
        return null;
    }

    private BookDeatilsByIsbnDto getClcCode(String isbn, BookDeatilsByIsbnDto bookDeatilsByIsbnDto) throws Exception {
        String requestUrl = String.format(OPEN_BOOK_BASE_API, isbn);
        Response response = OK_HTTP_CLIENT.get(requestUrl);
        if (response.isOK() && response.isJson()) {
            String responseBodyStr = response.bodyString();
            JSONObject entries = JSONUtil.parseObj(responseBodyStr);
            String data = entries.getStr("data", EMPTY);
            if (StrUtil.isNotBlank(data)) {
                JSONObject book = JSONUtil.parseObj(data);
                String clcCode = book.getStr("clcCode", EMPTY);
                if (StrUtil.isNotBlank(clcCode)) {
                    bookDeatilsByIsbnDto.setClcCode(clcCode);
                }
            }
        }
        return bookDeatilsByIsbnDto;
    }

}
