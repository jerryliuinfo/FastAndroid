package com.apache.fastandroid.demo.designmode.wrapper;

import android.content.res.Resources;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/10/13.
 */
public class AContextWrapper extends AContext{
   AContext mBase;

   public AContextWrapper(AContext mBase) {
      this.mBase = mBase;
   }



   public void attachBaseContext(AContext base){
      if (mBase != null){
         throw new IllegalStateException("base context already set");
      }
      this.mBase = base;
   }

   public AContext getContext(){
      return mBase;
   }


   @Override
   public void doSomething1() {
      try {
         mBase.doSomething1();
      }catch (Exception e){
         e.printStackTrace();
      }
   }

   @Nullable
   @Override
   public Resources getResources() {
      return mBase.getResources();
   }
}
