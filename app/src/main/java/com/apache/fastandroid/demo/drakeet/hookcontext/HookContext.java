package com.apache.fastandroid.demo.drakeet.hookcontext;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

import com.tesla.framework.common.util.log.NLog;


/**
 * Created by Jerry on 2021/10/13.
 */
public class HookContext extends ContextWrapper {
   public static final String TAG = "HookContext";
   public HookContext(Context base) {
      super(base);
   }

   private HookResource hookResource;


   @Override
   public Resources getResources() {
      Resources originalResources = super.getResources();

      if (hookResource == null){
         hookResource = new  HookResource(originalResources);
      }
      if (!hookResource.getConfiguration().equals(originalResources.getConfiguration())
              || !hookResource.getDisplayMetrics().equals(originalResources.getDisplayMetrics())){
         NLog.d(TAG, "配置发生了改变");
         hookResource.updateConfiguration(originalResources.getConfiguration(),originalResources.getDisplayMetrics());
      }
      return hookResource;
   }



   private class HookResource extends ResourcesWrapper{

      public HookResource(Resources resources) {
         super(resources);
      }

      @Override
      public String getString(int id) throws NotFoundException {
         if (id == 123){
            return "哈哈，这是Context.getString(123)的返回结果";
         }
         return super.getString(id);
      }
   }
}
