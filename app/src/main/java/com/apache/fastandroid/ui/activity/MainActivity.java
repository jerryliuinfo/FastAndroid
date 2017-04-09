package com.apache.fastandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.apache.fastandroid.R;
import com.apache.fastandroid.ui.fragment.TestTabFragment;
import com.tesla.framework.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainFragment.newFragment();
        Fragment fragment = TestTabFragment.newFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_content, fragment)
                .commit();
        getSupportActionBar().setTitle("news");

    }
}
