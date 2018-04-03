package com.tesla.framework.ui.fragment.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tesla.framework.R;
import com.tesla.framework.ui.fragment.APagingFragment;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 1、支持能够使用BaseAdapter的控件
 * 2、支持ViewType，默认是Normal Type
 *
 * @param <T>
 */
public class BasicListAdapter<T extends Serializable> extends BaseAdapter implements IPagingAdapter {

    private APagingFragment holderFragment;
    private IItemViewCreator<T> itemViewCreator;
    private List<T> datas;

    public BasicListAdapter(APagingFragment holderFragment, List<T> datas) {
        if (datas == null)
            datas = new ArrayList<>();
        this.holderFragment = holderFragment;
        this.itemViewCreator = holderFragment.configItemViewCreator();
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        T t = getDatas().get(position);
        if (t instanceof ItemTypeData) {
            return ((ItemTypeData) t).itemType();
        }

        return IPagingAdapter.TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IITemView<T> itemView;

        if (convertView == null) {
            int itemType = getItemViewType(position);

            convertView = itemViewCreator.newContentView(holderFragment.getActivity().getLayoutInflater(), parent, itemType);

            itemView = itemViewCreator.newItemView(convertView, itemType);
            itemView.onBindView(convertView);

            convertView.setTag(R.id.itemview, itemView);
        } else {
            itemView = (IITemView<T>) convertView.getTag(R.id.itemview);
        }

        itemView.reset(datas.size(), position);

        itemView.onBindData(convertView, datas.get(position), position);

        return convertView;
    }

    @Override
    public List<T> getDatas() {
        return datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
