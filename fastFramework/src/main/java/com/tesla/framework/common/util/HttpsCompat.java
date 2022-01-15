package com.tesla.framework.common.util;

import android.os.Build;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Jerry on 2022/1/15.
 */
public class HttpsCompat {

    /*public static void ignoreSSLForOkHttp(OkHttpClient.Builder builder) {
        builder.hostnameVerifier(getIgnoreHostnameVerifier())
                .sslSocketFactory(getIgnoreSSLSocketFactory());
    }

    public static void enableTls12ForOkHttp(OkHttpClient.Builder builder) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            SSLSocketFactory ssl = getEnableTls12SSLSocketFactory();
            if (ssl != null) {
                builder.sslSocketFactory(ssl);
            }
        }
    }
*/
    public static void ignoreSSLForHttpsURLConnection() {
        HttpsURLConnection.setDefaultHostnameVerifier(getIgnoreHostnameVerifier());
        HttpsURLConnection.setDefaultSSLSocketFactory(getIgnoreSSLSocketFactory());
    }

    public static void enableTls12ForHttpsURLConnection() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            SSLSocketFactory ssl = getEnableTls12SSLSocketFactory();
            if (ssl != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(ssl);
            }
        }
    }

    /**
     * 获取开启TLS1.2的SSLSocketFactory
     * 建议在android4.4及以下版本调用
     */
    public static SSLSocketFactory getEnableTls12SSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            return new Tls12SocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取忽略证书的HostnameVerifier
     * 与{@link #getIgnoreSSLSocketFactory()}同时配置使用
     */
    public static HostnameVerifier getIgnoreHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }

    /**
     * 获取忽略证书的SSLSocketFactory
     * 与{@link #getIgnoreHostnameVerifier()}同时配置使用
     */
    public static SSLSocketFactory getIgnoreSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    private static class Tls12SocketFactory extends SSLSocketFactory {
        private static final String[] TLS_SUPPORT_VERSION = {"TLSv1.1", "TLSv1.2"};

        private final SSLSocketFactory delegate;

        private Tls12SocketFactory(SSLSocketFactory base) {
            this.delegate = base;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return patch(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return patch(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket patch(Socket s) {
            if (s instanceof SSLSocket) {
                ((SSLSocket) s).setEnabledProtocols(TLS_SUPPORT_VERSION);
            }
            return s;
        }
    }
}
