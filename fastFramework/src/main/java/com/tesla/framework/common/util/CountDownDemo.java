package com.tesla.framework.common.util;

import android.os.SystemClock;
import com.tesla.framework.support.thread.DefaultPoolExecutor;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Jerry on 2019/2/8.
 */
public class CountDownDemo {

    public void doWork() throws InterruptedException {
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);
        long startTime = SystemClock.uptimeMillis();
        for (int i = 0; i < count; i++) {
            DefaultPoolExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {

                    }finally {
                        latch.countDown();
                    }
                }
            });
        }
        FrameworkLogUtil.d("doWork costTime = %s",(SystemClock.uptimeMillis() - startTime));
        latch.await();
    }
}
