package com.tesla.framework.common.util.file;

import java.util.ArrayList;

/**
 * author: jerry
 * created on: 2020/10/21 5:01 PM
 * description:
 */
public class BytesBufferPool {
    private static final int READ_STEP = 4096;

    public static class BytesBuffer {
        public byte[] data;
        public int offset;
        public int length;

        private BytesBuffer(int capacity) {
            this.data = new byte[capacity];
        }
    }

    private final int mPoolSize;
    private final int mBufferSize;
    private final ArrayList<BytesBuffer> mList;

    public BytesBufferPool(int poolSize, int bufferSize) {
        mList = new ArrayList<BytesBuffer>(poolSize);
        mPoolSize = poolSize;
        mBufferSize = bufferSize;
    }

    public synchronized BytesBuffer get() {
        int n = mList.size();
        return n > 0 ? mList.remove(n - 1) : new BytesBuffer(mBufferSize);
    }

    public synchronized void recycle(BytesBuffer buffer) {
        if (buffer.data.length != mBufferSize) return;
        if (mList.size() < mPoolSize) {
            buffer.offset = 0;
            buffer.length = 0;
            mList.add(buffer);
        }
    }

    public synchronized void clear() {
        mList.clear();
    }
}
