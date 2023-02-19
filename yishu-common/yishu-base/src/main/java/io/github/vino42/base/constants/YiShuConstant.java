package io.github.vino42.base.constants;

/**
 * =====================================================================================
 *
 * @Created :   2022/10/10 22:35
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public interface YiShuConstant {
    String NOT_AVALIABLE = "N_A";
    String PHONE_CAN_SEND_SMS_REGEX = "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[0-35-9]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[0-35-9]\\d{2}|6[2567]\\d{2}|4[579]\\d{2})\\d{6}$";
    /**
     *
     *  最短8位，最长16位 {8,16}
     * 必须包含1个数字
     * 必须包含1个小写字母
     * 必须包含1个大写字母
     * 必须包含1个特殊字符
     *
     **/
    /**
     * 强密码(必须包含大小写字母和数字 特殊字符的组合，长度在8-16之间)： "^.*(?=.{8,16})(?=.*\d)(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?\(\)]).*$"
     */
    String STRONG_PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$";

    interface OssConstants{
        String IMG_BUCKET_NAME="img";
    }
    interface SymbolConstants {

        String AMPERSAND = "&";

        String AMPERSAND_ENCODED = "&amp;";

        String APOSTROPHE = "'";

        String APOSTROPHE_AND_COMMA = "',";

        String APOSTROPHE_AND_COMMA_AND_APOSTROPHE = "','";

        String AT = "@";

        String BACK_SLASH = "\\";

        String BETWEEN = "BETWEEN";

        String BLANK = "";

        String CDATA_OPEN = "<![CDATA[";

        String CDATA_CLOSE = "]]>";

        String CLOSE_BRACKET = "]";

        String CLOSE_CURLY_BRACE = "}";

        String CLOSE_PARENTHESIS = ")";

        String COLON = ":";

        String COMMA = ",";

        String COMMA_AND_APOSTROPHE = ",'";

        String COMMA_AND_SPACE = ", ";

        String DASH = "-";

        String DOUBLE_APOSTROPHE = "''";

        String DOUBLE_CLOSE_BRACKET = "]]";

        String DOUBLE_OPEN_BRACKET = "[[";

        String DOUBLE_SLASH = "//";

        String EQUAL = "=";

        String GREATER_THAN = ">";

        String GREATER_THAN_OR_EQUAL = ">=";

        String FORWARD_SLASH = "/";

        String FOUR_SPACES = "    ";

        String FINISH_LEFT_ANGLE = "</";

        String FINISH_RIGHT_ANGLE = "/>";

        String GBK = "GBK";

        String IS_NOT_NULL = "IS NOT NULL";

        String IS_NULL = "IS NULL";

        String IN = "IN";

        String LEFT_ANGLE = "<";

        String LESS_THAN = "<";

        String LESS_THAN_OR_EQUAL = "<=";

        String LIKE = "LIKE";

        String MINUS = "-";

        String NBSP = "&nbsp;";

        String NEW_LINE = "\n";

        String NOT_EQUAL = "!=";

        String DB_NOT_EQUAL = "<>";

        String NOT_LIKE = "NOT LIKE";

        String NULL = "null";

        String OPEN_BRACKET = "[";

        String OPEN_CURLY_BRACE = "{";

        String OPEN_PARENTHESIS = "(";

        String PERCENT = "%";

        String PERIOD = ".";

        String PIPE = "|";

        String PLUS = "+";

        String POUND = "#";

        String QUESTION = "?";

        String QUOTE = "\"";

        String RETURN = "\r";

        String RETURN_NEW_LINE = "\r\n";

        String RIGHT_ANGLE = ">";

        String SEMICOLON = ";";

        String SLASH = FORWARD_SLASH;

        String SPACE = " ";

        String STAR = "*";

        String TAB = "\t";

        String TILDE = "~";

        String UNDERLINE = "_";

        String SUFFIX_EXCEL_2003 = ".xls";

        String SUFFIX_EXCEL_2007 = ".xlsx";

        String SUFFIX_JPEG = ".jpg";

        String SUFFIX_XML = ".xml";

        String SUFFIX_PDF = ".pdf";

        String SUFFIX_ZIP = ".zip";

        String SUFFIX_DOC = ".doc";

        String SUFFIX_DOCX = ".docx";

        String SUFFIX_PPT = ".ppt";

        String SUFFIX_PPTX = ".pptx";

        String SUFFIX_EXCEL = ".xls";

        String SUFFIX_EXCELX = ".xlsx";

        String SUFFIX_SWF = ".swf";

        String SUFFIX_PROPERTIES = ".properties";

        String SUFFIX_YML = ".yml";

        String SUFFIX_YAML = ".yaml";

        String SUFFIX_JSON = ".json";

        String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    }


    interface THREAD_CACHE_KEY {
        String THREAD_LOCAL_SERVICE_NAME = "THREAD_LOCAL_SERVICE_NAME";
        String THREAD_LOCAL_REQUEST_LOG_FORMAT_PATTERN = "REQUEST_LOG_FORMAT_PATTERN";
        String THREAD_LOCAL_AUTHKEY = "THREAD_LOCAL_AUTH_TOKEN";
        String THREAD_LOCAL_TENANT_CODE = "THREAD_LOCAL_TENANT_CODE";
        String THREAD_LOCAL_GRAY_VERSION = "THREAD_LOCAL_SERVICE_VERSION";
        String THREAD_LOCAL_REQUEST_START_TIME = "THREAD_LOCAL_REQUEST_START_TIME";
        String THREAD_LOCAL_REQUEST_ID = "THREAD_LOCAL_REQUEST_ID";
    }

    interface AUTH_CONSTANT {
        long DEFAULT_TOKEN_EXPIRE = 7200L;

        String AES_TOKEN_KEY = "05c780a85f14a705";

        String REDIS_CASBIN_CACHE_KEY = "casbin_rules";

        String REDIS_TOKEN_CACHE_KEY = "YISHU_TOKEN";

        String CASBIN_USER_PREFIX = "user_";

        String CASBIN_ROLE_PREFIX = "role_";

        String CASBIN_POLICY_G = "g";

        String CASBIN_POLICY_G2 = "g2";

        String REQUEST_HEADER_TOKEN_HEADER = "X-Token-Header";
    }

    interface ENDPOINT_CONSTANT {
        String ACTUATOR_PREFIX = "/management";

    }

    interface RequestHeaders {

        String REUQEST_INFO = "requestInfo";

        /**
         * 全局traceId
         */
        String REQUEST_HEADER_REQUEST_ID = "X-Request-ID";

        /**
         * 灰度多版本header
         */
        String REQUEST_HEADER_GRAY_VERSION_HEADER = "X-Version-Header";

        /**
         * 渠道header
         */
        String REQUEST_HEADER_CHANNEL_HEADER = "X-Channel-Header";

        /**
         * 授权类型
         */
        String REQUEST_HEADER_AUTH_TYPE_HEADER = "X-Auth-Type-Header";

        /**
         * 客户端类型
         */
        String REQUEST_HEADER_CLIENT_TYPE_HEADER = "X-Client-Type-Header";
        /**
         * token
         */
        String REQUEST_HEADER_TOKEN_HEADER = "X-Token-Header";

        /**
         * APM trace id
         */
        String REQUEST_HEADER_TRACE_ID_HEADER = "X-Trace-Id-Header";

        /**
         * 是否进行传参加密
         */
        String REQUEST_HEADER_IS_ENCODED_HEADER = "X-Encoded-Header";


        String REQUEST_HEADER_SERVICE_TOKEN_HEADER = "X-Service-Token-Header";
        String REQUEST_HEADER_SERVICE_PERM_HEADER = "X-Service-Perm-Header";

        String REQUEST_HEADER_USER_ID_HEADER = "X-User-Id-Header";
        String REQUEST_HEADER_USER_MOBILE_HEADER = "X-User-Mobile-Header";
        String REQUEST_HEADER_REQ_START_TIME_HEADER = "X-Request-Start-Time-Header";

        String UNKNOWN = "unknown";
        String PROXY_CLIENT_IP = "Proxy-Client-IP";
        String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
        String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
        String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
        String X_REAL_IP = "X-Real-IP";
    }

    interface ResponseHeaders {
        String RESPONSE_HEADER_SYS_RESPONSE_STATUS_HEADER = "X-System-Response-Status-Header";

        String RESPONSE_HEADER_SYS_RESPONSE_DATA_HEADER = "X-System-Response-Data-Header";

        String RESPONSE_HEADER_SYS_RESPONSE_MESSAGE_HEADER = "X-System-Response-Message-Header";

    }


    /**
     * LOG4J MDC constant
     */
    interface MDCConstant {

        String MDC_REQUEST_ID = "requestId";

        /**
         * SKYWALLKING TID
         */
        String MDC_APM_TRACE_ID = "traceId";

    }

    interface SystemConstant {
        /**
         * 实例Id
         */
        String INSTANCE_NAME = "instance_id";

        /**
         * 实例地址
         */
        String INSTANCE_ADDRESS = "instance_addr";

        /**
         * 应用名称
         */
        String APPLICATION_NAME = "application_name";
        /**
         * 开发
         */
        String PROFILE_DEV = "dev";
        /**
         * 测试
         */
        String PROFILE_TEST = "test";
        /**
         * 生产
         */
        String PROFILE_PROD = "prod";
        /**
         * 预发布
         */
        String PROFILE_PRE = "pre";
    }


    /**
     * Log4j中使用的一些常量
     */
    interface Log4jConstant {
        /**
         * git 分支
         */
        String GIT_BRANCH = "GIT_BRANCH";
        /**
         * 日志home 路径
         */
        String LOG_HOME = "log.home";

        /**
         * 根日志级别
         */
        String ROOT_NAME = "root";
        /**
         * 文件日志级别
         */
        String FILE_APPENDER_NAME = "FILE";
        /**
         * 控制台日志级别
         */
        String CONSOLE_APPENDER_NAME = "CONSOLE";


        /**
         * 请求日志格式
         */
        interface LogConstant {
            String REQUEST_LOG_FORMAT_PATTERN = "[ requestId:{} | ip:{} | ua:{} | token:{} | userId:{} | mobile:{} | reqTime: [{}] | method:{} | uri:{} | protocol:{} "
                    +
                    "| queryParams:{} | body:{} | response:{} ]";
        }

    }
}
