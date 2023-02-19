package io.github.vino42.base.http;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Route;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;


/**
 * =====================================================================================
 *
 * @Filename :   ProxyConfiguration.java
 * @Description :   http 代理
 * @Version :   3.0
 * @Created :   2018年2月6日 10:33:35
 * @Compiler :   jdk 1.8
 * =====================================================================================
 */
public final class ProxyConfiguration {

    public final String hostAddress;
    public final int port;
    public final String user;
    public final String password;
    public final Proxy.Type type;

    /**
     * @param hostAddress 服务器域名或IP，比如proxy.com, 192.168.1.1
     * @param port        端口
     * @param user        用户名，无则填null
     * @param password    用户密码，无则填null
     * @param type        默认Proxy.Type.HTTP
     */
    public ProxyConfiguration(String hostAddress, int port, String user, String password, Proxy.Type type) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.user = user;
        this.password = password;
        this.type = type;
    }

    public ProxyConfiguration(String hostAddress, int port, String user, String password) {
        this(hostAddress, port, user, password, Proxy.Type.HTTP);
    }

    public ProxyConfiguration(String hostAddress, int port) {
        this(hostAddress, port, null, null);
    }

    Proxy proxy() {
        return new Proxy(type, new InetSocketAddress(hostAddress, port));
    }

    Authenticator authenticator() {
        return new Authenticator() {
            @Override
            public okhttp3.Request authenticate(Route route, okhttp3.Response response) throws IOException {
                String credential = Credentials.basic(user, password);
                return response.request().newBuilder().
                        header("Proxy-Authorization", credential).
                        header("Proxy-Connection", "Keep-Alive").build();
            }
        };
    }
}
