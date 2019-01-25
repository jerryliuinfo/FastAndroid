package com.apache.fastandroid.topic.support.http.volly;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apache.fastandroid.topic.support.utils.MainLog;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.setting.Setting;
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
        final BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>(1);
        StringRequest stringRequest = new MyStringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                T result;
                try {
                    result  = parseResponse(response,responseCls);
                    if (result != null){
                        blockingQueue.offer(result,5,TimeUnit.SECONDS);
                        MainLog.d( "VollyUtility  offer time = %s",System.currentTimeMillis());
                    }else {
                        blockingQueue.offer(new TaskException("数据为空"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    blockingQueue.offer(new TaskException(e.getMessage()));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //这里获取数据错误了，也需要添加一个元素到queue吧?要不然只能等待blockingQueue超时才能继续往下执行
                blockingQueue.offer(new TaskException(error.getMessage()));
            }
        });
        Volley.newRequestQueue(FrameworkApplication.getContext()).add(stringRequest);
        try {
            //这里会一直等待,除非到了超时时间或者队列中有了元素
            Object result =  blockingQueue.poll(30, TimeUnit.SECONDS);
            MainLog.d( "VollyUtility  take time = %s",System.currentTimeMillis());
            //这里判断列队如果没有添加元素
            if (result == null ){
                throw new TaskException("timeout");
            }
            if (result instanceof TaskException){
                throw (TaskException) result;
            }
            return (T) result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T doPost(HttpConfig config, Setting action, Params urlParams, final Params bodyParams, Object requestObj,
                        final Class<T> responseCls) throws TaskException {
        final String requestUrl = (config.baseUrl + action.getValue() + (urlParams == null || urlParams.size() == 0? "" : "?" + ParamsUtil.encodeToURLParams(urlParams))).replaceAll(" ", "");
        //阻塞队列
        final BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>(1);
        StringRequest stringRequest = new MyStringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                T result;
                try {
                    result  = parseResponse(response,responseCls);
                    if (result != null){
                        blockingQueue.offer(result,5,TimeUnit.SECONDS);
                        MainLog.d( "VollyUtility  offer time = %s",System.currentTimeMillis());
                    }else {
                        blockingQueue.offer(new TaskException("数据为空"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    blockingQueue.offer(new TaskException(e.getMessage()));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //这里获取数据错误了，也需要添加一个元素到queue吧?要不然只能等待blockingQueue超时才能继续往下执行
                blockingQueue.offer(new TaskException(error.getMessage()));
            }
        }){
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
        try {
            //这里会一直等待,除非到了超时时间或者队列中有了元素
            Object result =  blockingQueue.poll(30, TimeUnit.SECONDS);
            MainLog.d("VollyUtility  take time = %s",System.currentTimeMillis());
            //这里判断列队如果没有添加元素
            if (result == null ){
                throw new TaskException("timeout");
            }
            if (result instanceof TaskException){
                throw (TaskException) result;
            }
            return (T) result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public <T> T doPostFiles(HttpConfig config, Setting action, Params urlParams, Params bodyParams, MultipartFile[] files, Class<T> responseCls) throws TaskException {
        return null;
    }



    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException  {
        if (responseCls.getSimpleName().equals("String"))
            return (T) resultStr;

        T result = JSON.parseObject(resultStr, responseCls);
        return result;
    }
}
