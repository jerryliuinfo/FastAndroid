package com.apache.fastandroid.demo.temp;

import android.os.Bundle;
import android.view.View;

import com.apache.fastandroid.R;
import com.tesla.framework.ui.activity.BaseActivity;



/**
 * Created by blueberry on 2016/6/20.
 */
public class ScrollConflictActivity extends BaseActivity implements View.OnClickListener {



    @Override
    public int getLayoutId() {
        return R.layout.temp_srcoll_confict_activity_view;
    }

    @Override
    public void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        findViewById(R.id.btn_out_scroll_hv).setOnClickListener(this);
        findViewById(R.id.btn_out_scroll_vv).setOnClickListener(this);
        findViewById(R.id.btn_innner_scroll_hv).setOnClickListener(this);
        findViewById(R.id.btn_innner_scroll_vv).setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_out_scroll_hv:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                ScrollConflictFragment.newInstance(ScrollConflictFragment.OUT_HV))
                        .commit();
                break;
            case R.id.btn_out_scroll_vv:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                ScrollConflictFragment.newInstance(ScrollConflictFragment.OUT_VV))
                        .commit();
                break;
            case R.id.btn_innner_scroll_hv:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                ScrollConflictFragment.newInstance(ScrollConflictFragment.INNER_HV))
                        .commit();
                break;
            case R.id.btn_innner_scroll_vv:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,
                                ScrollConflictFragment.newInstance(ScrollConflictFragment.INNER_VV))
                        .commit();
                break;
        }
    }
}
