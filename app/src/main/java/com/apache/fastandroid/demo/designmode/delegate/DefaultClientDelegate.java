package com.apache.fastandroid.demo.designmode.delegate;

/**
 * Created by Jerry on 2021/9/23.
 */
public class DefaultClientDelegate extends DefaultClient {
   private DefaultClient delegate;

   public DefaultClientDelegate(DefaultClient delegate) {
      this.delegate = delegate;
   }

   public void setDelegate(DefaultClient delegate) {
      this.delegate = delegate;
   }

   @Override
   public void doSomething() {
      if (delegate != null){
         delegate.doSomething();
         return;
      }
      super.doSomething();
   }

   @Override
   public void doSomething2() {
      if (delegate != null){
         delegate.doSomething2();
         return;
      }
      super.doSomething2();
   }
}
