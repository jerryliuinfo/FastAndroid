package com.apache.fastandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.apache.fastandroid.R;
import com.tesla.framework.common.util.log.NLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Jerry on 2021/12/13.
 */
public class CustomImageView extends AppCompatImageView {
   private Drawable drawable;
   public CustomImageView(@NonNull Context context) {
      super(context);
   }

   public CustomImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
   }

   public CustomImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   protected void onDraw(Canvas canvas) {
      NLog.d("CustomImageView", "CustomImageView onDraw");


      //内部会调用invalidate，导致又会重新 onDraw，会向任务队列内部post一个同步屏障,形成一个无限循环，导致queueIdle永远不会执行
//      setImageDrawable(getContext().getDrawable(R.drawable.batman));

      if (drawable == null){
         drawable = getContext().getDrawable(R.drawable.add_icon);
      }
      setImageDrawable(drawable);

      super.onDraw(canvas);
   }
}
