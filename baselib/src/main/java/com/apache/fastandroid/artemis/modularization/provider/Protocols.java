package com.apache.fastandroid.artemis.modularization.provider;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.Context;
import caom.apache.fastandroid.artemis.BuildConfig;
import com.apache.fastandroid.artemis.BaseApp;
import com.apache.fastandroid.artemis.BaselibLogUtil;

/**
 * author: 01370340
 * data: 2019/6/13
 * description:
 */
public class Protocols {


    public static ProtocalA getTopicProtocal(){
        String authority = BuildConfig.DEBUG? "PROVIDER_TOPIC_DEBUG": "PROVIDER_TOPIC";
        BaselibLogUtil.d("getTopicProtocal authority = %s", authority);
        return (ProtocalA) get(authority);
    }



    public static ContentProvider get(String authority){
        try {
            Context context = BaseApp.getContext();
            ContentProviderClient client  = context.getContentResolver().acquireContentProviderClient(authority);
            if (client != null) {
                ContentProvider provider = client.getLocalContentProvider();
                return provider;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
