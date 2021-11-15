package com.tesla.framework.ui.widget.edgeeffect;

import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Jerry on 2021/11/15.
 */
public class StretchEdgeEffectFactoryNew extends RecyclerView.EdgeEffectFactory {
   @NonNull
   @Override
   protected EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
      return new GlowingEdgeEffect(view.getContext());
   }
}
