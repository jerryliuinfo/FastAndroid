package com.apache.fastandroid.topic.support.exception;

import android.content.Context;
import android.text.TextUtils;

import com.tesla.framework.network.task.IExceptionDeclare;
import com.tesla.framework.network.task.TaskException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class FastAndroidExceptionDelegate implements IExceptionDeclare{
    private static final Map<String, String> errorMap = new HashMap<>();


    /**
     * 登陆超时
     *//*
    public static final int LOGIN_TIMEOUT = 0;
    *//**
     * 服务器错误
     *//*
    public static final int SERVER_ERROR = 1;
    *//**
     * 网络错误
     *//*
    public static final int NETWORK_ERROR = 2;
    *//**
     * 账号不存在
     *//*
    public static final int ACCOUNT_NOT_FOUND = 3;
    *//**
     * 登陆失败
     *//*
    public static final int FAILED_LOGIN = 4;
    *//**
     * 账号被锁定
     *//*
    public static final int ACCOUNT_LOCKED = 5;
    *//**
     * 获取ticket失败
     *//*
    public static final int GET_TICKET_FAILED = 6;
    *//**
     * 用户名/密码错误
     *//*
    public static final int INVALID_PASSWORD = 7;*/

    /**
     * 根据服务器返回的错误代码，返回错误信息
     *
     * @param errorMsg
     * @return
     */
    private static final String[][] errorMsgs = new String[][] { { "0", "用户名或密码不合法" }, { "101", "token失效" }, { "10010", "任务超时" },
            };

    static {
        for (String[] errorArr : errorMsgs) {
            errorMap.put(errorArr[0], errorArr[1]);
        }
    }

    /**
     * 根据服务器返回的字符串做异常检测
     * @param response
     * @throws TaskException
     */
    @Override
    public void checkResponse(String response) throws TaskException {
        String code = null;
        String msg = null;
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("code")){
                    code = jsonObject.getString("code");
                    if (jsonObject.has("msg")){
                        msg = jsonObject.getString("msg");
                    }
                    if (TextUtils.isEmpty(msg)){
                        msg = checkCode(code);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(msg)){
            throw new TaskException(code,msg);
        }
        if (!TextUtils.isEmpty(code)){
            TaskException taskException = new TaskException();
            taskException.setCode(code);
            throw taskException;
        }
    }

    @Override
    public String checkCode(String code) {
        if (errorMap.containsKey(code))
            return errorMap.get(code);

        return null;
    }


    public static String getMessage(Context context, TaskException exception, int defRes) {
        try {
            if (context != null) {
                String message = exception.getMessage();
                if (!TextUtils.isEmpty(message)) {
                    return message;
                }
                else {
                    return context.getString(defRes);
                }
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        return "";
    }
}
