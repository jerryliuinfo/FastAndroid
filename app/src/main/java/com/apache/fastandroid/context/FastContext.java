package com.apache.fastandroid.context;

import android.content.Context;

import com.google.gson.Gson;

import com.apache.fastandroid.sample.singleton.Singleton;
import com.tesla.framework.Global;
import com.tesla.framework.common.util.task.PriorityThreadPool;
import com.tesla.framework.common.util.task.TimerTaskManager;

/**
 * author: jerry
 * created on: 2020/10/22 11:56 AM
 * description:
 */
public final class FastContext {
    /**
     * 时间任务管理器单例
     */
    private static Singleton<TimerTaskManager, Context> sTimerTaskManager = new Singleton<TimerTaskManager, Context>() {
        @Override
        protected TimerTaskManager create(Context context) {
            return new TimerTaskManager(context);
        }
    };

    /**
     * 获取 {@link TimerTaskManager} 的唯一实例。
     * <p/>
     * 客户代码要通过此方法来获得时间任务管理器，而不是自己创建一个。
     */
    public static TimerTaskManager getTimerTaskManager() {
        return sTimerTaskManager.get(Global.getApplicationContext());
    }

    private final static int DEFAULT_POOL_SIZE = 2;

    private final static Singleton<PriorityThreadPool, Void> sBusinessDefault = new Singleton<PriorityThreadPool, Void>() {
        @Override
        protected PriorityThreadPool create(Void aVoid) {
            return new PriorityThreadPool("business-default", DEFAULT_POOL_SIZE);
        }
    };


    /**
     * 线程池中包含2个线程，通常短时间任务都用这个
     *
     * @return 线程池
     */
    public static PriorityThreadPool getBusinessDefaultThreadPool() {
        return sBusinessDefault.get(null);
    }


    private static Singleton<Gson, Void> sGson = new Singleton<Gson, Void>() {
        @Override
        protected Gson create(Void aVoid) {
            return new Gson();
        }
    };

    public static Gson getGson() {
        return sGson.get(null);
    }




}
