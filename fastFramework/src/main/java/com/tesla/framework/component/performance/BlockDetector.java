package com.tesla.framework.component.performance;

import android.os.Looper;
import android.util.Printer;
import com.tesla.framework.common.util.DebugUtils;
import com.tesla.framework.common.util.log.NLog;

/**
 *
 * 检测主线程卡顿
 */

public class BlockDetector {

    public static final String TAG = BlockDetector.class.getSimpleName();
    public static void init() {
        if(DebugUtils.isDebug()) {
            Looper.getMainLooper().setMessageLogging(new Printer() {
                //分发和处理消息开始前的log
                private static final String START = ">>>>> Dispatching";
                //分发和处理消息结束后的log
                private static final String END = "<<<<< Finished";

                @Override
                public void println(String x) {
                    NLog.d(TAG,"BlockDetector init println x = %s",x);
                    if (x.startsWith(START)) {
                        //开始计时
                        BlockMonitor.getInstance().startMonitor();
                    }
                    if (x.startsWith(END)) {
                        //结束计时
                        BlockMonitor.getInstance().removeMonitor();
                    }
                }
            });
        }
    }
}
