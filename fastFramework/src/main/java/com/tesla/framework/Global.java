/**
 * Name : Base.java <br>
 * Copyright : Copyright (c) Tencent Inc. All rights reserved. <br>
 * Description : Like "System" class in Java <br>
 */
package com.tesla.framework;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * 全局运行时环境<br>
 * <br>
 * 该类静态包装了{@link android.content.ContextWrapper}的全部方法，可以在不存在或者不方便传递
 * {@code Context} 的情况下使用当前的{@code Application}作为{@code Context}<br>
 *
 * <pre>
 * e.g.
 *
 * public boolean updateNetworkInfo()
 * {
 * 	#//获得Android连通性服务实例
 * 	ConnectivityManager manager = (ConnectivityManager) Global.getSystemService(Context.CONNECTIVITY_SERVICE);
 *
 * 	NetworkInfo info = manager.getActiveNetworkInfo();
 * }
 * </pre>
 * <p>
 * ① 若没有自定义{@code Application}的需要，请在AndroidManifest.xml中设定其 android:name
 * 属性为com.tencent.base.BaseApplication<br>
 * <br>
 * ② 若已经使用其他的{@code Application}的子类作为自己的Application，请在使用BASE库之前， 在其
 * {@code Application.onCreate()} 方法中调用 {@code Global.init(Application)} <br>
 * <br>
 * 若没有初始化{@code Global}，使用本类的静态方法会得到{@link } 的运行时异常，请检查
 * {@Application}的初始化代码或AndroidManifest.xml中的声明
 *
 * @author lewistian
 *
 */
public final class Global {
    private static Context context;
    private static boolean isDebug = false;

    public final static void init(Context ctx) {
        setContext(ctx);
    }

    public final static Context getContext() {
        if (context == null) {
            throw new IllegalArgumentException("Global's Context is NULL, have your Application in manifest "
                    + "subclasses BaseApplication or Call 'Global.init(this)' in your own Application ? ");
        }

        return context;
    }

    public final static void setContext(Context mContext) {
        context = mContext;

        try {
            ApplicationInfo info = context.getApplicationInfo();

            isDebug = ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);

            if (isDebug) {
                android.util.Log.w("Wns.Global.Runtime", "DEBUG is ON");
            }
        } catch (Exception e) {
            isDebug = false;
        }
    }




}
