package com.tesla.framework.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/11.
 */

public abstract class ARecycleViewStaggeredGridFragment<T extends Serializable,Ts extends Serializable,Header extends Serializable> extends ARecycleViewFragment<T,Ts,Header> {

    @Override
    protected RecyclerView.LayoutManager configLayoutManager() {

        return new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    }
}
