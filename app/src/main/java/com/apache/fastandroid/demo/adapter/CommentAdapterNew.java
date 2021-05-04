package com.apache.fastandroid.demo.adapter;

import com.apache.fastandroid.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/5/3.
 */
public class CommentAdapterNew extends BaseQuickAdapter<String,CommentViewHolderNew> {


    public CommentAdapterNew(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(CommentViewHolderNew helper, String item) {
        helper.setText(R.id.tv_title,item);
    }
}
