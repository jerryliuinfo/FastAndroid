package com.tesla.framework.network.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.network.NetworkHelper;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.task.TaskException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class OkHttpUtility implements IHttpUtility {

	static String getTag(Setting action, String append) {

		return ABizLogic.getTag(action, append);
	}

	@Override
	public <T> T doGet(HttpConfig config, Setting action, Params urlParams, Class<T> responseCls) throws TaskException {
		Request.Builder builder = createRequestBuilder(config, action, urlParams, "Get");
		Request request = builder.build();

		return executeRequest(request, responseCls, action, "Get");
	}

	@Override
	public <T> T doPost(HttpConfig config, Setting action, Params urlParams, Params bodyParams, Object requestObj, Class<T> responseCls) throws TaskException {
		Request.Builder builder = createRequestBuilder(config, action, urlParams, "Post");

		if (bodyParams != null) {
			String requestBodyStr = ParamsUtil.encodeToURLParams(bodyParams);

			NLog.d(getTag(action, "Post"), requestBodyStr);

			builder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), requestBodyStr));
		}
		else if (requestObj != null) {
			String requestBodyStr = JSON.toJSONString(requestObj);

			NLog.d(getTag(action, "Post"), requestBodyStr);

			builder.post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), requestBodyStr));
		}

		return executeRequest(builder.build(), responseCls, action, "Post");
	}

	@Override
	public <T> T doPostFiles(HttpConfig config, Setting action, Params urlParams, Params bodyParams, MultipartFile[] files, Class<T> responseCls) throws TaskException {
		return null;
	}

	private Request.Builder createRequestBuilder(HttpConfig config, Setting action, Params urlParams, String method) throws TaskException {
		// 是否有网络连接
		if (!NetworkHelper.isNetworkAvailable(FrameworkApplication.getContext())) {
			NLog.w(getTag(action, method), "没有网络连接");

			throw new TaskException(TaskException.TaskError.noneNetwork.toString());
		}

		String url = getUrl(config,action,urlParams);
		NLog.d(getTag(action, method), url);

		Request.Builder builder = new Request.Builder();
		builder.url(url);

		// add Cookie
		if (!TextUtils.isEmpty(config.cookie)) {
			builder.header("Cookie", config.cookie);

			NLog.d(getTag(action, method), "Cookie = " + config.cookie);
		}
		// add header
		if (config.headerMap.size() > 0) {
			Set<String> keySet = config.headerMap.keySet();
			for (String key : keySet) {
				builder.addHeader(key, config.headerMap.get(key));

				NLog.d(getTag(action, method), "Header[%s, %s]", key, config.headerMap.get(key));
			}
		}

		return builder;
	}



	public String getRequestBodyStr(){
		return null;
	}

	private <T> T executeRequest(Request request, Class<T> responseCls, Setting action, String method) throws TaskException {
		try {
			if (SettingUtility.getPermanentSettingAsInt("http_delay") > 0) {
				Thread.sleep(SettingUtility.getPermanentSettingAsInt("http_delay"));
			}
		} catch (Exception e) {
			NLog.printStackTrace(e);
		}

		try {
			Response response = getOkHttpClient().newCall(request).execute();
			NLog.w(getTag(action, method), "Http-code = %d", response.code());
			if (! (response.code() == HttpURLConnection.HTTP_OK || response.code() == HttpURLConnection.HTTP_PARTIAL)) {
				String responseStr = response.body().string();

				if (NLog.isDebug()) {
					NLog.w(getTag(action, method), responseStr);
				}

				TaskException.checkResponse(responseStr);

				throw new TaskException(TaskException.TaskError.timeout.toString());
			} else {
				String responseStr = response.body().string();
                NLog.v(getTag(action, method), "Response = %s", responseStr);

				return parseResponse(responseStr, responseCls);
			}
		} catch (SocketTimeoutException e) {
			NLog.w(getTag(action, method), e + "");

			throw new TaskException(TaskException.TaskError.timeout.toString());
		} catch (IOException e) {
			NLog.w(getTag(action, method), e + "");

			throw new TaskException(TaskException.TaskError.timeout.toString());
		} catch (TaskException e) {
			NLog.w(getTag(action, method), e + "");

			throw e;
		} catch (Exception e) {
			NLog.w(getTag(action, method), e + "");

			throw new TaskException(TaskException.TaskError.resultIllegal.toString());
		}
	}





	/**
	 * 根据action和urlParams 组装请求url
	 * https://diycode.cc/api/v3/topics.json?offset=0&limit=20,
	 * 如果不是这种形式的url，例如是restful风格的url:https://api.example.com/v1/employees
	 * 则可以重写这个方法去实现
	 * @return
	 */
	public String getUrl(HttpConfig config, Setting action, Params urlParams){
		return (config.baseUrl + action.getValue() + (urlParams == null ? "" : "?" + ParamsUtil.encodeToURLParams(urlParams))).replaceAll(" ", "");
	}


	protected   <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException  {
		if (responseCls.isAssignableFrom(String.class))
			return (T) resultStr;

		return JSON.parseObject(resultStr, responseCls);
	}


	public static final int READ_TIMEOUT = 30;//单位:s
	public static final int WRITE_TIMEOUT = 30;//单位:s
	public static final int CONNECT_TIMEOUT = 30;//单位:s

	OkHttpClient mOkHttpClient;
	public synchronized OkHttpClient getOkHttpClient() {
		if (mOkHttpClient == null){
			OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
			okBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
			okBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
			okBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

			//缓存
			File cacheFile = new File(FrameworkApplication.getContext().getCacheDir(), "cache");
			Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
			okBuilder.cache(cache);


			mOkHttpClient = okBuilder.build();
		}
		return mOkHttpClient;
	}

	static RequestBody createRequestBody(final MultipartFile file) {
		return new RequestBody() {

			@Override
			public MediaType contentType() {
				return MediaType.parse(file.getContentType());
			}

			@Override
			public long contentLength() throws IOException {
				return file.getBytes() != null ? file.getBytes().length : file.getFile().length();
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				Source source;
				if (file.getFile() != null) {
					source = Okio.source(file.getFile());
				}
				else {
					source = Okio.source(new ByteArrayInputStream(file.getBytes()));
				}

				OnFileProgress onFileProgress = file.getOnProgress();
				if (onFileProgress != null) {
					try {
						long contentLength = contentLength();
						long writeLen = 0;
						long readLen = -1;
						Buffer buffer = new Buffer();

						long MinProgressStep = 65536;
						long MinProgressTime = 300;

						long mLastUpdateBytes = 0;
						long mLastUpdateTime = 0l;
						while ((readLen = source.read(buffer, 8 * 1024)) != -1) {
							sink.write(buffer, readLen);
							writeLen += readLen;

							long now = System.currentTimeMillis();
							if (((writeLen - mLastUpdateBytes) > MinProgressStep &&
									(now - mLastUpdateTime) > MinProgressTime) ||
									writeLen == contentLength) {
								onFileProgress.onProgress(writeLen, contentLength);

								mLastUpdateBytes = writeLen;
								mLastUpdateTime = now;
							}
						}
					} catch (IOException e) {
						NLog.printStackTrace(e);
						throw e;
					} finally {
						Util.closeQuietly(source);
					}
				}
				else {
					try {
						sink.writeAll(source);
					} catch (IOException e) {
						throw e;
					} finally {
						Util.closeQuietly(source);
					}
				}
			}

		};
	}





}
