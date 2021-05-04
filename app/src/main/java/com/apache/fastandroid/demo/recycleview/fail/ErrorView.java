package com.apache.fastandroid.demo.recycleview.fail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.apache.fastandroid.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/5/4.
 */
public class ErrorView extends FrameLayout {
    private View rootView;
    public ErrorView(@NonNull Context context) {
        this(context,null);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        rootView = LayoutInflater.from(context).inflate(R.layout.error_view, this, true);

    }






}
