package com.apache.fastandroid.artemis.support;

import com.alibaba.fastjson.JSON;
import com.apache.fastandroid.artemis.support.bean.BaseBean;
import com.apache.fastandroid.artemis.support.exception.ICheck;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.http.OkHttpUtility;
import com.tesla.framework.network.task.TaskException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by 01370340 on 2017/9/29.
 * 1.对服务器返回的数据字段做一些校验
 * 2.返回bean对象
 */

public class BaseHttpUtility extends OkHttpUtility{

    /**
     * @param response
     * @param responseCls
     * @param <T>
     * @return
     * @throws TaskException
     * @throws IOException
     */

    public  <T> T parseResponse(Response response, Class<T> responseCls) throws TaskException, IOException {
        String responseStr = response.body().toString();
        NLog.d("DefHttpUtility", "responseStr = %s", response);
        if (responseCls.isAssignableFrom(String.class))
            return (T) responseStr;

        return jsonToBean(responseStr,responseCls);
    }



    /**
     * 将服务器返回的json字符串转换成业务实体bean
     * @param responseStr
     * @param responseCls
     * @param <T>
     * @return
     * @throws TaskException
     */
    protected <T> T jsonToBean(String responseStr, Class<T> responseCls) throws TaskException {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responseStr);
            //校验服务器的一些字段是否合法
            if (!jsonObject.has("code")){
                throw new TaskException("code字段 为空");
            }
            if (!jsonObject.has("details")){
                throw new TaskException("details字段 为空");
            }
            //解析
            T dataBean = JSON.parseObject(jsonObject.getString("details"),responseCls);


            //处理业务异常
            if (dataBean instanceof ICheck){
                ICheck iCheck = (ICheck) dataBean;
                iCheck.check();
            }

            //把公共字段 code,msg等字段塞进去
            if (dataBean instanceof BaseBean){

                BaseBean baseResultBean = (BaseBean) dataBean;

                try {
                    if (jsonObject.has("code")){
                        baseResultBean.code = jsonObject.getString("code");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (jsonObject.has("msg")){
                        baseResultBean.msg = jsonObject.getString("msg");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            return dataBean;

        } catch (JSONException e) {
            e.printStackTrace();
            throw  new TaskException("json解析错误");
        }
    }
}
