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


package io.github.vino42.minio.exception;

import io.github.vino42.base.exceptions.SystemInternalException;
import io.github.vino42.base.response.ServiceResponseCodeEnum;

import static io.github.vino42.base.response.ServiceResponseCodeEnum.OSS_ERROR;

/**
 * <p>Description: 获取从连接池中获取Minio客户端错误 </p>
 *
 * @author : https://gitee.com/herodotus/dante-engine
 * @date : 2021/11/8 11:45
 */
public class OssErrorException extends SystemInternalException {

    private static final long serialVersionUID = 6610083281801529147L;

    private int code;

    private String message;

    public OssErrorException(String message) {
        super(OSS_ERROR.status, message);
        this.message = message;
    }

    public OssErrorException(ServiceResponseCodeEnum serviceResponseCodeEnum) {
        super(serviceResponseCodeEnum);
    }

    public OssErrorException(long code, String message) {
        super(code, message);
    }

    public OssErrorException() {
        super(OSS_ERROR);
    }

    @Override
    public ServiceResponseCodeEnum getFeedback() {
        return OSS_ERROR;
    }
}
