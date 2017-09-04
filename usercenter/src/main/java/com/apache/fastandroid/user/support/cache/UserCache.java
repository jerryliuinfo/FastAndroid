package com.apache.fastandroid.user.support.cache;

import com.apache.fastandroid.artemis.ArtemisContext;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.tesla.framework.support.cache.DataCache;

/**
 * Created by 01370340 on 2017/9/4.
 */

public class UserCache {

    private static DataCache mCache = new DataCache("user");

    public static UserDetail getMe(){
        return mCache.getData("Gcs_Me_");
    }

    public static void saveMe(UserDetail me){
        mCache.saveData("Gcs_Me_", me);
        ArtemisContext.setUserBean(me);
    }
    public static void clearMe(){
        mCache.removeDate("Gcs_Me_");
    }
}
