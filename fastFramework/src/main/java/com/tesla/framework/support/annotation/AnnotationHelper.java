package com.tesla.framework.support.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

import apache.artemis_compiler.ProxyInfo;

/**
 * Created by Jerry on 2019/1/31.
 */
public class AnnotationHelper {
    /**
     * 用来缓存反射出来的类，节省每次都去反射引起的性能问题
     */
    private static final Map<Class<?>, Constructor<?>> BINDINGS = new LinkedHashMap<>();

    public static void inject(Activity activity) {
        inject(activity, activity.getWindow().getDecorView());
    }

    public static void inject(Activity host, View root) {
        String classFullName = host.getClass().getName() + ProxyInfo.ClassSuffix;
        try {
            Constructor constructor = BINDINGS.get(host.getClass());
            if(constructor==null){
                Class proxy = Class.forName(classFullName);
                constructor = proxy.getDeclaredConstructor(host.getClass(),View.class);
                BINDINGS.put(host.getClass(),constructor);
            }
            constructor.setAccessible(true);
            constructor.newInstance(host,root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

