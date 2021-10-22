package com.apache.fastandroid.demo.temp;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.R;
import com.tesla.framework.ui.fragment.BaseFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created by Jerry on 2021/10/22.
 */
public class IntDefFragment extends BaseFragment {
   @Override
   public int inflateContentView() {
      return R.layout.fragment_common;
   }

   @Override
   public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
      super.layoutInit(inflater, savedInstanceState);

   }

   public static final int GET_ONE               = 0x00000002;

   public static final int GET_TWO               = 0x00000004;

   public static final int GET_THREE             = 0x00000008;


   @IntDef({
           GET_ONE,
           GET_TWO,
           GET_THREE,
   })
   @Retention(RetentionPolicy.SOURCE)
   public @interface TypeInfoFlags {

   }


   public void getTypeBean(@TypeInfoFlags int flags){
      if ( (flags & GET_ONE ) != 0) {

      }else if ((flags & GET_TWO ) != 0){

      }else if ((flags & GET_THREE ) != 0){

      }

   }
}
