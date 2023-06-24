package com.apache.fastandroid.network.ssl;

import okhttp3.OkHttpClient;

public class SslClientSetup {
    public static void installCertificates(OkHttpClient.Builder builder) {
    /*    X509TrustManager trustManager = BackportTrustManager.create();
        builder.sslSocketFactory(new AntennaPodSslSocketFactory(trustManager), trustManager);
        builder.connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT));*/
    }
}
