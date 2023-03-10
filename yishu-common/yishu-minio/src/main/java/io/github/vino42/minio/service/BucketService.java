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


package io.github.vino42.minio.service;

import cn.hutool.core.collection.CollUtil;
import io.github.vino42.minio.domain.MinioBucket;
import io.github.vino42.minio.exception.OssErrorException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: Minio Bucket 存储通基础操作服务 Service </p>
 *
 * @author : https://gitee.com/herodotus/dante-engine
 * @date : 2022/6/30 13:26
 */
@Service
public class BucketService extends BaseMinioService {

    private static final Logger log = LoggerFactory.getLogger(BucketService.class);

    /**
     * 查询所有存储桶
     *
     * @return Bucket 列表
     */
    public List<MinioBucket> listBuckets() {
        String function = "listBuckets";
        MinioClient minioClient = getMinioClient();

        try {
            List<Bucket> buckets = minioClient.listBuckets();
            return toEntities(buckets);
        } catch (ErrorResponseException e) {
            log.error("[YiShu] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[YiShu] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssErrorException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[YiShu] |- Minio catch InternalException in [{}].", function, e);
            throw new OssErrorException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[YiShu] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssErrorException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[YiShu] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[YiShu] |- Minio catch IOException in [{}].", function, e);
            throw new OssErrorException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[YiShu] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssErrorException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[YiShu] |- Minio catch ServerException in [{}].", function, e);
            throw new OssErrorException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[YiShu] |- Minio catch XmlParserException in in [{}].", function, e);
            throw new OssErrorException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketExistsArgs {@link BucketExistsArgs}
     * @return true 存在，false 不存在
     */
    public boolean bucketExists(BucketExistsArgs bucketExistsArgs) {
        String function = "bucketExists";
        MinioClient minioClient = getMinioClient();

        try {
            return minioClient.bucketExists(bucketExistsArgs);
        } catch (ErrorResponseException e) {
            log.error("[YiShu] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[YiShu] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssErrorException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[YiShu] |- Minio catch InternalException in [{}].", function, e);
            throw new OssErrorException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[YiShu] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssErrorException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[YiShu] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[YiShu] |- Minio catch IOException in [{}].", function, e);
            throw new OssErrorException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[YiShu] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssErrorException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[YiShu] |- Minio catch ServerException in [{}].", function, e);
            throw new OssErrorException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[YiShu] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssErrorException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    /**
     * 创建存储桶
     * <p>
     * 该方法仅仅是 Minio 原始方法的封装，不包含校验等操作。
     *
     * @param makeBucketArgs {@link MakeBucketArgs}
     */
    public void makeBucket(MakeBucketArgs makeBucketArgs) {
        String function = "makeBucket";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.makeBucket(makeBucketArgs);
        } catch (ErrorResponseException e) {
            log.error("[YiShu] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[YiShu] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssErrorException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[YiShu] |- Minio catch InternalException in [{}].", function, e);
            throw new OssErrorException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[YiShu] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssErrorException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[YiShu] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[YiShu] |- Minio catch IOException in [{}].", function, e);
            throw new OssErrorException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[YiShu] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssErrorException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[YiShu] |- Minio catch ServerException in [{}].", function, e);
            throw new OssErrorException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[YiShu] |- Minio catch XmlParserException in [{}].", function, e);
            throw new OssErrorException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    /**
     * 创建存储桶。
     * <p>
     * 先检查该 Bucket 是否存在，如果不存在才创建
     *
     * @param bucketName bucketName
     */
    public void createBucket(String bucketName) {
        createBucket(BucketExistsArgs.builder().bucket(bucketName).build(), MakeBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储桶。
     * <p>
     * 先检查该 Bucket 是否存在，如果不存在才创建
     *
     * @param bucketName bucketName
     * @param region     region
     */
    public void createBucket(String bucketName, String region) {
        createBucket(BucketExistsArgs.builder().bucket(bucketName).region(region).build(), MakeBucketArgs.builder().bucket(bucketName).region(region).build());
    }

    /**
     * 创建存储桶。
     * <p>
     * 先检查该 Bucket 是否存在，如果不存在才创建
     *
     * @param bucketExistsArgs {@link BucketExistsArgs}
     * @param makeBucketArgs   {@link MakeBucketArgs}
     */
    public void createBucket(BucketExistsArgs bucketExistsArgs, MakeBucketArgs makeBucketArgs) {
        if (!bucketExists(bucketExistsArgs)) {
            makeBucket(makeBucketArgs);
        }
    }

    /**
     * 删除一个空的存储桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param removeBucketArgs {@link RemoveBucketArgs}
     */
    public void removeBucket(RemoveBucketArgs removeBucketArgs) {
        String function = "removeBucket";
        MinioClient minioClient = getMinioClient();

        try {
            minioClient.removeBucket(removeBucketArgs);
        } catch (ErrorResponseException e) {
            log.error("[YiShu] |- Minio catch ErrorResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response error.");
        } catch (InsufficientDataException e) {
            log.error("[YiShu] |- Minio catch InsufficientDataException in [{}].", function, e);
            throw new OssErrorException("Minio insufficient data error.");
        } catch (InternalException e) {
            log.error("[YiShu] |- Minio catch InternalException in [{}].", function, e);
            throw new OssErrorException("Minio internal error.");
        } catch (InvalidKeyException e) {
            log.error("[YiShu] |- Minio catch InvalidKeyException in [{}].", function, e);
            throw new OssErrorException("Minio key invalid.");
        } catch (InvalidResponseException e) {
            log.error("[YiShu] |- Minio catch InvalidResponseException in [{}].", function, e);
            throw new OssErrorException("Minio response invalid.");
        } catch (IOException e) {
            log.error("[YiShu] |- Minio catch IOException in [{}].", function, e);
            throw new OssErrorException("Minio io error.");
        } catch (NoSuchAlgorithmException e) {
            log.error("[YiShu] |- Minio catch NoSuchAlgorithmException in [{}].", function, e);
            throw new OssErrorException("Minio no such algorithm.");
        } catch (ServerException e) {
            log.error("[YiShu] |- Minio catch ServerException in [{}].", function, e);
            throw new OssErrorException("Minio server error.");
        } catch (XmlParserException e) {
            log.error("[YiShu] |- Minio catch XmlParserException in in [{}].", function, e);
            throw new OssErrorException("Minio xml parser error.");
        } finally {
            close(minioClient);
        }
    }

    private List<MinioBucket> toEntities(List<Bucket> buckets) {
        if (CollUtil.isNotEmpty(buckets)) {
            return buckets.stream().map(this::toEntity).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private MinioBucket toEntity(Bucket bucket) {
        MinioBucket minioBucket = new MinioBucket();
        minioBucket.setName(bucket.name());
        if (ObjectUtils.isNotEmpty(bucket.creationDate())) {
            minioBucket.setCreationDate(format(bucket.creationDate()));
        }
        return minioBucket;
    }

    private String format(ZonedDateTime zonedDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return zonedDateTime.format(dateTimeFormatter);
    }
}
