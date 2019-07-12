package com.tesla.framework.route;

import android.content.Context;
import android.text.TextUtils;
import com.tesla.framework.applike.IApplicationLike;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: 01370340
 * data: 2019/6/17
 * description:
 */
public class Route {
   private Route(){}
   private static class SingletonHolder{
       private SingletonHolder(){

       }
       private final static  Route INSTANCE = new Route();
   }
   public static Route getInstance() {
       return SingletonHolder.INSTANCE;
   }

   private static Map<String,IApplicationLike> sComponentsMap = new ConcurrentHashMap<>();

   private static Map<String,Object> sServiceMap = new ConcurrentHashMap<>();


   public void addService(String serviceName, Object serviceImpl){
       if (TextUtils.isEmpty(serviceName) || serviceImpl == null){
           return;
       }
       sServiceMap.put(serviceName,serviceImpl);
   }

   public void removeService(String serviceName){
       if (TextUtils.isEmpty(serviceName)){
           return;
       }
       sComponentsMap.remove(serviceName);
   }

   public synchronized Object getService(String serviceName){
       if (TextUtils.isEmpty(serviceName)){
           return null;
       }
       return sComponentsMap.get(serviceName);
   }


   public static void registerComponent(String clzName, Context context){
       if (TextUtils.isEmpty(clzName)){
           return ;
       }
       if (sComponentsMap.keySet().contains(clzName)){
           return;
       }
       try {
           Class<?> clz = Class.forName(clzName);
           IApplicationLike iApplicationLike = (IApplicationLike) clz.newInstance();
           iApplicationLike.onCreate(context);
           sComponentsMap.put(clzName,iApplicationLike);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    public static void unRegisterComponent(String clzName){
        if (TextUtils.isEmpty(clzName)){
            return ;
        }
        if (sComponentsMap.keySet().contains(clzName)){
            sComponentsMap.get(clzName).onStop();
            sComponentsMap.remove(clzName);
            return;
        }
        try {
            Class<?> clz = Class.forName(clzName);
            IApplicationLike iApplicationLike = (IApplicationLike) clz.newInstance();
            iApplicationLike.onStop();
            sComponentsMap.remove(clzName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
