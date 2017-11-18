package com.apache.fastandroid.support.http.volly;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apache.fastandroid.support.utils.MainLog;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.IHttpUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.http.ParamsUtil;
import com.tesla.framework.network.task.TaskException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by 01370340 on 2017/11/17.
 */

public class VollyUtility implements IHttpUtility {

    @Override
    public <T> T doGet(HttpConfig config, Setting action, Params urlParams, final Class<T> responseCls) throws TaskException {
        final String requestUrl = (config.baseUrl + action.getValue() + (urlParams == null || urlParams.size() == 0? "" : "?" + ParamsUtil.encodeToURLParams(urlParams))).replaceAll(" ", "");
        //阻塞队列
        final BlockingQueue<T> blockingQueue = new LinkedBlockingQueue<>(1);
        //这里是第3方的http请求，是通过异步回调的，需要变为同步返回
        StringRequest stringRequest = new MyStringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //解析服务器返回的数据
                    T result  = parseResponse(response,responseCls);
                    if (result != null){
                        blockingQueue.offer(result,5,TimeUnit.SECONDS);
                        NLog.d(MainLog.getLogTag(), "VollyUtility  offer time = %s",System.currentTimeMillis());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    //无论如何 在finnly 如果没有添加过元素,一定要添加一个元素 否则会一直阻塞
                    if (blockingQueue.isEmpty()){
                        //blockingQueue.put();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(FrameworkApplication.getContext()).add(stringRequest);

        try {
            //这里会一直等待,除非到了超时时间或者队列中有了元素
            T result =  blockingQueue.poll(30, TimeUnit.SECONDS);
            NLog.d(MainLog.getLogTag(), "VollyUtility  take time = %s",System.currentTimeMillis());
            if (result == null){
                throw new TaskException("VollyUtility onFailed");
            }
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T doPost(HttpConfig config, Setting action, Params urlParams, final Params bodyParams, Object requestObj,
                        Class<T> responseCls) throws TaskException {

        String requestUrl = (config.baseUrl + action.getValue() + (urlParams == null || urlParams.size() == 0? "" : "?" + ParamsUtil.encodeToURLParams(urlParams))).replaceAll(" ", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

            }
        }, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if (bodyParams != null){
                    for (int i = 0; i < bodyParams.size(); i++){
                        String key = bodyParams.getKeys().get(i);
                        params.put(key,bodyParams.getParameter(key));
                    }
                }

                return params;
            }
        };
        Volley.newRequestQueue(FrameworkApplication.getContext()).add(stringRequest);

        return null;

    }

    @Override
    public <T> T doPostFiles(HttpConfig config, Setting action, Params urlParams, Params bodyParams, MultipartFile[] files, Class<T> responseCls) throws TaskException {
        return null;
    }



    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException  {
        NLog.w("BizLogic",  "parseResponse = %s", resultStr);
        if (responseCls.getSimpleName().equals("String"))
            return (T) resultStr;

        T result = JSON.parseObject(resultStr, responseCls);
        return result;
    }
}
