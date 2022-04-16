package com.apache.fastandroid.demo.kt.coroutine;

import androidx.annotation.NonNull;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;

/**
 * Created by Jerry on 2022/4/10.
 */
class JavaUtil {
   public static void main(String[] args) {
      ContinuationUtil.INSTANCE.foo(new Continuation<String>() {
         @NonNull
         @Override
         public CoroutineContext getContext() {
            return null;
         }

         @Override
         public void resumeWith(@NonNull Object o) {

         }
      });

   }
}
