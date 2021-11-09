package com.apache.fastandroid.demo.temp.concurry;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/11/8.
 */
public class PlayerNew implements Runnable {
   public static final String TAG = "PlayerNew";
   private static final Object car = new Object();
   private static final Object sword = new Object();

   private String name;

   public PlayerNew(String name) {
      this.name = name;
   }


   @Override
   public void run() {
      play();
   }

   private void play(){
      if ("zhangsan".equals(name)){
         NLog.d(TAG, "zhangsan play");

         synchronized (car){
            NLog.d(TAG, "zhangsan 获得玩具车");
            synchronized (sword){
               NLog.d(TAG, "zhangsan 获得 sword 锁");

            }
         }

      }else  if ("lisi".equals(name)){
         NLog.d(TAG, "lisi play");
         synchronized (sword){
            NLog.d(TAG, "lisi 获得 sword 锁");
            synchronized (car){
               NLog.d(TAG, "lisi 获得 car 锁");

            }
         }
      }
   }
}
