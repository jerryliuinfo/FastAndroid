package com.tesla.framework.network.http;

import java.util.HashMap;
import java.util.Map;

public class HttpConfig {

	public String cookie;

	public String baseUrl;// 服务器地址

	public Map<String, String> headerMap = new HashMap<>();
	


	public HttpConfig copy(){
		HttpConfig httpConfig = new HttpConfig();
		httpConfig.cookie = cookie;
		httpConfig.baseUrl = baseUrl;
		httpConfig.headerMap = headerMap;
		return httpConfig;
	}

	public void addHeader(String key, String value) {
		headerMap.put(key, value);
	}

}
