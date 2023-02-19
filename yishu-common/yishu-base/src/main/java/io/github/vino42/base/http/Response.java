package io.github.vino42.base.http;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Charsets;
import io.github.vino42.base.json.jackson.JsonMapper;
import okhttp3.MediaType;

import java.io.IOException;

import static cn.hutool.core.text.StrPool.SLASH;

public final class Response {
    /**
     * 请求消耗时间，单位秒
     */
    public final double duration;
    /**
     * 回复状态码
     */
    public final int statusCode;
    private JsonMapper binder = JsonMapper.nonEmptyMapper();
    private okhttp3.Response response;
    private byte[] body;
    private MediaType mediaType;
    private Long contentLenth;
    private String requestUrl;

    private Response(okhttp3.Response response, int statusCode, double duration) {
        this.response = response;
        this.statusCode = statusCode;
        try {
            this.body = response.body().bytes();
            mediaType = response.body().contentType();
            contentLenth = response.body().contentLength();
            requestUrl = response.request().url().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.duration = duration;
    }

    public static Response create(okhttp3.Response response, double duration) {
        int code = response.code();
        return new Response(response, code, duration);
    }

    public boolean isOK() {
        return statusCode == 200;
    }

    public void close() {
        if (null != this.response) {
            this.response.close();
        }
    }

    public <T> T fromJson(Class<T> clazz) {
        return binder.fromJson(bodyString(), clazz);
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        return binder.fromJson(bodyString(), javaType);
    }

    public String bodyString() {
        return new String(body(), Charsets.UTF_8);
    }

    public byte[] body() {
        if (body != null) {
            return body;
        }
        return body;
    }

    public okhttp3.Response getResponse() {
        return this.response;
    }

    public String contentType() {
        return mediaType.type() + SLASH + mediaType.subtype();

    }

    public boolean isJson() {
        return contentType().equals(OkHttpClient.JsonMime);
    }

    public String url() {
        return requestUrl;
    }
}
