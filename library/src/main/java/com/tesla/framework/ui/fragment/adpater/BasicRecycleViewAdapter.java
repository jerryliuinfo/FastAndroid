package com.tesla.framework.ui.fragment.adpater;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.tesla.framework.R;
import com.tesla.framework.ui.fragment.APagingFragment;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;
import com.tesla.framework.ui.fragment.itemview.header.AHeaderItemViewCreator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jerryliu on 2017/4/3.
 */

public class BasicRecycleViewAdapter<T extends Serializable> extends RecyclerView.Adapter implements IPagingAdapter<T>{
    private ArrayList<T> datas;
    private IITemView<T> mFooterView;

    private AHeaderItemViewCreator<T> mHeaderItemViewCreator;
    private IItemViewCreator<T> mItemViewCreator;

    /**
     * //0:layoutRes   1:itemType:
     * 例如
     * new int[][]{ { R.layout.ui_search_headerview, 100 },R.layout.ui_search_headerview2, 101 } }
     */
    private int[][] mHeaderItemTypes;

    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;


    private APagingFragment ownerFragment;
    private Activity mActivity;

    public BasicRecycleViewAdapter(Activity activity,ArrayList<T> datas, IItemViewCreator<T> itemViewCreator,APagingFragment ownerFragment) {

        this.mActivity = activity;
        if (datas == null){
            datas = new ArrayList<>();
        }
        this.datas = datas;
        this.mItemViewCreator = itemViewCreator;
        this.ownerFragment = ownerFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView;
        IITemView<T> itemView;
        if (isFooter(viewType)){
            itemView = mFooterView;
            convertView = mFooterView.getConvertView();

        }else if (isHeader(viewType)){
            convertView = mHeaderItemViewCreator.newContentView(mActivity.getLayoutInflater(),parent,viewType);
            itemView = mHeaderItemViewCreator.newItemView(convertView, viewType);
            convertView.setTag(R.id.itemview, itemView);
        }else {
            convertView = mItemViewCreator.newContentView(mActivity.getLayoutInflater(),parent,viewType);
            itemView = mItemViewCreator.newItemView(convertView, viewType);
            convertView.setTag(R.id.itemview, itemView);
        }

        itemView.onBindView(convertView);
        if (!(itemView instanceof ARecycleViewItemViewHolder)){
            throw new RuntimeException("RecycleView只支持ARecycleViewItemView，请重新配置");
        }
        return (RecyclerView.ViewHolder) itemView;
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ARecycleViewItemViewHolder<T> itemViewHolder = (ARecycleViewItemViewHolder<T>) holder;
        //header和footer的ui刷新自己维护 不在adpater中维护
        if (position >= getHeaderCount()){
            int realPosition = position - getHeaderCount();
            itemViewHolder.reset(datas.size(), realPosition);
            //footer不会再这里刷新
            if (realPosition < datas.size()){
                itemViewHolder.onBindData(itemViewHolder.getConvertView(), getDatas().get(realPosition), realPosition);
            }

            // 不设置item点击监听事件，由各个item自行处理
           /* if (onItemClickListener != null){
                itemViewHolder.getConvertView().setOnClickListener(innerOnClickListener);
            }else {
                itemViewHolder.getConvertView().setOnClickListener(null);
            }

            if (onItemLongClickListener != null){
                itemViewHolder.getConvertView().setOnLongClickListener(innerLongClickClickListener);
            }else {
                itemViewHolder.getConvertView().setOnLongClickListener(null);
            }*/

        }
    }

    private View.OnClickListener innerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IITemView<T> itemView = (IITemView<T>) v.getTag(R.id.itemview);
            if (itemView != null && onItemClickListener != null){
                onItemClickListener.onItemClick(null,itemView.getConvertView(),itemView.itemPosition(), getItemId(itemView.itemPosition()));
            }
        }
    };

    private View.OnLongClickListener innerLongClickClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            IITemView<T> itemView = (IITemView<T>) v.getTag(R.id.itemview);
            if (itemView != null && innerLongClickClickListener != null){
                onItemLongClickListener.onItemLongClick(null,itemView.getConvertView(),itemView.itemPosition(),getItemId(itemView.itemPosition()));
            }
            return false;
        }
    };



    @Override
    public int getItemViewType(int position) {
        //footer
        if (mFooterView != null && position == getItemCount() - 1){
            return IPagingAdapter.TYPE_FOOTER;
        }
        //header
        else if (mHeaderItemViewCreator != null && position < mHeaderItemTypes.length){
            return mHeaderItemTypes[position][1];
        }

        // 自定义type
        if (position >= getHeaderCount()){
            T t = getDatas().get(position - getHeaderCount() );
            if (t instanceof IPagingAdapter.ItemTypeData){
                ItemTypeData itemTypeData = (ItemTypeData) t;
                return itemTypeData.itemType();
            }
        }
        return IPagingAdapter.TYPE_NORMAL;
    }

    private boolean isFooter(int viewType){
        return viewType == IPagingAdapter.TYPE_FOOTER;
    }


    private boolean isHeader(int viewType){
        if (mHeaderItemTypes != null){
            for (int[] mHeaderItemType : mHeaderItemTypes) {
                if (viewType == mHeaderItemType[1]){
                    return true;
                }
            }
        }
        return false;
    }


    public void addFooterView(IITemView<T> footerItemView){
        mFooterView = footerItemView;
        if (footerItemView.getConvertView().getLayoutParams() == null){
            footerItemView.getConvertView().setLayoutParams(
                    new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public void addHeaderViewCreator(AHeaderItemViewCreator<T> headerItemViewCreator){
        this.mHeaderItemViewCreator = headerItemViewCreator;
        this.mHeaderItemTypes = headerItemViewCreator.setHeaders();
    }




    @Override
    public int getItemCount() {
        int headerCount = getHeaderCount();
        int footerCount = mFooterView != null? 1:0;
        return datas.size() + headerCount + footerCount;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
    }

    private int getHeaderCount(){
      return mHeaderItemTypes != null? mHeaderItemTypes.length:0;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
