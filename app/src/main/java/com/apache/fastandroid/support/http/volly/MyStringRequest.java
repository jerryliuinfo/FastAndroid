package com.apache.fastandroid.support.http.volly;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by 01370340 on 2017/11/18.
 */

public class MyStringRequest extends StringRequest {
    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener
            errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String formatStr = new String(response.data,"UTF-8");
            return Response.success(formatStr, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new VolleyError(e.getMessage()));
        }
    }
}
