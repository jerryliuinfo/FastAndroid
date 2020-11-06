package com.apache.fastandroid.knowledge;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;


import com.apache.fastandroid.util.MainLogUtil;

import kotlin.jvm.Throws;

/**
 *
 * activity1-window1-windowmanager1-windowmanagerimpl1
 *                                                  \
 *                                                  windowmanagerImplGlobal
 *                                                  /
 * activity2-window2-windowmanager2-windowmanagerimpl2
 *
 * application-window-windowmanagr-windowmanagrimpl-windowmanagerImplGlobal
 *
 * 所以这里两种情况：
 * 对于activity，getSystemService(Window.Service) 每一个wm都不一样
 * 对于application.getSystemService(Window.Service) 全局一个wm
 *
 * 出现badtoken的本质原因是当windowmanager调用addview方法，add一个view到一个window的时候，这个时候会首先通过ipc操作，
 * 调用windowmanagerservice去检验当前window是否合法，wms由于超时机制，主动移除了window，而client并不知道，那么就会抛badtoken crash
 *
 * 根据上述原因，结合业务场景，有两类：
 * 1。dialog的addview
 * 2。activity的addview
 *
 * 而这些又是如何产生的呢？
 *
 * dialog addview ，通过ipc操作addview，这个时候是有时间开销的，如果隔了一段时间，才执行到，那么window已经不合法了。
 * 比如 dialog通过setcontentview，然后sleep(2000),那么问题毕现
 *
 * activity也一样，所有的主线程操作，通过主线程队列调度，主线程有任何消息阻塞，那么当window已经销毁的时候，
 * 在触发到windowmanager的addview方法，就会导致badtoken
 *
 * 所以日常开发需要注意以下几点：
 * 1。 一个activity结束，记得及时清除主线程队列。避免阻塞，导致的延迟任务出现意料之外的事情，--》removeAllMessages
 *
 *
 * 解决方案：
 * 我们知道windowmanager对于不同的context是不一样的，如果只是hook windowmanager，那么不同业务要单独hook，不能解决所有问题
 *
 * 这里提供一个通用的解决方案：
 *
 * 通过hook windowmanagerGlobal的方式，对其windowpanagerglobal的addview方法做一个代理，仿照android API > 25 的系统处理办法，对其进行处理。
 * 这种就能兼容到所有的处理badtoken异常
 *
 * 注明：并对其成功修复的，做好上报监控，以此评估修复效果。
 *
 * 该方案侵入性比较大，因为替换了系统的windowmanager为我们的proxy类
 * 更好的方案参考字节：https://juejin.im/post/5ec79fdc6fb9a047a226894c
 * 1。 兜底逻辑，oncreate到onresume如果时间过长，判断消息队列里面是否有H.Destory消息
 * 2。 判断windowvisiable
 */

public class FixBadTokenManager {

    public static final String TAG = "FixBadTokenManager";


    public static final String FIX_BAD_TOKEN_TIMES = "fix_bad_token_times";

    /**
     * 加个开关
     */
    private boolean mSwitchOn = true;

    public void setmSwitchOn(boolean targetSwitchOn) {
        this.mSwitchOn = targetSwitchOn;
    }

    private static WindowManager generateWindowManagerProxy(WindowManager windowManager){
        return generateProxyObject(windowManager);
    }

    @Throws(exceptionClasses = NullPointerException.class)
    private static void getAllInterfaces(Class clazz, HashSet<Class<?>> interfaceCollection) {
        Class<?>[] classes = clazz.getInterfaces();
        MainLogUtil.i(TAG, "getAllInterfaces  classes.length = " + classes.length);
        if (classes.length != 0) {
            interfaceCollection.addAll(Arrays.asList(classes));
        }
        if (clazz.getSuperclass() != Object.class) {
            getAllInterfaces(Objects.requireNonNull(clazz.getSuperclass()), interfaceCollection);
        }
    }

    private static WindowManager generateProxyObject(final WindowManager baseObject) {
        HashSet<Class<?>> classes = new HashSet<>();
        getAllInterfaces(baseObject.getClass(), classes);
        Class<?>[] interfaces = new Class[classes.size()];
        classes.toArray(interfaces);
        MainLogUtil.i(TAG, "generateProxyObject");

        Object proxyObject = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                String methodName = method.getName();
               // LogUtil.i(TAG, "generateProxyObject  methodName = " + methodName);
                try {
                    MainLogUtil.i(TAG, String.format("invoke: replace %s method with try catch",methodName));
                    return method.invoke(baseObject, args);
                } catch (Exception e) {
                    MainLogUtil.e(TAG, String.format("proxyObject invoke methodName=%s errorMsg = %s",methodName,e.getMessage()));
                    //加一个上报，评测addview修复的效果
//                    BeaconConst.reportDelay(FIX_BAD_TOKEN_TIMES,null);
                }
                return null;
            }
        });


        return (WindowManager)proxyObject;
    }

    /**
     *
     * @param activity
     * @return 原来老的windowmanager
     */
    public WindowManager hookWindowManagerForApiBelow25(Activity activity){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
            return null;
        }

        if(!mSwitchOn){
            MainLogUtil.i(TAG, "hookWindowManagerForApiBelow25: disallow hookWindowmanager");
            return null;
        }

        long startTime = System.currentTimeMillis();
        WindowManager hookBeforeManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if(hookBeforeManager==null){
            //hookbefore manager is null,don't need hook
            return null;
        }

        try {

            Field field = Activity.class.getDeclaredField("mWindowManager");
            field.setAccessible(true);
            WindowManager proxyWindowManager = generateWindowManagerProxy(hookBeforeManager);

            field.set(activity, proxyWindowManager);

        } catch (Exception e) {
            e.printStackTrace();
            MainLogUtil.i(TAG, "hookForApiBelow25: exception occur in hookWindowManagerForApiBelow25");

        }

        MainLogUtil.i(TAG, "hookWindowManagerForApiBelow25: costTime = "+(System.currentTimeMillis()-startTime));
        return hookBeforeManager;
    }

}
