package com.apache.fastandroid.demo.temp.specifyparent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Created by Jerry on 2021/10/26.
 */
public class DraggableContainer extends FrameLayout {

   private DrawerLayout drawerLayout;
   public DraggableContainer(@NonNull Context context) {
      this(context,null);
   }

   public DraggableContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
      this(context, attrs,0);
   }

   public DraggableContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      ensureDrawerLayout();
   }

   private void ensureDrawerLayout(){
      ViewParent parent = getParent();
      if (!(parent instanceof DrawerLayout)){
         throw new IllegalStateException("This " + this + " must be added to a DrawerLayout");
      }
      drawerLayout = (DrawerLayout) parent;
   }
}
