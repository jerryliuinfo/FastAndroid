package com.apache.fastandroid.demo.temp.concurry;

import com.tesla.framework.component.logger.Logger;

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
         Logger.d(  "zhangsan play");

         synchronized (car){
            Logger.d( "zhangsan 获得玩具车");
            synchronized (sword){
               Logger.d(  "zhangsan 获得 sword 锁");

            }
         }

      }else  if ("lisi".equals(name)){
         synchronized (sword){
            Logger.d("lisi 获得 sword 锁");
            synchronized (car){
               Logger.d("lisi 获得 car 锁");

            }
         }
      }
   }
}
