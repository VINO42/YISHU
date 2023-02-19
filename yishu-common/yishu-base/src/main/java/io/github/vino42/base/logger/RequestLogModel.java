package io.github.vino42.base.logger;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_MS_PATTERN;

@Data
public class RequestLogModel implements Serializable {

    private String address;
    private CharSequence method;
    private CharSequence uri;
    private String protocol;
    private CharSequence status;
    private String zonedDateTime = DateUtil.format(new Date(), NORM_DATETIME_MS_PATTERN);

    private long startTime = System.currentTimeMillis();

    private int port;
    private String ua;
    //客户端类型
    private String clientType;
    //是否加密传参
    private String isEncoded;
    //token 头
    private String token;
    //用户id
    private String userId;
    //用户手机号
    private String mobile;
    //请求体
    private String body;
    //query参数
    private String queryParams;
    //接口消耗时间
    private long duration;
    //响应报文
    private String result;
}
