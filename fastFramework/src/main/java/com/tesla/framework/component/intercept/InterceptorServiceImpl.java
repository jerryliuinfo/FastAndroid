package com.tesla.framework.component.intercept;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.SystemClock;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ThreadUtils.SimpleTask;
import com.tesla.framework.support.bean.InterceptorBean;
import com.tesla.framework.support.execption.HandlerException;
import com.tesla.framework.support.thread.CancelableCountDownLatch;
import static com.tesla.framework.common.util.Consts.TAG;

/**
 * Created by Jerry on 2019/2/9.
 */
public class InterceptorServiceImpl implements InterceptorService {
    private static boolean interceptorHasInit;
    private static final Object interceptorInitLock = new Object();
    static List<IInterceptor> interceptors = new ArrayList<>();


    public void init(final Context context) {
        ThreadUtils.executeByIo(new SimpleTask<Void>() {
            @Override
            public Void doInBackground() throws Throwable {
                SystemClock.sleep(200);
                interceptorHasInit = true;
                synchronized (interceptorInitLock) {
                    interceptorInitLock.notifyAll();
                }
                return null;
            }

            @Override
            public void onSuccess(Void result) {

            }
        });
    }

    @Override
    public void doInterceptions(final InterceptorBean interceptorBean, final InterceptorCallback callback){
        //等待init初始化完成
        checkInterceptorsInitStatus();
        if (!interceptorHasInit){
            callback.onInterrupt(new HandlerException("Interceptors initialization takes too much time."));
            return;
        }

        ThreadUtils.executeByIo(new SimpleTask<Void>() {
            @Override
            public Void doInBackground() throws Throwable {
                CancelableCountDownLatch interceptorCounter = new CancelableCountDownLatch(interceptors.size());
                try {
                    _execute(0, interceptorCounter, interceptorBean);
                    interceptorCounter.await(30, TimeUnit.SECONDS);
                    if (interceptorCounter.getCount() > 0) {    // Cancel the navigation this time, if it hasn't return anythings.
                        callback.onInterrupt(new HandlerException("The interceptor processing timed out."));
                    } else if (null != interceptorBean.getTag()) {    // Maybe some exception in the tag.
                        callback.onInterrupt(new HandlerException(interceptorBean.getTag().toString()));
                    } else {
                        callback.onContinue(interceptorBean);
                    }
                } catch (Exception e) {
                    callback.onInterrupt(e);
                }
                return null;
            }

            @Override
            public void onSuccess(Void result) {

            }
        });

    }

    private void _execute(final int index, final CancelableCountDownLatch countDownLatch,final InterceptorBean interceptorBean){
        if (index < interceptors.size()){
            IInterceptor interceptor = interceptors.get(index);
            interceptor.process(new InterceptorCallback() {
                @Override
                public void onContinue(InterceptorBean interceptorBean) {
                    countDownLatch.countDown();
                    _execute(index+1,countDownLatch,interceptorBean);
                }

                @Override
                public void onInterrupt(Throwable exception) {
                    //只要有一个拦截器执行了onInterrupt，就将CountDownLatch计数清0,执行onContinue函数
                    interceptorBean.setTag(null == exception ? new RuntimeException("No message.") : exception.getMessage());    // save the exception message for backup.
                    countDownLatch.cancel();
                }
            });
        }
    }

    private static void checkInterceptorsInitStatus() {
        synchronized (interceptorInitLock) {
            while (!interceptorHasInit) {
                try {
                    interceptorInitLock.wait(10 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(TAG + "Interceptor init cost too much time error! reason = [" + e.getMessage() + "]");
                }
            }
        }
    }

}
