package com.apache.fastandroid.demo.component.loadsir.sample.callback;

import android.content.Context;
import android.view.View;

import com.apache.fastandroid.R;
import com.kingja.loadsir.callback.Callback;




public class PlaceholderCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.loadsir_layout_placeholder;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
