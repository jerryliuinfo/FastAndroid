package com.apache.fastandroid.demo.drakket.hookcontext;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by Jerry on 2021/10/27.
 */
public class HookEditText extends AppCompatEditText {
   public HookEditText(@NonNull Context context) {
      super(context);
   }

   public HookEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
   }

   public HookEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }
}
