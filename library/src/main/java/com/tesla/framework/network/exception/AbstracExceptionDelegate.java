package com.tesla.framework.network.exception;

import android.content.Context;
import android.text.TextUtils;

import com.tesla.framework.network.task.IExceptionDeclare;
import com.tesla.framework.network.task.TaskException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 01370340 on 2017/11/27.
 */

public abstract class AbstracExceptionDelegate implements IExceptionDeclare {

    private static  String[][] errorMsgs = null;
    private static final Map<String, String> errorMap = new HashMap<>();




    private  void initErrorMap(){
        if (errorMsgs == null){
            errorMsgs = generateCodeMsgMap();
        }
        if (errorMap.size() == 0 && errorMsgs != null && errorMsgs.length > 0 ){
            for (String[] errorArr : errorMsgs) {
                errorMap.put(errorArr[0], errorArr[1]);
            }
        }
    }


    protected  abstract String[][] generateCodeMsgMap();



    /**
     * 根据服务器返回的字符串做异常检测
     * @param response
     * @throws TaskException
     */
    @Override
    public void checkResponse(String response) throws TaskException {
        initErrorMap();
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
        if (!TextUtils.isEmpty(msg)){
            throw new TaskException(code,msg);
        }

        throw new TaskException(code);
    }

    @Override
    public String checkCode(String code) {
        initErrorMap();
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
