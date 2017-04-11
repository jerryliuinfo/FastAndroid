package com.tesla.framework.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/11.
 */

public abstract class ARecycleViewGridFragment<T extends Serializable,Ts extends Serializable,Header extends Serializable> extends ARecycleViewFragment<T,Ts,Header> {

    @Override
    protected RecyclerView.LayoutManager configLayoutManager() {

        return new GridLayoutManager(getContext(),2);
    }
}
