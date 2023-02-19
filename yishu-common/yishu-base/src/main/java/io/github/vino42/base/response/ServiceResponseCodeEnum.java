package io.github.vino42.base.response;

import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static java.util.Arrays.asList;

public enum ServiceResponseCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200000, "ok", "成功", Level.INFO, HttpStatus.OK),


    BAD_REQUEST(400000, "bad request", "错误请求", Level.ERROR, HttpStatus.BAD_REQUEST),

    ILLEGAL_ARGS(400001, "Illegal argument", "参数错误", Level.ERROR, HttpStatus.BAD_REQUEST),

    /**
     * 参数校验不通过
     */
    PARAM_NOT_VALID(400004, "Parameter not valid. for %s", "参数校验不通过", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 参数校验不通过 为空
     */
    PARAM_BLANK(400005, "The required parameter %s is blank.", "参数校验不通过,参数存在空参数", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 参数校验 超界限
     */
    PARAM_OUT_RANGE(
            400006,
            "The value of parameter %s is not in the right range.",
            "参数校验不通过,超界限",
            Level.INFO,
            HttpStatus.BAD_REQUEST),

    /**
     * 表单异常
     */
    PARAM_FORMAT_INVALID(
            400007, "The format of parameter %s is not correct.", "表单提交异常", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 分页请求数据过大
     */
    PARAM_PAGE_SETTING_INVALID(
            400008, "Return message is too long, please resize page and retry.", "分页请求数据过大", Level.INFO,
            HttpStatus.BAD_REQUEST),

    /**
     * 参数不支持
     */
    PARAM_NOT_SUPPORT(400009, "The parameter(%s) not supported.", "参数不支持", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 请求内容过长
     */
    PARAM_CONTENT_TOO_LONG(
            400010, "The parameter(%s) content is out of limit.", "请求内容过长", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 请求不可读
     */
    PARAM_BODY_NOT_READABLE(400011, "HttpMessageNotReadable", "请求不可读", Level.INFO, HttpStatus.BAD_REQUEST),

    /**
     * 参数类型不匹配
     */
    PARAM_TYPE_NOT_MATCH(
            400012,
            "MethodArgumentTypeMismatch. The value of %s(%s) resolved to %s fail.",
            "参数类型不匹配",
            Level.INFO,
            HttpStatus.BAD_REQUEST),

    /**
     * 非multipart request
     */
    MULTIPART_INVALID(
            400013,
            "Request is not a validate multipart request, please check request or file size.",
            "非multipart 请求",
            Level.INFO,
            HttpStatus.BAD_REQUEST),

    /**
     * 无效的账号或密码
     */
    ACCOUNT_OR_PWD_INVALID(
            400014, "invalid account or password .", "无效的账号或密码", Level.ERROR, HttpStatus.BAD_REQUEST),

    /**
     * 无效的短信验证码
     */
    SMS_CODE_INVALID(400015, "invalid sms code .", "无效短信验证码", Level.ERROR, HttpStatus.BAD_REQUEST),

    /**
     * 无效的图形验证码
     */
    CAPTCHA_CODE_INVALID(400016, "invalid captcha code .", "无效的图形验证码", Level.ERROR, HttpStatus.BAD_REQUEST),


    CONTENT_TYPE_NOT_SUPPORT(400017, "CONTENT_TYPE_NOT_SUPPORT", "不支持的请求", Level.INFO, HttpStatus.BAD_REQUEST),

    METHOD_ARGUMENT_NOT_VALID(400018, "METHOD_ARGUMENT_NOT_VALID", "接口参数校验失败，参数使用错误或者未接收到参数"),

    CAPTCHA_CODE_EXPIRED(400019, "  captcha code  expired .", "图形验证码已过期", Level.ERROR, HttpStatus.BAD_REQUEST),

    CAPTCHA_CODE_NOT_FIT(400020, "  captcha code  not fit .", "图形验证码不匹配", Level.ERROR, HttpStatus.BAD_REQUEST),

    /*滑动验证码开始 400200*/
    //验证码已失效,请重新获取
    DYNAMIC_CAPTCHA_INVALID(400100, "captcha invalid", "验证码已失效,请重新获取"),

    //验证码验证失败
    DYNAMIC_CAPTCHA_COORDINATE_ERROR(400101, "captcha coordinate error", "验证失败"),

    //验证码底图初始化失败,请检查路径
    CAPTCHA_CATEGORY_ERROR(400102, "captcha category error", "验证码分类错误"),

    CAPTCHA_HANDLE_NOT_EXIST(400107, "captcha handler not exist ", "验证码处理器不存在"),

    //滑动验证码接口验证失败数太多 get后验证失败次数过多
    DYNAMIC_CAPTCHA_REQ_LOCK_GET_ERROR(400103, "captcha get too many fail times,plz retry later ", "验证动态验证码错误次数过多,请稍后再试"),

    //check接口请求次数超限,请稍后再试!
    DYNAMIC_CAPTCHA_LIMIT_CHECK_ERROR(400104, "captcha validate too many fail times,plz retry later ", "接口验证失败次数过多,请稍后再试"),

    //verify接口请求次数超限,请稍后再试!
    DYNAMIC_CAPTCHA_REQ_LIMIT_VERIFY_ERROR(400105, "captcha validate too many fail times,plz retry later ", "验证码验证失败数过多,请稍后再试"),

    // 获取验证码失败 请联系管理员
    CAPTCHA_CANNOT_EMPTY(400106, "captcha cannot empty", "验证码不能为空"),
    CAPTCHA_PARAM_NOT_FIT(400107, "captcha  input param not fit", "验证码参数格式错误"),

    /**
     * 需要授权
     */
    AUTH_401_NEED_AUTH(401001, "Need Authentication.", "登录过期,请重新登录", Level.INFO, HttpStatus.UNAUTHORIZED),
    /**
     * 授权失败
     */
    AUTH_401_UNAUTHORIZED(401002, "Authentication failed.", "授权失败,请重新登录", Level.INFO, HttpStatus.UNAUTHORIZED),
    /**
     * token过期失效 需要授权
     */
    AUTH_401_EXPIRED(
            401003, "Certification expired. Re-auth please.", "登录过期,请重新登录", Level.INFO, HttpStatus.UNAUTHORIZED),
    /**
     * 签名错误
     */
    SIGN_INVALID(401004, "Security session invalid.", "签名错误", Level.INFO, HttpStatus.UNAUTHORIZED),
    /**
     * SECURITY_SESSION_INVALID context 传递 session失效
     */
    SECURITY_SESSION_INVALID(
            401005, "Security session invalid.", "请登录", Level.INFO, HttpStatus.UNAUTHORIZED),
    /**
     *
     */
    AUTH_401_BAD_CLIENT_CREDENTIALS(
            401006, "invalid_client. Re-auth please.", "请登录", Level.ERROR, HttpStatus.UNAUTHORIZED),
    /**
     * INVALID_GRANT
     */
    AUTH_401_INVALID_GRANT(
            401007, "invalid_grant. Re-auth please.", "非法授权方式,请登录", Level.ERROR, HttpStatus.UNAUTHORIZED),
    /**
     * UNAUTHORIZED_CLIENT
     */
    AUTH_401_UNAUTHORIZED_CLIENT(
            401008, "unauthorized_client.", "请登录", Level.ERROR, HttpStatus.UNAUTHORIZED),
    /**
     * 无效SCOPE
     */
    AUTH_401_INVALID_SCOPE(401009, "invalid_scope.", "请登录", Level.ERROR, HttpStatus.UNAUTHORIZED),
    /**
     * unsupported_grant_type
     */
    AUTH_401_UN_SUPPORTEDGRANT_TYPE(
            401010, "unsupported_grant_type.", "请登录", Level.ERROR, HttpStatus.UNAUTHORIZED),
    /**
     * unauthorized_user
     */
    AUTH_401_UNAUTHORIZED_USER(401012, "unauthorized_user.", "未授权,请登录", Level.ERROR, HttpStatus.UNAUTHORIZED),

    /**
     * token 无效
     */
    AUTH_401_TOKEN_INVALID(401013, "Invalid token.", "请登录", Level.INFO, HttpStatus.UNAUTHORIZED),

    /**
     * token 无效
     */
    AUTH_401_UNSUPPORT_AUTH_TYPE(401014, "Unsupport auth type.", "请登录", Level.INFO, HttpStatus.UNAUTHORIZED),


    ACCOUNT_EXPIRED(401015, "Account expired.", "账号过期", Level.INFO, HttpStatus.UNAUTHORIZED),
    BAD_CREDENTIALS(401016, "BAD  CREDENTIALS", "用户名或密码错误", Level.INFO, HttpStatus.UNAUTHORIZED),
    CREDENTIALS_EXPIRED(401017, "CREDENTIALS EXPIRED", "该账户密码凭证已过期", Level.INFO, HttpStatus.UNAUTHORIZED),
    ACCOUNT_DISABLED(401018, "ACCOUNT DISABLED", "该账户已经被禁用", Level.INFO, HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED(401019, "ACCOUNT LOCKED", "该账户已经被锁定", Level.INFO, HttpStatus.UNAUTHORIZED),
    ACCOUNT_ENDPOINT_LIMITED(401020, "ACCOUNT ENDPOINT LIMITED", "您已经使用其它终端登录,请先退出其它终端", Level.INFO, HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_FOUND(401021, "USERNAME_NOT_FOUND", "用户名或密码错误", Level.INFO, HttpStatus.UNAUTHORIZED),
    SESSION_EXPIRED(401022, "SESSION EXPIRED", "Session 已过期，请刷新页面后再使用", Level.INFO, HttpStatus.UNAUTHORIZED),
    USER_EXISTED(401023, "USER_EXISTED", "用户已存在", Level.INFO, HttpStatus.UNAUTHORIZED),
    FORBIDDEN(401024, "forbidden", "没有权限,禁止访问", Level.INFO, HttpStatus.UNAUTHORIZED),
    ACCOUNT_EXISTED(401026, "ACCOUNT_EXISTED", "账号已存在", Level.INFO, HttpStatus.UNAUTHORIZED),


    /**
     * 没有权限
     */
    AUTH_403_FORBIDDEN(403000, "Permission deny.", "没有权限", Level.INFO, HttpStatus.FORBIDDEN),
    /**
     * insufficient_scope
     */
    AUTH_403_INSUFFICIENT_SCOPE(403002, "insufficient_scope", "无效的域", Level.INFO, HttpStatus.FORBIDDEN),
    /**
     * app患者端登录后台管理 非租户用户登录租户后台管理
     */
    NO_AUTHORITY_TO_LOGIN(403003, "no authority to login", "无权登录", Level.INFO, HttpStatus.FORBIDDEN),
    /**
     * 404
     */
    NOT_FOUND_404(404000, "NOT FOUND", "服务器未找到", Level.INFO, HttpStatus.NOT_FOUND),
    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOWED(405000, "Method Not Allowed", "请求方法不支持", Level.INFO, HttpStatus.METHOD_NOT_ALLOWED),

    NOT_ACCEPTABLE(406000, "Not Acceptable", "请求ContentType不支持", Level.INFO, HttpStatus.NOT_ACCEPTABLE),

    UNSUPPORTED_RESPONSE_TYPE(406001, "Unsupport Response ", "不支持的响应类型", Level.INFO, HttpStatus.NOT_ACCEPTABLE),

    UNSUPPORTED_TOKEN_TYPE(406002, "Unsupport token type ", "授权服务器不支持撤销提供的令牌类型", Level.INFO, HttpStatus.NOT_ACCEPTABLE),
    STAMP_EXPIRE(407000, "Stamp expire ", "签章已过期", Level.INFO, HttpStatus.INTERNAL_SERVER_ERROR),
    STAMP_DELETE_FAILED(407001, "Stamp delete fail.", "从缓存中删除签章失败", Level.ERROR, HttpStatus.REQUEST_TIMEOUT),

    STAMP_FIT_ERROR(407002, "Stamp not fit.", "签章信息无法匹配", Level.ERROR, HttpStatus.REQUEST_TIMEOUT),

    STAMP_PARMA_ALACK(407003, "Stamp params alack.", "缺少签章身份标记参数", Level.ERROR, HttpStatus.REQUEST_TIMEOUT),

    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(408000, "Request timeout.", "请求超时", Level.ERROR, HttpStatus.REQUEST_TIMEOUT),

    /**
     * 服务响应超时
     */
    SERVICE_RESPONSE_TIMEOUT(408001, "Service response timeout.", "服务响应超时", HttpStatus.REQUEST_TIMEOUT),
    INVALID_REQUEST(412000, "Invalid Request", "无效请求", HttpStatus.PRECONDITION_FAILED),
    INVALID_REDIRECT_URI(412001, "Invalid Redirect URI", "无效的跳转路径", HttpStatus.PRECONDITION_FAILED),

    URI_TOO_LONG(
            414000,
            "URI Too Long.",
            "请求路径过长",
            Level.INFO,
            HttpStatus.URI_TOO_LONG),
    /**
     * contenttype 不支持
     */
    CONTENT_TYPE_INVALID(
            415000,
            "HttpMediaTypeNotSupported. ContentType(%s) is not acceptable.",
            "报文类型不支持",
            Level.INFO,
            HttpStatus.UNSUPPORTED_MEDIA_TYPE),


    /**
     * 不支持的请求体
     */
    REQUEST_BODY_INCORRECT(
            422000, "Entity format not supported。", "请求体类型不支持", Level.ERROR, HttpStatus.UNPROCESSABLE_ENTITY),


    /**
     * 限流
     */
    REQUEST_RAIT_LIMIT(429000, "Too Many Requests", "请稍后重试", Level.INFO, HttpStatus.TOO_MANY_REQUESTS),

    /**
     * 重复提交
     */
    DUMPLICATE_REQUEST_LIMIT(429001, "Dumplicate requests", "重复提交,请稍后重试", Level.INFO, HttpStatus.TOO_MANY_REQUESTS),

    /**
     * 获取锁失败,访问的资源暂时被锁定
     */
    GET_RESOURCE_LOCK_FAILED_429002(429002, "Request  get resource lock failed may be locking, plz try later ", "请稍后重试", Level.ERROR, HttpStatus.TOO_MANY_REQUESTS),

    /**
     * Upgrade Required app版本过低 需要升级
     */
    UPGRADE_REQUIRED(426000, "Upgrade Required", "app版本过低 需要升级"),


    /**
     * 系统内部异常
     */
    SYSTEM_INTERNAL_ERROR(500000, "System internal error", "服务开小差啦", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * 未知异常
     */
    UNKNOWN(500001, "Unknown error.", "未知错误", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * NULL POINT
     */
    NULL_POINT_EXECETION(500003, "NULL_POINT_EXECETION", "空指针", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * IO_EXECETION
     */
    IO_EXECETION(500002, "IO_EXECETION", "io异常", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),


    /**
     * sql 愈发异常
     */
    BAD_SQL_GRAMMAR(500004, "BAD_SQL_GRAMMAR", "sql 语法异常"),

    DATA_INTEGRITY_VIOLATION(500005, "DATA_INTEGRITY_VIOLATION", "该数据正在被其它数据引用，请先删除引用关系，再进行数据删除操作"),

    TRANSACTION_ROLLBACK(500006, "TRANSACTION_ROLLBACK", "数据事务处理失败，数据回滚"),
    /**
     * RPC 通信异常
     */
    RPC_ERROR(500007, "RPC error with error code '%s'.", "服务通信异常", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * 持久化异常
     */
    PERSISTENCE_TO_DB_FAIL(500008, "Persistent fail!", "数据持久化异常", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    /**
     * 中间件异常
     */
    MID_WARE_CONNECT_FAIL(500009, "Connect ", "中间件异常", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),

    PLATFORM_INIT_PROPERTIES_EXCEPTION(500010, "system init properties error ", "系统初始化启动配置异常", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    AES_KEY_ILLEGAL(501000, "AES KEY IS ILLEGAL ", "静态AES加密算法KEY非法", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 网关异常
     */
    GATEWAY_ERROR(502000, "BAD GATEWAY", "网关异常", Level.ERROR, HttpStatus.BAD_GATEWAY),
    /**
     * 服务暂时不可用
     */
    SERVICE_UNAVALIABLE_ERROR(
            503000, "Service Unavailable Just a moment", "服务暂时不可用,请稍后重试", Level.ERROR, HttpStatus.SERVICE_UNAVAILABLE),
    /**
     * 服务暂时不可用
     */
    RPC_UNKNOWN(
            503001, "RPC Service Unavailable Just a moment", "服务暂时不可用,请稍后重试", Level.ERROR, HttpStatus.SERVICE_UNAVAILABLE),

    /**
     * 服务降级
     */
    DEGRADE_ERROR(
            503001, "Service Unavailable Just a moment", "服务暂时不可用,请稍后重试", Level.ERROR, HttpStatus.SERVICE_UNAVAILABLE),

    TEMPORARILY_UNAVAILABLE(
            503002, "temporarily unavailable", "服务暂时不可用,请稍后重试", Level.ERROR, HttpStatus.SERVICE_UNAVAILABLE),

    /**
     * 网关超时
     */
    GATEWAY_CONNECT_TIME_OUT(504000, "GATEWAY CONNECT TIME OUT", "网关超时", Level.ERROR, HttpStatus.GATEWAY_TIMEOUT),
    /**
     * 用户已存在
     */
    OSS_ERROR(600000, "OSS ERROR", "oss对象存储异常"),
    /** ==============================BIZ 业务响应码================================================= */
    /**
     * 用户已存在
     */
    USER_NOT_EXIST(700000, "user NOT exist in system", "用户不存在"),
    /**
     * 用户已存在
     */
    USER_ALREADY_EXIST(700001, "user already exist in system", "用户已存在"),

    /**
     * 用户账号失效
     */
    USER_ALREADY_EXPIRED(700002, "user already expired", "用户账号失效 "),

    /**
     * 用户账号冻结中
     */
    USER_ALREADY_FROZEN(700003, "user already expired", "用户账号冻结中 "),

    /**
     * 需要绑定手机号
     */
    NEED_BIND_PHONE(700004, "user need bind phone ", "请绑定手机号"),
    /**
     * 密码错误
     */
    PASSWORD_NOT_FIT(700005, "wrong password ", "密码错误"),
    WS_ERROR(800000, "websocket error", "远端服务器异常"),
    WS_ILLEGAL_CHANNEL(800001, "illegal channel", "非法信道"),
    WS_PRINCIPAL_ERROR(800002, "principal error", "无法找到用户"),
    ILLEGAL_ISBN_ERROR(900001, "ileegal isbn", "isbn对应的书籍不存在,请检查");



    public int status;
    public String message;
    public String messageEn;
    public Level logLevel;
    public HttpStatus httpStatus;

    ServiceResponseCodeEnum(int status, String messageEn, Level logLevel, HttpStatus httpStatus) {
        this.status = status;
        this.messageEn = messageEn;
        this.logLevel = logLevel;
        this.httpStatus = httpStatus;
    }

    ServiceResponseCodeEnum(int status, String messageEn, String message, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.messageEn = messageEn;
        this.httpStatus = httpStatus;
    }

    ServiceResponseCodeEnum(int status, String messageEn, String message, Level logLevel, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.messageEn = messageEn;
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
    }

    ServiceResponseCodeEnum(int status, String messageEn, String message) {
        this.status = status;
        this.message = message;
        this.messageEn = messageEn;
    }

    ServiceResponseCodeEnum(int status, String messageEn, Level logLevel) {
        this.status = status;
        this.messageEn = messageEn;
        this.logLevel = logLevel;
    }

    ServiceResponseCodeEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    ServiceResponseCodeEnum(int status, String messageEn, HttpStatus httpStatus) {
        this.status = status;
        this.messageEn = messageEn;
        this.httpStatus = httpStatus;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static ServiceResponseCodeEnum getByCode(int code) {
        Optional<ServiceResponseCodeEnum> first = asList(values()).stream().filter(d -> d.getStatus() == code).findFirst();
        return first.orElse(null);
    }
}
