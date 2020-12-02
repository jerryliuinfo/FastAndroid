package com.apache.fastandroid.demo.other;

import android.view.ViewGroup;

import com.apache.fastandroid.R;
import com.apache.fastandroid.artemis.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * Created by Jerry on 2020/12/2.
 * 参考:https://blog.csdn.net/u013394527/article/details/80066654
 * android:clipChildren的意思：是否限制子View在其范围内(默认为true)，设置为false则代表子View可以超出父View的控件
 * 这个属性只有设置在子View的 「父View的父View」上面才有效
 */
public class ClipChildFragment extends BaseFragment {

    @Override
    public int inflateContentView() {
        return R.layout.custom_view_clipchild;
    }

    private class MyAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
