package io.github.vino42.minio.definition.dto.api;

import io.minio.BucketArgs;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;


/**
 * <p>Description: Minio 基础 Bucket Dto </p>
 *
 * @author : https://gitee.com/herodotus/dante-engine
 * @date : 2022/7/1 23:44
 */
public abstract class BucketArgsDto<B extends BucketArgs.Builder<B, A>, A extends BucketArgs> extends BaseArgsDto<B, A> {

    @NotNull(message = "存储桶名称不能为空")
    private String bucketName;
    private String region;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    protected void prepare(B builder) {
        builder.bucket(getBucketName());
        if (StringUtils.isNotBlank(getRegion())) {
            builder.region(getRegion());
        }
        super.prepare(builder);
    }

    @Override
    public A build() {
        B builder = getBuilder();
        prepare(builder);
        return builder.build();
    }
}
