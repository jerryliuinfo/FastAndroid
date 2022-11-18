package com.apache.fastandroid.demo.kt.func;

/**
 * Created by Jerry on 2022/11/5.
 */
public class JvmOverloadsDemo {

   /**
    * java 调用 kotlin 的默认默认参数方法
    * 由于使用了默认参数后，可以避免重载。但是 java 却无法调用，因为对 java 而言只有一个方法是可见的，
    * 它是所有参数都存在的完整签名参数，如果希望也向 java 调用者暴露多个重载，可以使用 JvmOverloads
    */
   public static void callJavaDefaultParamFunc() {

      JvmOverloads.notAnnotateWithJvmOverloads("ad",10);

      JvmOverloads.annotateWithJvmOverloads("ad");
   }


}
