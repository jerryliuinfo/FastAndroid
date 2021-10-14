package com.apache.fastandroid.demo.designmode.wrapper;

/**
 * Created by Jerry on 2021/10/13.
 */
class AContextWrapper extends AContext{
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


   @Override
   public void doSomething1() {
      try {
         mBase.doSomething1();
      }catch (Exception e){
         e.printStackTrace();
      }
   }

}
