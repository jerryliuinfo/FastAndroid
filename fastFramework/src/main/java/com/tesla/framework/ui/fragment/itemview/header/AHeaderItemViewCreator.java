package com.tesla.framework.ui.fragment.itemview.header;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/4/3.
 */

public abstract class AHeaderItemViewCreator<T extends Serializable> implements IItemViewCreator<T> {

    @Override
    public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        for (int [] headerLayoutRes: setHeaders()){
            if (viewType == headerLayoutRes[1]){
                return inflater.inflate(headerLayoutRes[0], parent,false);
            }
        }
        return null;
    }




    /**
     * 0:layoutRes  1:viewType
     * @return
     */
    public abstract int[][] setHeaders();


}
