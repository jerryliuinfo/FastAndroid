package com.tesla.framework.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by jiong 2020/2/18
 */

public interface IView {
    int getLayoutId();
    void bindUI(View rootView);
    void layoutInit(LayoutInflater inflater, Bundle savedInstanceState);
}
