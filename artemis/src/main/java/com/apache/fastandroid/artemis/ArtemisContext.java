package com.apache.fastandroid.artemis;

import com.apache.fastandroid.artemis.support.bean.UserDetail;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class ArtemisContext {
    private static UserDetail mUserBean;




    public static void setUserBean(UserDetail userBean){
        mUserBean =  userBean;
    }

    public static UserDetail getUserBean(){
        return mUserBean;
    }



}
