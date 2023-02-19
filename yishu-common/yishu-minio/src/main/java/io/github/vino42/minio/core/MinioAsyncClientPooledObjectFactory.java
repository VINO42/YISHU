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


package io.github.vino42.minio.core;

import io.github.vino42.minio.configuration.MinioProperties;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * <p>Description: 扩展的 Minio 异步 Client 池化工厂 </p>
 *
 * @author : https://gitee.com/herodotus/dante-engine
 * @date : 2022/7/3 20:25
 */
public class MinioAsyncClientPooledObjectFactory extends BasePooledObjectFactory<MinioAsyncClient> {

    private final MinioProperties minioProperties;

    public MinioAsyncClientPooledObjectFactory(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Override
    public MinioAsyncClient create() throws Exception {
        io.minio.MinioAsyncClient minioAsyncClient = io.minio.MinioAsyncClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        return new MinioAsyncClient(minioAsyncClient);
    }

    @Override
    public PooledObject<MinioAsyncClient> wrap(MinioAsyncClient minioAsyncClient) {
        return new DefaultPooledObject<>(minioAsyncClient);
    }
}
