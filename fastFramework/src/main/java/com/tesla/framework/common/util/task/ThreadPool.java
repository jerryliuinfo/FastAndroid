package com.tesla.framework.common.util.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tesla.framework.common.util.FrameworkLogUtil;

/**
 * author: jerry
 * created on: 2020/10/22 12:06 PM
 * description:
 */
public class ThreadPool {
    private static final String TAG = "ThreadPool";

    protected static final int DEFAULT_POOL_SIZE = 4;
    protected static final int KEEP_ALIVE_TIME = 10; // 10 seconds

    // Resource type
    public static final int MODE_NONE = 0;
    public static final int MODE_CPU = 1;
    public static final int MODE_NETWORK = 2;

    public static final JobContext JOB_CONTEXT_STUB = new JobContextStub();

    ResourceCounter mCpuCounter = new ResourceCounter(2);
    ResourceCounter mNetworkCounter = new ResourceCounter(2);

    // A Job is like a Callable, but it has an addition JobContext parameter.
    public interface Job<T> {
        T run(JobContext jc);
    }

    public interface JobContext {
        boolean isCancelled();

        void setCancelListener(CancelListener listener);

        boolean setMode(int mode);
    }

    private static class JobContextStub implements JobContext {
        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public void setCancelListener(CancelListener listener) {
        }

        @Override
        public boolean setMode(int mode) {
            return true;
        }
    }

    public interface CancelListener {
        public void onCancel();
    }

    private static class ResourceCounter {
        public int value;

        public ResourceCounter(int v) {
            value = v;
        }
    }

    private final Executor mExecutor;

    /**
     * 按照executor创建线程池
     * 如果没有指定executor,返回默认最大数目为2的线程池
     *
     * @param executor
     */
    public ThreadPool(Executor executor) {
        if (executor != null){
            mExecutor = executor;
        }else {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>(),
                    new PriorityThreadFactory("thread_pool", android.os.Process.THREAD_PRIORITY_BACKGROUND));
            threadPoolExecutor.allowCoreThreadTimeOut(true);
            mExecutor = threadPoolExecutor;
        }
    }

    public ThreadPool() {
        this("thread-pool", DEFAULT_POOL_SIZE);
    }

    public ThreadPool(String name, int poolSize) {
        this(name, poolSize, poolSize, new LinkedBlockingQueue<Runnable>());
    }

    public ThreadPool(String name, int coreSize, int maxSize, BlockingQueue<Runnable> queue) {
        this(name, coreSize, maxSize, queue, true);
    }

    public ThreadPool(String name, int coreSize, int maxSize, BlockingQueue<Runnable> queue, boolean allowCoreThreadTimeOut) {
        if (coreSize <= 0) coreSize = 1;
        if (maxSize <= coreSize) maxSize = coreSize;

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                coreSize, maxSize, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, queue,
                new PriorityThreadFactory(name,
                        android.os.Process.THREAD_PRIORITY_BACKGROUND));
        threadPoolExecutor.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
        mExecutor = threadPoolExecutor;
    }

    // Submit a job to the thread pool. The listener will be called when the
    // job is finished (or cancelled).
    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener) {
        Worker<T> w = new Worker<T>(job, listener);
        mExecutor.execute(w);
        return w;
    }

    public <T> Future<T> submit(Job<T> job) {
        return submit(job, null);
    }

    private class Worker<T> implements Runnable, Future<T>, JobContext, Comparable<Worker> {
        private static final String TAG = "Worker";
        private final Job<T> mJob;
        private final FutureListener<T> mListener;
        private CancelListener mCancelListener;
        private ResourceCounter mWaitOnResource;
        private volatile boolean mIsCancelled;
        private boolean mIsDone;
        private T mResult;
        private int mMode;

        public Worker(Job<T> job, FutureListener<T> listener) {
            mJob = job;
            mListener = listener;
        }

        // This is called by a thread in the thread pool.
        public void run() {
            if (mListener != null) mListener.onFutureBegin(this);

            T result = null;

            // A job is in CPU mode by default. setMode returns false
            // if the job is cancelled.
            if (setMode(MODE_CPU)) {
                try {
                    result = mJob.run(this);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    FrameworkLogUtil.d( "Exception in running a job", ex);
                }
            }

            synchronized (this) {
                setMode(MODE_NONE);
                mResult = result;
                mIsDone = true;
                notifyAll();
            }
            if (mListener != null) mListener.onFutureDone(this);
        }

        // Below are the methods for Future.
        public synchronized void cancel() {
            if (mIsCancelled) return;
            mIsCancelled = true;
            if (mWaitOnResource != null) {
                synchronized (mWaitOnResource) {
                    mWaitOnResource.notifyAll();
                }
            }
            if (mCancelListener != null) {
                mCancelListener.onCancel();
            }
        }

        public boolean isCancelled() {
            return mIsCancelled;
        }

        public synchronized boolean isDone() {
            return mIsDone;
        }

        public synchronized T get() {
            while (!mIsDone) {
                try {
                    wait();
                } catch (Exception ex) {
                    FrameworkLogUtil.d( "ignore exception", ex);
                    // ignore.
                }
            }
            return mResult;
        }

        public void waitDone() {
            get();
        }

        // Below are the methods for JobContext (only called from the
        // thread running the job)
        public synchronized void setCancelListener(CancelListener listener) {
            mCancelListener = listener;
            if (mIsCancelled && mCancelListener != null) {
                mCancelListener.onCancel();
            }
        }

        public boolean setMode(int mode) {
            // Release old resource
            ResourceCounter rc = modeToCounter(mMode);
            if (rc != null) releaseResource(rc);
            mMode = MODE_NONE;

            // Acquire new resource
            rc = modeToCounter(mode);
            if (rc != null) {
                if (!acquireResource(rc)) {
                    return false;
                }
                mMode = mode;
            }

            return true;
        }

        private ResourceCounter modeToCounter(int mode) {
            if (mode == MODE_CPU) {
                return mCpuCounter;
            } else if (mode == MODE_NETWORK) {
                return mNetworkCounter;
            } else {
                return null;
            }
        }

        private boolean acquireResource(ResourceCounter counter) {
            while (true) {
                synchronized (this) {
                    if (mIsCancelled) {
                        mWaitOnResource = null;
                        return false;
                    }
                    mWaitOnResource = counter;
                }

                synchronized (counter) {
                    if (counter.value > 0) {
                        counter.value--;
                        break;
                    } else {
                        try {
                            counter.wait();
                        } catch (InterruptedException ex) {
                            // ignore.
                        }
                    }
                }
            }

            synchronized (this) {
                mWaitOnResource = null;
            }

            return true;
        }

        private void releaseResource(ResourceCounter counter) {
            synchronized (counter) {
                counter.value++;
                counter.notifyAll();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public int compareTo(Worker another) {
            Comparable<? super Job> myJob = (Comparable<? super Job>) mJob;
            return myJob.compareTo(another.mJob);
        }
    }
}
