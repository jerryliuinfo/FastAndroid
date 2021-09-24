package com.apache.fastandroid.component.anr;

import com.github.anrwatchdog.ANRWatchDog;
import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/9/24.
 */
public class AnrManager {
   public static final String TAG = "AnrManager";

   private static AnrManager sAnrMananger = new AnrManager();

   private AnrManager() {
   }

   public static AnrManager getInstance(){
      return sAnrMananger;
   }
   private ANRWatchDog anrWatchDog;
   public void start(AnrConfig anrConfig){
      if (anrWatchDog != null){
         NLog.d(ANRWatchDog.TAG, "anrWatchDog.isAliv: %s",anrWatchDog.isAlive());
      }
      if (anrWatchDog != null && anrWatchDog.isAlive()){
         anrWatchDog.interrupt();
         anrWatchDog = null;
      }
      anrWatchDog = new ANRWatchDog(anrConfig.get_timeoutInterval());
      anrWatchDog .setIgnoreDebugger(anrConfig.is_ignoreDebugger())
              //只有线程名字为APP开头 的才会被报 ANR
//                .setReportThreadNamePrefix("APP")
              //只有主线程耗时才会被监控
              .setReportThreadNamePrefix(anrConfig.get_namePrefix())
              .setANRInterceptor(anrConfig.getAnrInterceptor())
               .setANRListener(anrConfig.getAnrListener());
      anrWatchDog.start();
   }

}
