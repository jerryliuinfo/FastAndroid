package com.apache.fastandroid;

import android.view.View;
import com.apache.fastandroid.util.MainLogUtil;

/**
 * Created by liujian on 2018/4/11.
 */

public class LogUtils {

    public static void system(View v){
        MainLogUtil.d("MainActivity-asm onclick ");
    }
}
