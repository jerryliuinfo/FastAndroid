package com.apache.fastandroid.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by jerryliu on 2017/4/10.
 */

public class FavoriteFragment extends ABaseFragment {
    public static FavoriteFragment newFragment(String title) {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @ViewInject(idStr = "tv_content")
    TextView tv_content;
    
    
    @Override
    public int inflateContentView() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (getArguments() != null){
            String title = getArguments().getString("title");
            tv_content.setText(title);
        }


    }
}
