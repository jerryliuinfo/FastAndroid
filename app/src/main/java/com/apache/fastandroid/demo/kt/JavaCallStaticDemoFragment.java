package com.apache.fastandroid.demo.kt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.databinding.FragmentCallstaticJavaBinding;
import com.apache.fastandroid.demo.kt.staticusage.Foo;
import com.apache.fastandroid.demo.kt.staticusage.FooKt;
import com.apache.fastandroid.demo.kt.staticusage.ObjectClass;
import com.tesla.framework.ui.fragment.ABaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import timber.log.Timber;

/**
 * Created by Jerry on 2022/6/2.
 */
class JavaCallStaticDemoFragment extends ABaseFragment {

   private FragmentCallstaticJavaBinding mBinding;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      mBinding = FragmentCallstaticJavaBinding.inflate(inflater);


      mBinding.btnCallStaticFunc.setOnClickListener(v -> {
         callStaticFunUsage();
      });
      mBinding.btnCallStaticField.setOnClickListener(v -> {
         callStaticFunUsage();
      });

      return mBinding.getRoot();
   }

   private void callStaticFunUsage(){
      Foo.Companion.f();
      Foo.f2();

      ObjectClass.INSTANCE.f();
      ObjectClass.f2();

      FooKt.topLevelFun1();
      FooKt.topLevelFun2();

   }


   private void callStaticFieldUsage(){

     Foo.Companion.getName();
     Foo.getName2();
     Timber.d( Foo.name3);

     ObjectClass.INSTANCE.getName();
     ObjectClass.getName2();
     Timber.d( ObjectClass.name3);


   }

}
