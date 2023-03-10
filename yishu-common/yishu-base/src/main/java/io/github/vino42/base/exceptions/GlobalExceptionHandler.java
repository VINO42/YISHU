/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package io.github.vino42.base.exceptions;

import io.github.vino42.base.response.ResultMapper;
import io.github.vino42.base.response.ServiceResponseCodeEnum;
import io.github.vino42.base.response.ServiceResponseResult;
import io.github.vino42.base.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理器
 * 1**	信息，服务器收到请求，需要请求者继续执行操作
 * 2**	成功，操作被成功接收并处理
 * 3**	重定向，需要进一步的操作以完成请求
 * 4**	客户端错误，请求包含语法错误或无法完成请求
 * 5**	服务器错误，服务器在处理请求的过程中发生了错误
 * <p>
 * 1开头的状态码
 * 100	Continue	继续。客户端应继续其请求
 * 101	Switching Protocols	切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议，例如，切换到HTTP的新版本协议
 * <p>
 * 2开头的状态码
 * 200	OK	请求成功。一般用于GET与POST请求
 * 201	Created	已创建。成功请求并创建了新的资源
 * 202	Accepted	已接受。已经接受请求，但未处理完成
 * 203	Non-Authoritative Information	非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本
 * 204	No Content	无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档
 * 205	Reset Content	重置内容。服务器处理成功，用户终端（例如：浏览器）应重置文档视图。可通过此返回码清除浏览器的表单域
 * 206	Partial Content	部分内容。服务器成功处理了部分GET请求
 * <p>
 * 3开头的状态码
 * 300	Multiple Choices	多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端（例如：浏览器）选择
 * 301	Moved Permanently	永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替
 * 302	Found	临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI
 * 303	See Other	查看其它地址。与301类似。使用GET和POST请求查看
 * 304	Not Modified	未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源
 * 305	Use Proxy	使用代理。所请求的资源必须通过代理访问
 * 306	Unused	已经被废弃的HTTP状态码
 * 307	Temporary Redirect	临时重定向。与302类似。使用GET请求重定向
 * <p>
 * 4开头的状态码
 * 400	Bad Request	客户端请求的语法错误，服务器无法理解
 * 401	Unauthorized	请求要求用户的身份认证
 * 402	Payment Required	保留，将来使用
 * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
 * 404	Not Found	服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
 * 405	Method Not Allowed	客户端请求中的方法被禁止
 * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
 * 407	Proxy Authentication Required	请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权
 * 408	Request Time-out	服务器等待客户端发送的请求时间过长，超时
 * 409	Conflict	服务器完成客户端的PUT请求是可能返回此代码，服务器处理请求时发生了冲突
 * 410	Gone	客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
 * 411	Length Required	服务器无法处理客户端发送的不带Content-Length的请求信息
 * 412	Precondition Failed	客户端请求信息的先决条件错误
 * 413	Request Entity Too Large	由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息
 * 414	Request-URI Too Large	请求的URI过长（URI通常为网址），服务器无法处理
 * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
 * 416	Requested range not satisfiable	客户端请求的范围无效
 * 417	Expectation Failed	服务器无法满足Expect的请求头信息
 * <p>
 * 5开头的状态码
 * 500	Internal Server Error	服务器内部错误，无法完成请求
 * 501	Not Implemented	服务器不支持请求的功能，无法完成请求
 * 502	Bad Gateway	充当网关或代理的服务器，从远端服务器接收到了一个无效的请求
 * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
 * 504	Gateway Time-out	充当网关或代理的服务器，未及时从远端服务器获取请求
 * 505	HTTP Version not supported	服务器不支持请求的HTTP协议的版本，无法完成处理
 *
 * @author gengwei.zheng
 */
@RestControllerAdvice(basePackages = "io.github.vino42")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final Map<String, ServiceResponseResult<Object>> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        // 401.** 对应错误
        EXCEPTION_DICTIONARY.put("AccessDeniedException", getUnauthorizedResult(ServiceResponseCodeEnum.AUTH_401_NEED_AUTH));
        EXCEPTION_DICTIONARY.put("InsufficientAuthenticationException", getUnauthorizedResult(ServiceResponseCodeEnum.AUTH_401_NEED_AUTH));
        EXCEPTION_DICTIONARY.put("HttpRequestMethodNotSupportedException", getResult(ServiceResponseCodeEnum.METHOD_NOT_ALLOWED));
        EXCEPTION_DICTIONARY.put("HttpMediaTypeNotAcceptableException", getUnsupportedMediaTypeResult(ServiceResponseCodeEnum.CONTENT_TYPE_NOT_SUPPORT));
        EXCEPTION_DICTIONARY.put("IllegalArgumentException", getInternalServerErrorResult(ServiceResponseCodeEnum.BAD_REQUEST));
        EXCEPTION_DICTIONARY.put("NullPointerException", getInternalServerErrorResult(ServiceResponseCodeEnum.NULL_POINT_EXECETION));
        EXCEPTION_DICTIONARY.put("IOException", getInternalServerErrorResult(ServiceResponseCodeEnum.IO_EXECETION));
        EXCEPTION_DICTIONARY.put("HttpMessageNotReadableException", getInternalServerErrorResult(ServiceResponseCodeEnum.PARAM_BODY_NOT_READABLE));
        EXCEPTION_DICTIONARY.put("MissingServletRequestParameterException", getInternalServerErrorResult(ServiceResponseCodeEnum.PARAM_BODY_NOT_READABLE));
        EXCEPTION_DICTIONARY.put("ProviderNotFoundException", getServiceUnavailableResult(ServiceResponseCodeEnum.SERVICE_UNAVALIABLE_ERROR));
        EXCEPTION_DICTIONARY.put("BadSqlGrammarException", getInternalServerErrorResult(ServiceResponseCodeEnum.BAD_SQL_GRAMMAR));
        EXCEPTION_DICTIONARY.put("DataIntegrityViolationException", getInternalServerErrorResult(ServiceResponseCodeEnum.DATA_INTEGRITY_VIOLATION));
        EXCEPTION_DICTIONARY.put("TransactionRollbackException", getInternalServerErrorResult(ServiceResponseCodeEnum.TRANSACTION_ROLLBACK));
        EXCEPTION_DICTIONARY.put("BindException", getNotAcceptableResult(ServiceResponseCodeEnum.METHOD_ARGUMENT_NOT_VALID));
        EXCEPTION_DICTIONARY.put("MethodArgumentNotValidException", getNotAcceptableResult(ServiceResponseCodeEnum.METHOD_ARGUMENT_NOT_VALID));
        EXCEPTION_DICTIONARY.put("HttpRequestMethodNotSupportedException", getNotAcceptableResult(ServiceResponseCodeEnum.METHOD_NOT_ALLOWED));
        EXCEPTION_DICTIONARY.put("HttpMediaTypeNotSupportedException", getNotAcceptableResult(ServiceResponseCodeEnum.CONTENT_TYPE_NOT_SUPPORT));

    }

    protected static ServiceResponseResult<Object> getResult(ServiceResponseCodeEnum resultErrorCodes) {
        return ResultMapper.error(resultErrorCodes);
    }

    /**
     * 401	Unauthorized	请求要求用户的身份认证
     *
     * @param resultCode 401
     * @return {@link ServiceResponseResult}
     */
    public static ServiceResponseResult<Object> getUnauthorizedResult(ServiceResponseCodeEnum resultCode) {
        return getResult(resultCode);
    }

    /**
     * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
     *
     * @param resultCode 403
     * @return {@link ServiceResponseResult}
     */
    public static ServiceResponseResult<Object> getForbiddenResult(ServiceResponseCodeEnum resultCode) {
        return getResult(resultCode);
    }

    /**
     * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
     *
     * @param resultCode 406
     * @return {@link ServiceResponseResult}
     */
    public static ServiceResponseResult<Object> getNotAcceptableResult(ServiceResponseCodeEnum resultCode) {
        return getResult(resultCode);
    }

    /**
     * 412 Precondition Failed	客户端请求信息的先决条件错误
     *
     * @param resultCode 412
     * @return {@link ServiceResponseResult}
     */
    public static ServiceResponseResult<Object> getPreconditionFailedResult(ServiceResponseCodeEnum resultCode) {
        return getResult(resultCode);
    }

    /**
     * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     *
     * @param resultCode 415
     * @return {@link ServiceResponseResult}
     */
    private static ServiceResponseResult<Object> getUnsupportedMediaTypeResult(ServiceResponseCodeEnum resultCode) {
        return getResult(resultCode);
    }

    /**
     * 500	Internal Server Error	服务器内部错误，无法完成请求
     *
     * @param resultCode 500
     * @return {@link ServiceResponseResult}
     */
    public static ServiceResponseResult<Object> getInternalServerErrorResult(ServiceResponseCodeEnum resultCode) {
        return getResult(resultCode);
    }

    /**
     * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     *
     * @param resultCode 503
     * @return {@link ServiceResponseResult}
     */
    public static ServiceResponseResult<Object> getServiceUnavailableResult(ServiceResponseCodeEnum resultCode) {
        return getResult(resultCode);
    }

    public static ServiceResponseResult<Object> resolveException(Exception ex, String path) {

        log.trace("[YiShu] |- Global Exception Handler, Path : [{}], Exception : [{}]", path, ex);

        if (ex instanceof SystemInternalException) {
            SystemInternalException exception = (SystemInternalException) ex;
            ServiceResponseResult<Object> result = exception.getServiceResponseResult();
            result.path(path);
            return result;
        } else {
            ServiceResponseResult<Object> result = ResultMapper.error();
            String exceptionName = ex.getClass().getSimpleName();
            if (StringUtils.isNotEmpty(exceptionName) && EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
                result = EXCEPTION_DICTIONARY.get(exceptionName);
            } else {
                log.warn("[YiShu] |- Global Exception Handler,  Can not find the exception name [{}] in dictionary, please do optimize ", exceptionName);
            }

            result.path(path);
            result.exception(ex);
            log.debug("[YiShu] |- Global Exception Handler, Error is : {}", result);
            return result;
        }
    }

    /**
     * 全局系统内部异常.
     *
     * @param e the e
     * @return ServiceResultWrapper
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponseResult<Object> exception(Exception e) throws Throwable {

        log.error("异常信息 ex={}", e.getMessage(), e);

        return resolveException(e, WebUtils.getRequest().getRequestURI());
    }
}
