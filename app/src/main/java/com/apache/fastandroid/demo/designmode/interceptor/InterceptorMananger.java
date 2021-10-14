package com.apache.fastandroid.demo.designmode.interceptor;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/9/24.
 */
public class InterceptorMananger {
   public static final String TAG = "InterceptorMananger";

   private static IInterceptor DEFAULT_INTERCEPTOR = new IInterceptor() {
      @Override
      public long intercept(long duration) {
         return 0;
      }
   };

   private static IListener DEFAULT_LISTENER = new IListener() {
      @Override
      public void onListener() {
         NLog.d(TAG, "onListener");
      }
   };

   private IInterceptor _interceptor = DEFAULT_INTERCEPTOR;

   private IListener _listener = DEFAULT_LISTENER;

   public void setInterceptor(IInterceptor interceptor) {
      if (interceptor == null){
         _interceptor = DEFAULT_INTERCEPTOR;
      }else {
         _interceptor = interceptor;
      }
   }

   public void run(){

      long duration = 2000;
      long ret = _interceptor.intercept(duration);
      if (ret > 0){
         return;
      }

      _listener.onListener();
   }

   public interface IInterceptor {
      /**
       * Called when main thread has froze more time than defined by the timeout.
       *
       * @param duration The minimum time (in ms) the main thread has been frozen (may be more).
       * @return 0 or negative if the ANR should be reported immediately. A positive number of ms to postpone the reporting.
       */
      long intercept(long duration);
   }

   public interface IListener {
      /**
       * Called when an ANR is detected.
       *
       */
      void onListener();
   }
}
