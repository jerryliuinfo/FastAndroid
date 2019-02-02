package com.tesla.framework.support.annotation;

import android.app.Activity;

import apache.artemis_compiler.ProxyInfo;

/**
 * Created by Jerry on 2019/1/31.
 */
public class AnnotationHelper {
    public static void inject(Activity o) {
        inject(o, o);
    }

    public static void inject(Activity host, Object root) {
        String classFullName = host.getClass().getName() + ProxyInfo.ClassSuffix;
        try {
            Class proxy = Class.forName(classFullName);
            TA injector = (TA) proxy.newInstance();
            injector.inject(host, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
