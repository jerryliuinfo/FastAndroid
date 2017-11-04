package com.tesla.framework.ui.fragment.itemview.footer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/3.
 */

public abstract class AFooterItemViewCreator<T extends Serializable> implements IItemViewCreator<T> {

    @Override
    public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        for (int [] footerLayoutRes: setFooters()){
            if (viewType == footerLayoutRes[1]){
                return inflater.inflate(footerLayoutRes[0], parent,false);
            }
        }
        return null;
    }




    /**
     * 0:layoutRes  1:viewType
     * @return
     */
    public abstract int[][] setFooters();


}
