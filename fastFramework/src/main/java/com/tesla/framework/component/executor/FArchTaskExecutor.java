package com.tesla.framework.component.executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executor;

/**
 * Created by Jerry on 2020-05-16.
 */
public class FArchTaskExecutor extends FTaskExecutor {
    private static volatile FArchTaskExecutor sInstance;

    @NonNull
    private FTaskExecutor mDelegate;

    @NonNull
    private FTaskExecutor mDefaultTaskExecutor;

    @NonNull
    private static final Executor sMainThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().postToMainThread(command);
        }
    };

    @NonNull
    private static final Executor sIOThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().executeOnDiskIO(command);
        }
    };

    private FArchTaskExecutor() {
        mDefaultTaskExecutor = new FDefaultTaskExecutor();
        mDelegate = mDefaultTaskExecutor;
    }

    /**
     * Returns an instance of the task executor.
     *
     * @return The singleton FArchTaskExecutor.
     */
    @NonNull
    public static FArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (FArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new FArchTaskExecutor();
            }
        }
        return sInstance;
    }

    /**
     * Sets a delegate to handle task execution requests.
     * <p>
     * If you have a common executor, you can set it as the delegate and App Toolkit components will
     * use your executors. You may also want to use this for your tests.
     * <p>
     * Calling this method with {@code null} sets it to the default FTaskExecutor.
     *
     * @param taskExecutor The task executor to handle task requests.
     */
    public void setDelegate(@Nullable FTaskExecutor taskExecutor) {
        mDelegate = taskExecutor == null ? mDefaultTaskExecutor : taskExecutor;
    }

    @Override
    public void executeOnDiskIO(Runnable runnable) {
        mDelegate.executeOnDiskIO(runnable);
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        mDelegate.postToMainThread(runnable);
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    @Override
    public boolean isMainThread() {
        return mDelegate.isMainThread();
    }
}
