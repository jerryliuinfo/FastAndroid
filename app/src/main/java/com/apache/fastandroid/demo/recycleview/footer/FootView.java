package com.apache.fastandroid.demo.recycleview.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.apache.fastandroid.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/5/4.
 */
public class FootView extends FrameLayout {
    private View rootView;
    private ImageView iv;
    private int type;
    public FootView(@NonNull Context context, int type) {
        this(context,null,type);
        this.type = type;
    }

    public FootView(@NonNull Context context, @Nullable AttributeSet attrs, int type) {
        this(context, attrs,0,type);
    }

    public FootView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int type) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr,type);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr,int type){
        rootView = LayoutInflater.from(context).inflate(R.layout.footer_view, this, true);
        iv = rootView.findViewById(R.id.iv);
        if (type == 1){
            iv.setImageResource(R.drawable.rm_icon);
        }
    }


    public void setListener(OnClickListener mListener) {
        iv.setOnClickListener(mListener);

    }




}
