package com.tesla.framework.network.http;

import com.tesla.framework.common.util.log.NLog;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class HttpsUtility extends OkHttpUtility {

	private static OkHttpClient mOKHttpClient;

	public synchronized OkHttpClient getOkHttpClient() {
		if (mOKHttpClient == null) {
			try {
				OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
				okBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
				okBuilder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
				okBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);

				TrustManager tm = new X509TrustManager() {

					public void checkClientTrusted(X509Certificate[] chain,
												   String authType) throws CertificateException {

					}

					public void checkServerTrusted(X509Certificate[] chain,
												   String authType) throws CertificateException {

					}

					public X509Certificate[] getAcceptedIssuers() {

						return new X509Certificate[0];

					}

				};

				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, new TrustManager[] { tm }, null);
				okBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tm);
				okBuilder.hostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				mOKHttpClient = okBuilder.build();

			} catch (Exception e) {
				NLog.printStackTrace(e);
			}
		}

		return mOKHttpClient;
	}



}
