package com.apache.fastandroid.support.exception;

import android.text.TextUtils;

import com.tesla.framework.network.task.IExceptionDeclare;
import com.tesla.framework.network.task.TaskException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class FastAndroidErrorMsg  implements IExceptionDeclare{
    private static final Map<String, String> errorMap = new HashMap<String, String>();

    /**
     * 根据服务器返回的错误代码，返回错误信息
     *
     * @param errorMsg
     * @return
     */
    private static final String[][] errorMsgs = new String[][] { { "100", "用户名或密码不合法" }, { "101", "token失效" }, { "10010", "任务超时" },
            };

    static {
        for (String[] errorArr : errorMsgs) {
            errorMap.put(errorArr[0], errorArr[1]);
        }
    }

    @Override
    public void checkResponse(String response) throws TaskException {
        String code = null;
        String msg = null;
        if (TextUtils.isEmpty(response)){
            msg = response;
        }
        throw new TaskException(code,msg);
    }

    @Override
    public String checkCode(String code) {
        if (errorMap.containsKey(code))
            return errorMap.get(code);

        return null;
    }
}
