package com.apache.fastandroid.topic.app;

import android.content.Context;
import com.apache.fastandroid.topic.cache.TopicDb;
import com.tesla.framework.applike.DefApplicationLike;

/**
 * Created by 01370340 on 2017/9/23.
 */

public class TopicAppLike extends DefApplicationLike {
    @Override
    public void onCreate(Context context) {
        TopicDb.initDb(context);
    }

    @Override
    public void onStop() {
    }
}
