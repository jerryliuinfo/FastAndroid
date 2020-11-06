package com.tesla.framework.common.util.task;

import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author: jerry
 * created on: 2020/10/22 12:02 PM
 * description:
 */
public class PriorityThreadPool extends ThreadPool {

    public PriorityThreadPool() {
        this("priority-thread-pool", DEFAULT_POOL_SIZE);
    }

    public PriorityThreadPool(Executor executor) {
        super(executor);
    }

    public PriorityThreadPool(String name, int coreSize, int poolSize) {
        super(name, coreSize, poolSize, new PriorityBlockingQueue<Runnable>());
    }

    public PriorityThreadPool(String name, int poolSize) {
        super(name, poolSize, poolSize, new PriorityBlockingQueue<Runnable>());
    }

    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener, Priority priority) {
        if (priority == null) {
            priority = Priority.NORMAL;
        }
        PriorityJob<T> priorityJob = new PriorityJob<T>(job, priority.priority, priority.fifo);
        return super.submit(priorityJob, listener);
    }

    public <T> Future<T> submit(Job<T> job, Priority priority) {
        return submit(job, null, priority);
    }

    @Override
    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener) {
        return submit(job, listener, null);
    }

    @Override
    public <T> Future<T> submit(Job<T> job) {
        return submit(job, null, null);
    }

    /**
     * Priority definition for . {@link #priority} determines the basic level,
     * {@link #fifo} determines whether fifo or filo is used within same {@link #priority}.
     */
    public static class Priority {

        public final static Priority LOW = new Priority(-1, true);

        public final static Priority NORMAL = new Priority(0, true);

        public final static Priority HIGH = new Priority(1, false);

        public final int priority;
        public final boolean fifo;

        public Priority(int priority, boolean fifo) {
            this.priority = priority;
            this.fifo = fifo;
        }
    }

    private static class PriorityJob<T> implements Job<T>, Comparable<PriorityJob> {

        private static final AtomicLong SEQ = new AtomicLong(0);

        private final Job<T> mJob;

        /**
         * the bigger, the prior.
         */
        private final int mPriority;

        /**
         * whether fifo(with same {@link #mPriority}).
         */
        private final boolean mFifo;

        /**
         * seq number.
         */
        private final long mSeqNum;

        public PriorityJob(Job<T> job, int priority, boolean fifo) {
            mJob = job;
            mPriority = priority;
            mFifo = fifo;
            mSeqNum = SEQ.getAndIncrement();
        }

        @Override
        public T run(JobContext jc) {
            return mJob.run(jc);
        }

        @Override
        public int compareTo(PriorityJob another) {
            return mPriority > another.mPriority ? -1 : (mPriority < another.mPriority ? 1 : subCompareTo(another));
        }

        private int subCompareTo(PriorityJob another) {
            int result = mSeqNum < another.mSeqNum ? -1 : (mSeqNum > another.mSeqNum ? 1 : 0);
            return mFifo ? result : -result;
        }
    }

    // ------------- default --------------
    private static class InstanceHolder {
        public static final PriorityThreadPool INSTANCE = new PriorityThreadPool();
    }

    public static PriorityThreadPool getDefault() {
        return InstanceHolder.INSTANCE;
    }
}
