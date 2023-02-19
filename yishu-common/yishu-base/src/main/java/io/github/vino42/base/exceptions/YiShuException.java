package io.github.vino42.base.exceptions;

import io.github.vino42.base.response.ServiceResponseCodeEnum;
import io.github.vino42.base.response.ServiceResponseResult;
import kotlin.Result;

/**
 * =====================================================================================
 *
 * @Created :   2022/9/3 17:46
 * @Compiler :  jdk 11
 * @Author :    VINO
 * @Email : 38912428@qq.com
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
public interface YiShuException {


    /**
     * 获取反馈信息
     *
     * @return 反馈信息对象 {@link ServiceResponseCodeEnum}
     */
    ServiceResponseCodeEnum getFeedback();

    /**
     * 错误信息转换为 Result 对象。
     *
     * @return 结果对象 {@link Result}
     */
    ServiceResponseResult<Object> getServiceResponseResult();
}
