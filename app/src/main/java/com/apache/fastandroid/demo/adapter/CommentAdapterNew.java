package com.apache.fastandroid.demo.adapter;

import com.apache.fastandroid.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/5/3.
 */
public class CommentAdapterNew extends BaseQuickAdapter<String, BaseViewHolder> {
    public CommentAdapterNew(@Nullable List<String> data) {
        super(R.layout.item_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,item);

    }
}
