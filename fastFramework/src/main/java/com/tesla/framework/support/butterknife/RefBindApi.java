package com.tesla.framework.support.butterknife;

import android.app.Activity;
import android.view.View;

import com.tesla.framework.common.util.log.NLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jerry on 2019/1/27.
 */
public class RefBindApi {

    public static void bind(Activity obj){
        bindLayout(obj);
        bindView(obj);
        bindOnClick(obj);

    }


    public static void bindLayout(Activity obj){
        Class<?> clz = obj.getClass();
        for (;clz != Object.class; clz = clz.getSuperclass()){
            if (clz.getName().startsWith("android")){
                break;
            }
            if (clz.isAnnotationPresent(BindLayout.class)){
                BindLayout bindId = clz.getAnnotation(BindLayout.class);
                int id = bindId.value();
                try {
                    Method method = clz.getMethod("setContentView", int.class);
                    method.setAccessible(true);
                    method.invoke(obj, id);
                    NLog.d("id = %s, obj = %s", id,obj);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }



    }
    public static void bindView(Activity obj){
        Class<?> clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BindView.class)){
                BindView bindView = field.getAnnotation(BindView.class);
                int id = bindView.value();
                try {
                    Method method = clz.getMethod("findViewById",int.class);
                    method.setAccessible(true);
                    //得到view
                    //Object view = method.invoke(obj, id);
                    Object view = obj.findViewById(id);
                    field.setAccessible(true);
                    //将view设置给Activiyt中定义的成员变量
                    field.set(obj,view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void bindOnClick(final Activity obj){
        Class  clz = obj.getClass();
        for (; clz != Object.class; clz = clz.getSuperclass()){
            if (clz.getName().startsWith("android")){
                break;
            }
            Method[] methods = clz.getDeclaredMethods();
            for (final Method method : methods) {
                if (method.isAnnotationPresent(BindOnClick.class)){
                    BindOnClick bindOnClick = method.getAnnotation(BindOnClick.class);
                    int [] ids = bindOnClick.value();
                    for (final int id : ids) {
                        final View view = obj.findViewById(id);
                        if (view != null){
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    method.setAccessible(true);
                                    try {
                                        method.invoke(obj,view);
                                        NLog.d("bindOnClick id = %s, view = %s", id,view);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    }
                }
            }
        }

    }
}
