package io.github.vino42.base.http;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.net.HttpHeaders;
import io.github.vino42.base.common.UserAgents;
import io.github.vino42.base.json.jackson.JsonMapper;
import okhttp3.*;
import okio.BufferedSink;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.EMPTY;


public final class OkHttpClient {
    public static final String PROXY_HOST = "221.2.155.35";
    public static final int PROXY_PORT = 8060;
    public static final String PROXY_USER = "";
    public static final String PROXY_PASS = "";
    public static final String ContentTypeHeader = "Content-Type";
    public static final String OCTETMime = "application/octet-stream";
    public static final String JsonMime = "application/json";
    public static final String FormMime = "application/x-www-form-urlencoded";
    public static final String DefaultMime = "application/x-www-form-urlencoded; charset=UTF-8";
    public static final String MultipartFormMime = "multipart/form-data";
    public static final MediaType MT_MULTIPART_FORM_MIME = MediaType.parse(MultipartFormMime);
    /**
     * 所有都是UTF-8编码
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    /**
     * 连接超时时间 单位秒(默认10s)
     */
    public static final int CONNECT_TIMEOUT = 10;
    /**
     * 写超时时间 单位秒(默认 0 , 不超时)
     */
    public static final int WRITE_TIMEOUT = 0;
    /**
     * 回复超时时间 单位秒(默认30s)
     */
    public static final int READ_TIMEOUT = 30;
    /**
     * 底层HTTP库所有的并发执行的请求数量
     */
    public static final int DISPATCHER_MAX_REQUESTS = 32;
    /**
     * 底层HTTP库对每个独立的Host进行并发请求的数量
     */
    public static final int DISPATCHER_MAX_REQUESTS_PER_HOST = 8;
    /**
     * 底层HTTP库中复用连接对象的最大空闲数量
     */
    public static final int CONNECTION_POOL_MAX_IDLE_COUNT = 16;
    /**
     * 底层HTTP库中复用连接对象的回收周期（单位分钟）
     */
    public static final int CONNECTION_POOL_MAX_IDLE_MINUTES = 5;
    private final static Logger logger = LoggerFactory.getLogger(OkHttpClient.class);
    private static final Supplier<OkHttpClient> OK_HTTP_CLIENT_SUPPLIER = OkHttpClient::new;
    private static final OkHttpClient proxyOkHttpClient = new OkHttpClient(new ProxyConfiguration(PROXY_HOST, PROXY_PORT, PROXY_USER, PROXY_PASS));
    protected static JsonMapper binder = JsonMapper.nonDefaultMapper();
    private final okhttp3.OkHttpClient httpClient;

    /**
     * 构建一个默认配置的 HTTP Client 类
     */
    public OkHttpClient() {
        this(null, CONNECT_TIMEOUT, READ_TIMEOUT, WRITE_TIMEOUT,
                DISPATCHER_MAX_REQUESTS, DISPATCHER_MAX_REQUESTS_PER_HOST,
                CONNECTION_POOL_MAX_IDLE_COUNT, CONNECTION_POOL_MAX_IDLE_MINUTES);
    }

    /**
     * 构建一个ProxyConfiguration的 HTTP Client 类
     */
    public OkHttpClient(final ProxyConfiguration proxy) {
        this(proxy, CONNECT_TIMEOUT, READ_TIMEOUT, WRITE_TIMEOUT,
                DISPATCHER_MAX_REQUESTS, DISPATCHER_MAX_REQUESTS_PER_HOST,
                CONNECTION_POOL_MAX_IDLE_COUNT, CONNECTION_POOL_MAX_IDLE_MINUTES);
    }

    /**
     * 构建一个自定义配置的 HTTP Client 类
     */
    public OkHttpClient(final ProxyConfiguration proxy,
                        int connTimeout, int readTimeout, int writeTimeout, int dispatcherMaxRequests,
                        int dispatcherMaxRequestsPerHost, int connectionPoolMaxIdleCount,
                        int connectionPoolMaxIdleMinutes) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(dispatcherMaxRequests);
        dispatcher.setMaxRequestsPerHost(dispatcherMaxRequestsPerHost);
        ConnectionPool connectionPool = new ConnectionPool(connectionPoolMaxIdleCount,
                connectionPoolMaxIdleMinutes, TimeUnit.MINUTES);
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();

      /*  try {
            TrustManager[] trustAllCerts = buildTrustManagers();
            final SSLContext
                    sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("build ssl failed",e);
        }*/
        builder.dispatcher(dispatcher);
        builder.connectionPool(connectionPool);
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        });

        if (proxy != null) {
            builder.proxy(proxy.proxy());
            if (proxy.user != null && proxy.password != null) {
                builder.proxyAuthenticator(proxy.authenticator());
            }
        }

        builder.connectTimeout(connTimeout, TimeUnit.SECONDS);
        builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        httpClient = builder.build();
    }

    public static OkHttpClient createProxy() {
        return proxyOkHttpClient;
    }

    public static OkHttpClient create() {
        return OK_HTTP_CLIENT_SUPPLIER.get();
    }

    private static String userAgent() {
        return UserAgents.getUserAgent();
    }

    private static RequestBody create(final MediaType contentType,
                                      final byte[] content, final int offset, final int size) {
        if (content == null) {
            throw new NullPointerException("content == null");
        }
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return size;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, size);
            }
        };
    }

    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }

    public Response get(String url) throws Exception {
        return get(url, new HashMap<>());
    }

    public Response get(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            params.forEach((key, value) -> {
                httpBuilder.addQueryParameter(key, value.toString());
            });
        }
        Request.Builder requestBuilder = new Request.Builder().get().url(httpBuilder.build());
        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, params, EMPTY, send);
        return send;
    }

    public Response get(String url, Map<String, String> headers) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, EMPTY, EMPTY, send);
        return send;
    }

    public Response delete(String url, Map<String, String> headers) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().delete().url(url);
        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, EMPTY, EMPTY, send);
        return send;
    }

    public Response delete(String url, String data, Map<String, String> headers) throws Exception {

        RequestBody rBody = getRequestBody(data.getBytes(UTF_8), headers, "");
        Request.Builder requestBuilder = new Request.Builder().delete(rBody).url(url);

        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, EMPTY, data, send);
        return send;
    }

    public Response delete(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        final FormBody.Builder f = new FormBody.Builder();
        params.forEach((key, value) -> f.add(key, value.toString()));
        Request.Builder requestBuilder = new Request.Builder().url(url).post(f.build());

        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, params, EMPTY, send);
        return send;
    }

    public Response post(String url, byte[] body, Map<String, String> headers) throws Exception {
        return post(url, body, headers, JsonMime);

    }

    public Response post(String url, File body, Map<String, String> headers) throws Exception {
        return post(url, body, headers, OCTETMime);
    }

    public void asyncPost(String url, byte[] body, Map<String, String> headers, final AsyncCallback cb) throws Exception {
        asyncPost(url, body, headers, DefaultMime, cb);
    }

    public Response post(String url, String body, Map<String, String> headers) throws Exception {
        return post(url, StringUtils.getBytesUtf8(body), headers, JsonMime);
    }

    public void asyncPost(String url, String body, Map<String, String> headers, final AsyncCallback cb) throws Exception {
        asyncPost(url, StringUtils.getBytesUtf8(body), headers, DefaultMime, cb);
    }

    public Response post(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        final FormBody.Builder f = new FormBody.Builder();
        params.forEach((key, value) -> f.add(key, value.toString()));
        return post(url, f.build(), headers);
    }

    public Response post(String url, Map<String, String> queryParams, Map<String, Object> body, Map<String, String> headers) throws Exception {
        final FormBody.Builder f = new FormBody.Builder();
        body.forEach((key, value) -> f.add(key, value.toString()));
        url = rebuildUrl(url, queryParams);
        return post(url, f.build(), headers);
    }

    private String rebuildUrl(String url, Map<String, String> queryParams) {
        if (!CollectionUtils.isEmpty(queryParams)) {
            StringBuffer sb = new StringBuffer(url);
            if (!url.contains("?")) {
                queryParams.entrySet().forEach(d -> {
                    String key = d.getKey();
                    Object value = d.getValue();
                    sb.append(CharUtil.AMP);
                    if (StrUtil.NULL.equals(value)) {
                        sb.append(key + "=");
                    } else {
                        sb.append(key + "=" + value);
                    }
                });
                url = url + sb.toString().replaceFirst(String.valueOf(CharUtil.AMP), "?");
            } else {
                queryParams.entrySet().forEach(d -> {
                    String key = d.getKey();
                    Object value = d.getValue();
                    sb.append(CharUtil.AMP);
                    if (StrUtil.NULL.equals(value)) {
                        sb.append(key + "=");
                    } else {
                        sb.append(key + "=" + value);
                    }
                });
                url = url + sb;
            }
        }
        return url;
    }

    public Response post(String url, FormBody.Builder f, Map<String, String> headers) throws Exception {
        return post(url, f.build(), headers);
    }

    public void asyncPost(String url, Map<String, Object> params, Map<String, String> headers, final AsyncCallback cb) throws Exception {
        final FormBody.Builder f = new FormBody.Builder();
        params.forEach((key, value) -> f.add(key, value.toString()));
        asyncPost(url, f.build(), headers, cb);
    }

    public Response post(String url, byte[] body, Map<String, String> headers, String contentType) throws Exception {
        RequestBody rBody = getRequestBody(body, headers, contentType);
        return post(url, rBody, headers);
    }

    public Response post(String url, File body, Map<String, String> headers, String contentType) throws Exception {
        RequestBody rBody = getRequestBody(body, headers, contentType);
        return post(url, rBody, headers);
    }

    private RequestBody getRequestBody(File body, Map<String, String> headers, String contentType) {
        RequestBody rBody;
        if (body != null) {
            Object obj = headers.get(ContentTypeHeader);
            if (Objects.nonNull(obj)) {
                contentType = String.valueOf(obj);
            }
            MediaType t = MediaType.parse(contentType);
            rBody = RequestBody.create(t, body);
        } else {
            throw new RuntimeException("file is not find");
        }
        return rBody;
    }

    private RequestBody getRequestBody(byte[] body, Map<String, String> headers, String contentType) {
        RequestBody rBody;
        if (body != null && body.length > 0) {
            Object obj = headers.get(ContentTypeHeader);
            if (Objects.nonNull(obj)) {
                contentType = String.valueOf(obj);
            }
            MediaType t = MediaType.parse(contentType);
            rBody = RequestBody.create(t, body);
        } else {
            rBody = RequestBody.create(null, new byte[0]);
        }
        return rBody;
    }

    public void asyncPost(String url, byte[] body, Map<String, String> headers, String contentType, final AsyncCallback cb) throws Exception {
        RequestBody rBody = getRequestBody(body, headers, contentType);
        asyncPost(url, rBody, headers, cb);
    }

    public Response post(String url, byte[] body, int offset, int size,
                         Map<String, String> headers, String contentType) throws Exception {
        RequestBody rBody = getRequestBody(body, headers, contentType);
        return post(url, rBody, headers);
    }

    private Response post(String url, RequestBody body, Map<String, String> headers) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, EMPTY, body, send);
        return send;
    }

    private void asyncPost(String url, RequestBody body, Map<String, String> headers, final AsyncCallback cb) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        asyncSend(requestBuilder, headers, cb);
    }

    public Response multipartPost(String url,
                                  Map<String, Object> fields,
                                  String name,
                                  String fileName,
                                  byte[] fileBody,
                                  String mimeType,
                                  Map<String, String> headers) throws Exception {
        RequestBody file = RequestBody.create(MediaType.parse(mimeType), fileBody);
        return multipartPost(url, fields, name, fileName, file, headers);
    }

    public Response multipartPost(String url,
                                  Map<String, Object> fields,
                                  String name,
                                  String fileName,
                                  File fileBody,
                                  String mimeType,
                                  Map<String, String> headers) throws Exception {
        RequestBody file = RequestBody.create(MediaType.parse(mimeType), fileBody);
        return multipartPost(url, fields, name, fileName, file, headers);
    }

    private Response multipartPost(String url,
                                   Map<String, Object> fields,
                                   String name,
                                   String fileName,
                                   RequestBody file,
                                   Map<String, String> headers) throws Exception {
        final MultipartBody.Builder mb = new MultipartBody.Builder();
        mb.addFormDataPart(name, fileName, file);

        fields.forEach((key, value) -> mb.addFormDataPart(key, value.toString()));
        mb.setType(MT_MULTIPART_FORM_MIME);
        RequestBody body = mb.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, EMPTY, body, send);
        return send;
    }

    public Response send(final Request.Builder requestBuilder, Map<String, String> headers) throws Exception {
        requestBuilder.header(HttpHeaders.USER_AGENT, userAgent());
        if (headers != null) {
            headers.forEach((key, value) -> requestBuilder.header(key, value.toString()));
        }
        long start = System.currentTimeMillis();
        okhttp3.Response res = null;
        Response r;
        double duration = (System.currentTimeMillis() - start) / 1000.0;
        try {
            res = httpClient.newCall(requestBuilder.build()).execute();
            logger.info("[ okhttpclient  send res:{} ]", res);
        } catch (IOException e) {
            logger.error("[ okhttpclient  send error ]", e);
            throw new Exception(e);
        }
        r = Response.create(res, duration);
        if (r.statusCode >= 300) {
            logger.error("Response :url{},{}", requestBuilder.build().url(), binder.toJson(r));
        }
        if (res != null) {
            res.close();
            r.close();
        }
        return r;
    }


    public void asyncSend(final Request.Builder requestBuilder, Map<String, String> headers, final AsyncCallback cb) {
        requestBuilder.header("User-Agent", userAgent());
        if (headers != null) {
            headers.forEach((key, value) -> requestBuilder.header(key, value.toString()));
        }
        final long start = System.currentTimeMillis();
        httpClient.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                long duration = (System.currentTimeMillis() - start) / 1000;
                cb.onFailure("", duration);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                long duration = (System.currentTimeMillis() - start) / 1000;
                cb.OnComplete(Response.create(response, duration), duration);
            }
        });
    }

    public void asyncPost(String url, byte[] body, int offset, int size,
                          Map<String, String> headers, String contentType, AsyncCallback cb) {
        RequestBody rbody;
        if (body != null && body.length > 0) {
            MediaType t = MediaType.parse(contentType);
            rbody = create(t, body, offset, size);
        } else {
            rbody = RequestBody.create(null, new byte[0]);
        }

        Request.Builder requestBuilder = new Request.Builder().url(url).post(rbody);
        asyncSend(requestBuilder, headers, cb);
    }

    public void asyncMultipartPost(String url,
                                   Map<String, String> fields,
                                   String name,
                                   String fileName,
                                   byte[] fileBody,
                                   String mimeType,
                                   Map<String, String> headers,
                                   AsyncCallback cb) {
        RequestBody file = RequestBody.create(MediaType.parse(mimeType), fileBody);
        asyncMultipartPost(url, fields, name, fileName, file, headers, cb);
    }

    public void asyncMultipartPost(String url,
                                   Map<String, String> fields,
                                   String name,
                                   String fileName,
                                   File fileBody,
                                   String mimeType,
                                   Map<String, String> headers,
                                   AsyncCallback cb) throws Exception {
        RequestBody file = RequestBody.create(MediaType.parse(mimeType), fileBody);
        asyncMultipartPost(url, fields, name, fileName, file, headers, cb);
    }

    private void asyncMultipartPost(String url,
                                    Map<String, String> fields,
                                    String name,
                                    String fileName,
                                    RequestBody file,
                                    Map<String, String> headers,
                                    AsyncCallback cb) {
        final MultipartBody.Builder mb = new MultipartBody.Builder();
        mb.addFormDataPart(name, fileName, file);

        fields.forEach((key, value) -> mb.addFormDataPart(key, value));
        mb.setType(MediaType.parse("multipart/form-data"));
        RequestBody body = mb.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        asyncSend(requestBuilder, headers, cb);
    }

    //put
    public Response put(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        final FormBody.Builder f = new FormBody.Builder();
        params.forEach((key, value) -> f.add(key, value.toString()));
        return put(url, f.build(), headers);
    }

    public Response put(String url, RequestBody body, Map<String, String> headers) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().url(url).put(body);
        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, EMPTY, body, headers, send);
        return send;
    }

    public Response put(String url, String body, Map<String, String> headers) throws Exception {
        return put(url, StringUtils.getBytesUtf8(body), headers, JsonMime);
    }

    public Response put(String url, byte[] body, Map<String, String> headers, String contentType) throws Exception {
        RequestBody rBody = getRequestBody(body, headers, contentType);
        return put(url, rBody, headers);
    }

    public Response put(String url, File body, Map<String, String> headers, String contentType) throws Exception {
        RequestBody rBody = getRequestBody(body, headers, contentType);
        return put(url, rBody, headers);
    }

    //patch
    public Response patch(String url, Map<String, Object> params, Map<String, String> headers) throws Exception {
        final FormBody.Builder f = new FormBody.Builder();
        params.forEach((key, value) -> f.add(key, value.toString()));
        return patch(url, f.build(), headers);
    }

    private Response patch(String url, RequestBody body, Map<String, String> headers) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().url(url).patch(body);
        Response send = send(requestBuilder, headers);
        logger.info("[ okttp Response url:{},params:{},body:{},headers:{},response:{}]", url, EMPTY, body, headers, send);
        return send;
    }

    public Response patch(String url, String body, Map<String, String> headers) throws Exception {
        return patch(url, StringUtils.getBytesUtf8(body), headers, JsonMime);
    }

    public Response patch(String url, byte[] body, Map<String, String> headers, String contentType) throws Exception {
        RequestBody rBody = getRequestBody(body, headers, contentType);
        return patch(url, rBody, headers);
    }

}

