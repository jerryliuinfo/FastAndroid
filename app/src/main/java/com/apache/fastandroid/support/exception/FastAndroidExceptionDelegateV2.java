package com.apache.fastandroid.support.exception;

import com.tesla.framework.network.exception.AbstracExceptionDelegate;

/**
 * Created by 01370340 on 2017/9/1.
 * 定义异常code和对应msg
 */

public class FastAndroidExceptionDelegateV2 extends AbstracExceptionDelegate{

    private static final String[][] errorMsgs = new String[][] { { "0", "用户名或密码不合法" }, { "101", "token失效" }, { "10010", "任务超时" },
    };
    @Override
    protected String[][] generateCodeMsgMap() {
        return errorMsgs;
    }
}
