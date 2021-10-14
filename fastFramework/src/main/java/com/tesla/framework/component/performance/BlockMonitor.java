package com.tesla.framework.component.performance;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.tesla.framework.common.util.log.NLog;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 */

public class BlockMonitor {
    private static final String TAG = "=============BlockMonitor============= \n ";
    private static BlockMonitor sInstance = new BlockMonitor();
    private Handler mIoHandler;
    //方法耗时的卡口,500毫秒
    private static final long TIME_BLOCK = 500L;
    //存放一个msg周期的卡顿堆栈信息，防止重复打印
    private Set<String> mBlockStackTrace;

    private BlockMonitor() {
        HandlerThread logThread = new HandlerThread("BlockMonitor");
        logThread.start();
        mIoHandler = new Handler(logThread.getLooper());
        mBlockStackTrace = Collections.synchronizedSet(new HashSet<String>());
    }

    private Runnable mLogRunnable = new Runnable() {
        @SuppressLint("LongLogTag")
        @Override
        public void run() {
            //继续检测
            startMonitor();
            //打印出执行的耗时方法的栈消息
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString());
                sb.append("\n");
            }
            String s = sb.toString();
            if (!mBlockStackTrace.contains(s)) {
                mBlockStackTrace.add(s);
                //BlockLogUtils.e(TAG, s);
                NLog.e(TAG, "stackTrace = %s", s);
            }
        }
    };

    public static BlockMonitor getInstance() {
        return sInstance;
    }

    /**
     * 开始计时
     */
    public void startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    /**
     * 停止计时
     */
    public void removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
        mBlockStackTrace.clear();
    }
}