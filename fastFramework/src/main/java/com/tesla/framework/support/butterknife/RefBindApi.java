package com.tesla.framework.support.butterknife;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import com.tesla.framework.common.util.log.NLog;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jerry on 2019/1/27.
 * 通过反射的方式做findViewById和OnClick事件处理
 */
public class RefBindApi {

    public static void bind(Activity obj,Context context){
        bindLayout(obj);
        bindView(obj,context);
        bindOnClick(obj);
    }


    public static void bindLayout(Activity obj){
        Class<?> clz = obj.getClass();
        for (;clz != Object.class; clz = clz.getSuperclass()){
            if (clz.getName().startsWith("android")){
                break;
            }
            if (clz.isAnnotationPresent(RefBindLayout.class)){
                RefBindLayout bindId = clz.getAnnotation(RefBindLayout.class);
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
    public static void bindView(Activity injectedSource,Context context){
        Class<?> clz = injectedSource.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RefBindView.class)){
                RefBindView bindView = field.getAnnotation(RefBindView.class);
                int id = bindView.value();
                if (id == 0) {
                    String idStr = bindView.idStr();
                    if (!TextUtils.isEmpty(idStr)) {
                        try {
                            Resources resources = context.getPackageManager().getResourcesForApplication(context.getPackageName());
                            id = resources.getIdentifier(idStr, "id", context.getPackageName());
                            if (id == 0)
                                throw new RuntimeException(String.format("%s 的属性%s关联了id=%s，但是这个id是无效的", injectedSource.getClass().getSimpleName(),
                                        field.getName(), idStr));
                        } catch (Exception e) {
                            //									e.printStackTrace();
                        }
                    }
                }
                try {
                    Object view = injectedSource.findViewById(id);
                    field.setAccessible(true);
                    //将view设置给Activiyt中定义的成员变量
                    field.set(injectedSource,view);
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
                if (method.isAnnotationPresent(RefBindOnClick.class)){
                    RefBindOnClick bindOnClick = method.getAnnotation(RefBindOnClick.class);
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
