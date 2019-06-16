package com.apache.fastandroid.artemis.util;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by kkmike999 on 2016/05/26.
 * <p>
 * 需要在{@linkplain Application#onCreate()}时diaoyong{@linkplain #init(Context)}初始化
 */
public class AppContextProvider {

	private static Context context;

	public static final Context getContext() {
		if (context == null) {
			try {
				Method method = Class.forName("android.app.ActivityThread")
                                     .getMethod("currentApplication");
				method.setAccessible(true);

				Application application = (Application) method.invoke(null, (Object[]) null);

				if (application != null) {
					AppContextProvider.context = application;

					return application;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Method method = Class.forName("android.app.AppGlobals")
                                     .getMethod("getInitialApplication");
				method.setAccessible(true);

				Application application = (Application) method.invoke(null, (Object[]) null);

				if (application != null) {
					AppContextProvider.context = application;

					return application;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return context;
	}

	public static void init(Context cx) {
		context = cx.getApplicationContext();
	}
}
