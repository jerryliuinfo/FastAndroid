package com.apache.fastandroid.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jerryliu on 2017/6/8.
 */

public class DownloadManager  {
    public static final String TAG = DownloadManager.class.getSimpleName();
    /**
     * 默认核心线程是5个
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 默认执行最大线程是128个
     */
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE_TIME = 1;

    private static final ThreadFactory mThreadFactory = new ThreadFactory() {
        AtomicInteger count = new AtomicInteger(1);
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,"WorkTask # "+count.getAndIncrement());
        }
    };

    private static LinkedBlockingQueue<Runnable> mPoolWorkQueue = new LinkedBlockingQueue<>(10);

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS,mPoolWorkQueue,mThreadFactory);

    private static final InternalHandler sHandler = new InternalHandler();
    private TaskException exception;

    public enum Status {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING,
        /**
         * Indicates that the task is running.
         */
        RUNNING,
        /**
         * Indicates that the task has finished.
         */
        FINISHED,
    }


    private volatile Status mStatus = Status.PENDING;
    private IDownLoadCallback callback;

    private  WorkerRunnable mWorker;
    private  FutureTask mFuture = null;
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();
    public DownloadManager() {
        mWorker = new WorkerRunnable() {

            @Override
            public Object call() throws Exception {
                mTaskInvoked.set(true);
                NLog.d(TAG, "WorkerRunnable call");
                return postResult(doInBackground(mUrl));
            }
        };
        mFuture = new FutureTask(mWorker) {
            @Override
            protected void done() {
                NLog.d(TAG, "FutureTask done");
                try {
                    Object obj = get();
                    NLog.d(TAG, "FutureTask done obj = %s", obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void setCallback(IDownLoadCallback callback) {
        this.callback = callback;
    }

    public void execute(String url){
        onPreExecute();
        mStatus = Status.RUNNING;
        mWorker.mUrl = url;
        THREAD_POOL_EXECUTOR.execute(mFuture);

    }


    private void finish(Object result) {
        if (isCancelled()) {
            onCancelled();
        } else {
            onPostExecute(result);
        }
        mStatus = Status.FINISHED;
    }


    final protected void onPreExecute() {
        NLog.d(TAG, "onPreExecute");
        onPrepare();
    }

    /**
     * 线程开始执行
     */
    protected void onPrepare() {
        if (callback != null){
            callback.onStart();
        }
    }

    protected void onCancelled() {
    }

    public Object workInBackground(String url) throws TaskException{
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NLog.d(TAG, "doInBackground");

        return null;
    }

    final protected void onPostExecute(Object result) {
        if (exception == null) {
            NLog.d(TAG, String.format("onTaskSuccess()"));
            onSuccess(result);
        }
        else if (exception != null) {
            onFailure(exception);
        }

        onFinished();
    }

    /**
     */
    protected void onFailure(TaskException exception) {
        if (callback != null){
            callback.onFailed();
        }
    }

    /**
     * 没有抛出异常，且<tt>Result</tt>不为<tt>Null</tt>
     */
    protected void onSuccess(Object result) {
        if (callback != null){
            callback.onSuccess();
        }
    }

    public final boolean isCancelled() {
        return mFuture.isCancelled();
    }

    /**
     * 线程结束，不管线程结束是什么状态，都会执行这个方法
     */
    protected void onFinished() {
        NLog.d(TAG, "onFinished");
        if (callback != null){
            callback.onFinish();
        }
    }



    private Object postResult(Object result) {
        Message message = sHandler.obtainMessage(MESSAGE_POST_RESULT, new AsyncTaskResult<>(this,result));
        message.sendToTarget();
        return result;
    }

    public Object doInBackground(String url){
        try {
            return workInBackground(url);
        } catch (TaskException e) {
            e.printStackTrace();
            exception = e;
        }


        return null;
    }




    private static abstract class WorkerRunnable<Params, Result> implements Callable{
        String mUrl;
    }


    private static class InternalHandler extends Handler {

        InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({ "unchecked" })
        @Override
        public void handleMessage(Message msg) {
            AsyncTaskResult result = (AsyncTaskResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    // There is only one result
                    result.mTask.finish(result.mData[0]);
                    break;

            }
        }
    }

    private static final int MESSAGE_POST_RESULT = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;

    private static class AsyncTaskResult<Data> {
        final DownloadManager mTask;
        final Data[] mData;

        AsyncTaskResult(DownloadManager downloadManager,Data... data) {
            mTask = downloadManager;
            mData = data;
        }
    }
}
