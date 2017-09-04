package com.apache.fastandroid.artemis;

import com.apache.fastandroid.artemis.support.bean.Token;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class ArtemisContext {
    private static Token mToken;

    public static void setToken(Token token){
        mToken = token;
    }

    public static Token getToken(){
        return mToken;
    }
}
