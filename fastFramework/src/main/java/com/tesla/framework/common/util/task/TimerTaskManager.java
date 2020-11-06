/*
 * @author：kenli
 * @version：1.0.0
 * @created：2014-10-9 下午8:16
 * @remark：
 *
 * Copyright (C) 1998 - 2014 Tencent. All Rights Reserved
 */
package com.tesla.framework.common.util.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;

import com.tesla.framework.common.util.FrameworkLogUtil;


/**
 * 作为一个任务调度中心，用于调度重复性任务。
 * 对于执行一次性任务的需求，用 postDelay 即可，无需使用此类。
 * 为了不影响其他任务的调度，任务应该尽可能短，建议 post 到 ui 线程上面执行（如果任务在 Timer 线程上执行较长时间，会影响其他任务的调度）。
 * <p/>
 * 尽量使用此类，而不是自己弄一个 Timer，以避免性能浪费。
 * <p/>
 * 调用：
 * 请记得一定要调用 {@link TimerTaskManager#cancel(String)}，避免任务泄露。
 * <p/>
 * 注意：
 * 此类的设计已经避免了在取消任务后，仍有 runnable 被 post 到 ui 线程上的情况。
 * <p/>
 * 命名：
 * 此类用任务名来区分任务。
 * 注意！为了能更好的调试程序，任务名要取有意义的，别随便乱取，最好长一点，避免重复。
 * 以下是推荐的任务名命名方式（具体请参照音准器}）：
 * "模块名称" + "_" + "有意义的任务名称"
 *
 * @author kenli
 */
public class TimerTaskManager {

    private static final String TAG = "TimerTaskManager";

    /**
     * 线程池调度器
     */
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    /**
     * 用于存储<任务名-任务>的 map
     */
    private Map<String, InternalTaskEntry> mNameMap = new HashMap<String, InternalTaskEntry>();

    /**
     * <p/>
     * <p/>
     * 而不是自己创建此类的一个新实例。
     *
     * @param context 提供给组件使用的 Context，请传入 App 的 Context
     */
    public TimerTaskManager(Context context) {
        ensureTimerInstance();
    }

    /**
     * 在指定延迟后，以指定时间间隔重置执行任务
     *
     * @param taskName 任务名（为了能更好的查找问题，请用有意义的长名称）。
     *                 如果任务名和已存在的任务名重复了，旧任务将会被取消，新任务将会替代旧任务。
     * @param delay    第一次执行前的延迟（单位ms）
     * @param period   重复执行的时间间隔（单位ms）
     * @param runnable 任务的执行体
     * @throws IllegalArgumentException {@code taskName == null} or
     *                                            {@code delay < 0} or
     *                                            {@code period <= 0} or
     *                                            {@code runnable == null}.
     */
    public synchronized void schedule(
            String taskName,
            long delay,
            long period,
            TimerTaskRunnable runnable) {
        FrameworkLogUtil.d( String.format("schedule begin [%s].", taskName));
        

        if (taskName == null) {
            throw new IllegalArgumentException("taskName 参数不能为 null.");
        }
        if (delay < 0 || period <= 0) {
            throw new IllegalArgumentException("delay 或者 period 不合法.");
        }
        if (runnable == null) {
            throw new IllegalArgumentException("runnable 参数不能为 null.");
        }

        ensureTimerInstance();

        // 如果有重名任务，则移除前一个
        if (mNameMap.containsKey(taskName)) {
            FrameworkLogUtil.d( String.format("schedule -> cancel duplication of name[%s].", taskName));
            cancel(taskName);
        }

        // 调度新任务
        FrameworkLogUtil.d( String.format("schedule -> create new Task [%s][period : %d].", taskName, period));
        InternalTaskEntry entry = InternalTaskEntry.createEntry(runnable);
        entry.mPeriod = period;
        entry.mTaskName = taskName;
        entry.sckeduledTask = mScheduledThreadPoolExecutor.scheduleWithFixedDelay(entry.mRunnableProxy, delay, period, TimeUnit.MILLISECONDS);
        mNameMap.put(taskName, entry);

        FrameworkLogUtil.d( String.format("schedule end [%s].", taskName));
    }

    public synchronized boolean hasTask(String taskName) {
        return mNameMap.containsKey(taskName);
    }

    /**
     * 取消指定任务的执行。
     *
     * @param taskName 任务名
     */
    public synchronized void cancel(String taskName) {
        // 取消任务
        InternalTaskEntry entry = mNameMap.get(taskName);
        if (entry != null) {
            FrameworkLogUtil.d( String.format("cancel -> cancel TimerTask [%s].", taskName));
            if (entry.sckeduledTask != null) {
                entry.sckeduledTask.cancel(true);
            }
            boolean tmp = mScheduledThreadPoolExecutor.remove(entry.mRunnableProxy);
            mScheduledThreadPoolExecutor.purge();
            FrameworkLogUtil.d( "cancel -> cancel TimerTask:" + tmp + "\n" + mScheduledThreadPoolExecutor.toString());
            entry.mRunnable.mIsValid = false;
            entry.mRunnable = null;

            mNameMap.remove(taskName);
        } else {
            FrameworkLogUtil.d( String.format("cancel -> not find task[%s].", taskName));
        }
    }

    /**
     * 确保 Timer 存在。
     * 因为 Timer 在没有任务的时候会销毁（以节省性能），此方法应该在每次加入任务之前被调用一次。
     */
    private void ensureTimerInstance() {
        if (mScheduledThreadPoolExecutor == null) {
            mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2) {
                @Override
                protected void afterExecute(Runnable r, Throwable t) {
                    super.afterExecute(r, t);
                    if (t == null && r instanceof Future<?>) {
                        try {
                            Future<?> future = (Future<?>) r;
                            if (future.isDone())
                                future.get();
                        } catch (CancellationException ce) {
                            t = ce;
                        } catch (ExecutionException ee) {
                            t = ee.getCause();
                        } catch (InterruptedException ie) {

                        }
                    }
                    if (t != null) {
                        FrameworkLogUtil.d( "Exception happen when execute task! : " + t.toString());
                    }
                }
            };
        }
    }

    /**
     * 在没有任务要执行的时候，尝试销毁定时器
     */
    private synchronized void shutdown() {
        if (this.mScheduledThreadPoolExecutor != null) {
            if (mNameMap.isEmpty()) {
                FrameworkLogUtil.d( "shutdown ScheduledThreadPoolExecutor");
                mScheduledThreadPoolExecutor.shutdown();
                mScheduledThreadPoolExecutor = null;
            }
        }
    }

    /**
     * 内部用于表示一个任务的类。
     */
    private static class InternalTaskEntry {

        /**
         * 任务对应的 runnable，它起一个代理作用，实际上调用的是传入的 runnable
         */
        private Runnable mRunnableProxy = new Runnable() {
            @Override
            public void run() {
                if (mRunnable != null) {
//                    boolean success = KaraokeContext.getDefaultMainHandler().post(mRunnable);
//                    FrameworkLogUtil.d( String.format("TimerTask -> post : %s; %b;", mTaskName, success));
//                    FrameworkLogUtil.d( String.format("TimerTask -> run : %s", mTaskName));
                    mRunnable.run();
                }
            }
        };

        /**
         * 任务执行的时间间隔
         */
        private long mPeriod = Long.MIN_VALUE;

        /**
         * 任务的实际执行体
         */
        private TimerTaskRunnable mRunnable;

        /**
         * 任务名称
         */
        private String mTaskName;

        private ScheduledFuture<?> sckeduledTask;

        /**
         * 创建实例
         *
         * @param runnable
         * @return
         */
        public static InternalTaskEntry createEntry(TimerTaskRunnable runnable) {
            InternalTaskEntry result = new InternalTaskEntry();
            runnable.mIsValid = true;
            result.mRunnable = runnable;
            return result;
        }

        @Override
        public String toString() {
            return String.format(
                    "Period = %d; IsValid = %b;",
                    mPeriod,
                    mRunnable != null && mRunnable.mIsValid
            );
        }
    }

    /**
     * 这是一个靠谱的 runnable 实现，不会因为 Timer 延迟而多执行一次。
     * 这样设计的目的是为了避免 runnable post 到 ui 线程的同时，取消任务，runnable 仍然有机会被执行的问题。
     * 请在执行前，调用一下 {@link TimerTaskRunnable#isCancelled} 判断任务是否已经被取消。
     */
    public static abstract class TimerTaskRunnable implements Runnable {

        /**
         * 标记任务是否仍然在跑
         */
        private volatile boolean mIsValid;

        /**
         * final 的目的是禁止 override 此方法
         */
        @Override
        final public void run() {
            if (mIsValid) {
                onExecute();
            }
        }

        /**
         * 客户代码需要执行的代码，放在这个方法里面
         */
        public abstract void onExecute();

        /**
         * 判断任务是否被 cancel 掉。
         *
         * 否则返回 false；
         */
        public boolean isCancelled() {
            return !mIsValid;
        }
    }
}