package com.tesla.framework.component.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author: 01370340
 * data: 2019-10-17
 * description:
 */
public class FastBus {
    private Map<Object, List<SubsribeMethod>> mCache = null;
    private FastBus(){
        mCache = new HashMap<>();
    }
    private static class SingletonHolder{
        private SingletonHolder(){

        }
        private final static  FastBus INSTANCE = new FastBus();
    }
    public static FastBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void registe(Object obj){
        List<SubsribeMethod> list = mCache.get(obj);
        if (list == null){
            List<SubsribeMethod> subsribeMethods = findSubsribeMethodList(obj);
            if (subsribeMethods != null && subsribeMethods.size() > 0){
                mCache.put(obj,subsribeMethods);
            }
        }
    }

    public void unregiste(Object obj){
        List<SubsribeMethod> list = mCache.get(obj);
        if (list != null){
            mCache.remove(obj);
            list = null;
        }
    }

    public void post(Object type){
        Set<Object> keySet = mCache.keySet();
        Iterator<Object> iterator = keySet.iterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();
            List<SubsribeMethod> list = mCache.get(obj);
            for (SubsribeMethod subsribeMethod : list) {
                if (subsribeMethod.type.isAssignableFrom(type.getClass())){
                    invoke(subsribeMethod,obj,type);
                }
            }
        }
    }

    private void invoke(SubsribeMethod subsribeMethod, Object obj,Object type){
        Method method = subsribeMethod.method;
        try {
            method.invoke(obj,type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private List<SubsribeMethod> findSubsribeMethodList(Object obj){
        List<SubsribeMethod> list = new ArrayList<>();
        Class clz = obj.getClass();
        while (clz != null){
            String name = clz.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") ||  name.startsWith("android.")){
                break;
            }

            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {


                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null){
                    continue;
                }

                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length != 1){
                    throw new IllegalArgumentException("subscribe method only accept one paramter");
                }
                SubsribeMethod subsribeMethod = new SubsribeMethod(method,subscribe,paramTypes[0]);
                list.add(subsribeMethod);
            }
            clz = clz.getSuperclass();
        }
        return list;
    }

}
