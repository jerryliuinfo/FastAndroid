package com.apache.fastandroid.demo.adapter;

import com.apache.fastandroid.R;
import com.apache.fastandroid.bean.ViewItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2022/1/16.
 */
public class DemoItemAdapter extends BaseQuickAdapter<ViewItemBean, BaseViewHolder> {
    public DemoItemAdapter(@Nullable List<ViewItemBean> data) {
        super(R.layout.layout_cell_bord_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewItemBean item) {
        helper.setText(R.id.txt_title,item.getTitle());
        helper.setText(R.id.txt_description,item.getDescription());
    }

    @Override
    protected int getDefItemViewType(int position) {
        ViewItemBean item = getItem(position);
        return item.getClazz() == null ? R.layout.layout_cell_bord_item_title:R.layout.layout_cell_bord_item;
    }
}
