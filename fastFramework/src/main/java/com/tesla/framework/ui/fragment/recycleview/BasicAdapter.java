package com.tesla.framework.ui.fragment.recycleview;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Jerry on 2021/5/2.
 */
public abstract class BasicAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IViewHolder<T,VH>{
    protected List<T> mDatas = new ArrayList<>();

    private IItemClickListener mListener;

    private VH vh;


    public BasicAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public BasicAdapter() {
    }

    public void setDatas(List<T> datas){
        if (datas == null){
            datas = new ArrayList<>();
        }
        this.mDatas = datas;
    }

    public T getData(int position){
        return mDatas.get(position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        vh = holder;
        onBindHolder(holder,position);
        if (mListener != null){
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getData(position),position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setListener(IItemClickListener listener) {
        this.mListener = listener;
    }
}
