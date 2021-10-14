package com.tesla.framework.component.interceptor.encrypt;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.tesla.framework.common.util.GlobalConfig;
import com.tesla.framework.common.util.RSAUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Invocation;

/**
 * description:
 * author chaojiong.zhang
 * data: 2021/6/28
 * copyright TCL+
 */

//加密拦截器，统一在这里加密
public class ACEncrpyInterceptor implements Interceptor {
    public static final String MIMETYPE_FORMBODY = "application/x-www-form-urlencoded";
    public static final String MIMETYPE_JSON = "application/json";

    @Override
    public @NonNull
    Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request newRequest = oldRequest;
        //获取注解类型
        int encryptType = getEncryptType(oldRequest);

        switch (encryptType) {
            case ACEncrypt.OnlyQuery:
                newRequest = encryptQueryRequest(oldRequest);
                break;
            case ACEncrypt.OnlyBody:
                newRequest = encryptBodyRequest(oldRequest);
                break;
            case ACEncrypt.QueryAndBody:
                newRequest = encryptQueryRequest(oldRequest);
                newRequest = encryptBodyRequest(newRequest);
                break;
            //无需加密的，不做任何动作
            case ACEncrypt.None:
            default:
        }
        return chain.proceed(newRequest);
    }


    // 加密 query
    private Request encryptQueryRequest(Request request) {
        try {
            //Request 的 Builder
            Request.Builder mBuilder = request.newBuilder();
            //url 的 Builder
            HttpUrl.Builder mUrlBuilder = request.url().newBuilder();
            //找到参数串
            String queryStr = request.url().query();
            StringBuilder newQueryBuilder = new StringBuilder();
            //转成 Map 结构
            ArrayMap<String, String> mQueryMap = toQueryNamesAndValuesMap(queryStr);
            if (mQueryMap != null && mQueryMap.size() > 0) {
                Set<Map.Entry<String, String>> mQuerySets = mQueryMap.entrySet();
                boolean isFirst = true;
                for (Map.Entry<String, String> entry : mQuerySets) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        newQueryBuilder.append('&');
                    }
                    //做加密
                    newQueryBuilder.append(entry.getKey() + "=" + escapeSpecialChar(RSAUtil.encodeNoPadding(entry.getValue())));
                }
            }
            //替换成加密的Query
            mUrlBuilder.encodedQuery(newQueryBuilder.toString());
            return mBuilder.url(mUrlBuilder.build()).header("Encrypt", "true").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    // 加密body
    private Request encryptBodyRequest(Request request) {
        try {
            RequestBody oldBody = request.body();
            String mimeType;
            if (oldBody == null || oldBody.contentType() == null
                    || (mimeType = oldBody.contentType().toString()) == null) {
                return request;
            }

            if (!mimeType.contains(MIMETYPE_FORMBODY) && !mimeType.contains(MIMETYPE_JSON)) {
                return request;
            }

            //Request 的 Builder
            Request.Builder mBuilder = request.newBuilder();

            Buffer buffer = new Buffer();

            oldBody.writeTo(buffer);

            String strOldBody = buffer.readUtf8();

            String strNewBody = RSAUtil.encodeNoPadding(strOldBody);
            RequestBody body = RequestBody.create(strNewBody, oldBody.contentType());
            return mBuilder.header("Encrypt", "true").method(request.method(), body).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

   // query 需要替换 = ， body 中的加密后不用替换
    private String escapeSpecialChar(String value) {
        if (value == null) return null;
        return value.replace("=", "%3D");
    }


    /**
     * copy form retrofit source
     *
     * @param queryStr
     * @return
     */
    private ArrayMap<String, String> toQueryNamesAndValuesMap(String queryStr) {
        if (TextUtils.isEmpty(queryStr)) return null;
        ArrayMap result = new ArrayMap();
        int pos = 0;
        int length = queryStr.length();
        while (pos <= length) {
            int ampersandOffset = queryStr.indexOf('&', pos);
            if (ampersandOffset == -1) ampersandOffset = length;

            int equalsOffset = queryStr.indexOf('=', pos);
            if (equalsOffset == -1 || equalsOffset > ampersandOffset) {
                result.put(queryStr.substring(pos, ampersandOffset), null);
            } else {
                result.put(queryStr.substring(pos, equalsOffset), queryStr.substring(equalsOffset + 1, ampersandOffset));
            }
            pos = ampersandOffset + 1;
        }
        return result;
    }


    private @ACEncrypt int getEncryptType(Request request) {
        boolean isEncrypt = GlobalConfig.getInstance().isEncrypt;
        if(isEncrypt) {
            return ACEncrypt.None;
        }

        Invocation tag = request.tag(Invocation.class);
        if (tag == null) {
            return ACEncrypt.None;
        }
        Method method = tag.method();
        ACEncrypt annotation = method.getAnnotation(ACEncrypt.class);
        if (annotation == null) {
            return ACEncrypt.None;
        }
        return annotation.encryptType();
    }
}
