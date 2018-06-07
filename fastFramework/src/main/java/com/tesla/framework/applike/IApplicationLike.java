package com.tesla.framework.applike;

import android.content.Context;

/**
 * Created by 01370340 on 2017/9/23.
 */

public interface IApplicationLike {

    void onCreate(Context context);

    void onStop();

    void onTrimMemory();


}
