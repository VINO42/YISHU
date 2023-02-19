package io.github.vino42.base.response;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.*;


/**
 * The class Wrap mapper.
 */
public final class ResultMapper {

    /**
     * Instantiates a new wrap mapper.
     */
    private ResultMapper() {
        // no arg constructor
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param status  the status
     * @param message the message
     * @param o       the o
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status, String message, E o) {

        return wrap(status, message, o, "");
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param status  the status
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status, String message, String attache) {

        return wrap(status, message, null, attache);
    }

    /**
     * Wrap.
     *
     * @param errorDetail attache msg
     * @param <E>         the element type
     * @param status      the status
     * @param message     the message
     * @param o           the o
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status, String message, E o, String errorDetail) {

        return create(status, message, o, errorDetail, null);
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param status  the status
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status, String message) {

        return wrap(status, message, null, "");
    }

    /**
     * Wrap.
     *
     * @param <E>    the element type
     * @param status the status
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status) {

        return wrap(status, null);
    }

    /**
     * Wrap.
     *
     * @param <E> the element type
     * @param e   the e
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(Exception e) {

        return new ServiceResponseResult<>(SYSTEM_INTERNAL_ERROR.getStatus(), e.getMessage());
    }

    /**
     * Un wrapper.
     *
     * @param <E>     the element type
     * @param wrapper the wrapper
     * @return the e
     */
    public static <E> E unWrap(ServiceResponseResult<E> wrapper) {

        return wrapper.getData();
    }

    /**
     * Wrap ERROR. status=100
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> illegalArgument() {

        return wrap(BAD_REQUEST.status, BAD_REQUEST.message);
    }

    /**
     * Wrap ERROR. status=401001
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> pleaseLogin() {

        return wrap(AUTH_401_NEED_AUTH.status, AUTH_401_NEED_AUTH.message);
    }

    public static <E> ServiceResponseResult<E> forbidden() {

        return wrap(FORBIDDEN.status, FORBIDDEN.message);
    }

    /**
     * Wrap ERROR. status=100
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> illegalArgument(String msg) {

        return wrap(BAD_REQUEST.status, msg);
    }

    /**
     * Wrap ERROR. status=500
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error() {

        return error(SYSTEM_INTERNAL_ERROR.getStatus(), SYSTEM_INTERNAL_ERROR.getMessage(), null, null, null);

    }

    /**
     * Wrap ERROR. status=500
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(ServiceResponseCodeEnum responseEnum) {
        return error(responseEnum.getStatus(), responseEnum.message, null, null, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(String message, String attache) {
        return error(SYSTEM_INTERNAL_ERROR.getStatus(), message, null, attache, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(String message) {
        return error(SYSTEM_INTERNAL_ERROR.getStatus(), message, null, null, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(int status, String message) {

        return error(status, message, null, null, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(int status, String message, String errorDetail) {
        return error(status, message, null, errorDetail, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(int status, String message, E data, String attache, StackTraceElement[] stackTrace) {

        return create(status, message, data, attache, stackTrace);
    }

    public static <E> ServiceResponseResult<E> error(Exception e) {
        return error(SYSTEM_INTERNAL_ERROR.status, SYSTEM_INTERNAL_ERROR.message, null, e.getStackTrace()[0].toString(), e.getStackTrace());
    }

    /**
     * Wrap SUCCESS. status=200
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> ok() {
        return create(SUCCESS.status, SUCCESS.message, null, StrUtil.EMPTY, null);
    }

    private static <T> ServiceResponseResult<T> create(int status, String message, T data, String errorDetail, StackTraceElement[] stackTrace) {
        ServiceResponseResult<T> result = new ServiceResponseResult<>();
        if (StringUtils.isNotBlank(message)) {
            result.message(message);
        }

        if (StringUtils.isNotBlank(errorDetail)) {
            result.detail(errorDetail);
        }

        result.status(status);

        if (ObjectUtils.isNotEmpty(data)) {
            result.data(data);
        }

        if (ArrayUtils.isNotEmpty(stackTrace)) {
            result.stackTrace(stackTrace);
        }
        return result;
    }


    /**
     * Ok wrapper.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> ok(E o, String msg, String attache) {
        return create(SUCCESS.status, msg, o, attache, null);
    }

    /**
     * Wrap SUCCESS. status=200
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> ok(E o, String msg) {

        return new ServiceResponseResult(SUCCESS.status, msg, o);
    }


    /**
     * Ok wrapper.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> ok(E o) {
        return new ServiceResponseResult<>(SUCCESS.status, SUCCESS.message, o);
    }


}
