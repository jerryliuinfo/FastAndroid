package com.apache.fastandroid.demo.designmode.delegate;

/**
 * Created by Jerry on 2021/9/23.
 */
public class MiddleWareClientBase extends DefaultClientDelegate {

   private MiddleWareClientBase middleWareClientBase;

   public MiddleWareClientBase(DefaultClient delegate) {
      super(delegate);
   }

   public MiddleWareClientBase() {
      super(null);
   }

   /**
    * 不允许子类重写
    * @param delegate
    */
   @Override
   public final void setDelegate(DefaultClient delegate) {
      super.setDelegate(delegate);
   }

   final MiddleWareClientBase enq(MiddleWareClientBase base){
      setDelegate(base);
      this.middleWareClientBase = base;
      return this.middleWareClientBase;
   }

   final MiddleWareClientBase next() {
      return this.middleWareClientBase;
   }
}
