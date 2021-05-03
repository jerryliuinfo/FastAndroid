package com.apache.fastandroid.demo.widget.banner.vewholder;

import android.view.View;
import android.widget.ImageView;

import com.apache.fastandroid.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
//        this.imageView = (ImageView) view;
        this.imageView = view.findViewById(R.id.icon);
    }
}