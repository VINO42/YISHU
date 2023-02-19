package io.github.vino42.constants;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/15 11:51
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public interface BookBaseConstant {
    String OPEN_BOOK_BASE_API = "http://47.99.80.202:6066/openApi/getInfoByIsbn?appKey=ae1718d4587744b0b79f940fbef69e77&isbn=%s";

    String DOUBAN_ISBN = "https://book.douban.com/isbn/%s";

    String LOCAL_DOUBAN_BOOK_INFO_BASE_URL = "http://81.70.80.153:3000";

    interface BOOK_INFO {
        String SEARCH = "/search?text=%s";
        String SEARCH_BY_ISBN = "/book?isbn=%s";
    }


}
