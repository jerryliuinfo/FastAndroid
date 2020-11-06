package com.apache.fastandroid.performance;

import android.content.Context;

/**
 * author: jerry
 * created on: 2020/8/17 1:11 PM
 * description:
 */
public class LeakSingle {

    private static Context sContext;
    private static LeakSingle mLeakSingle;

    public LeakSingle(Context context) {
        sContext = context;
    }

    public static LeakSingle getInstance(Context context){
        if (mLeakSingle == null){
            mLeakSingle = new LeakSingle(context);
        }
        return mLeakSingle;
    }
}
