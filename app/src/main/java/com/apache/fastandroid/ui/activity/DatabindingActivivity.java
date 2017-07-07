package com.apache.fastandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.apache.fastandroid.R;
import com.apache.fastandroid.databinding.ActivityUserBinding;
import com.apache.fastandroid.support.databinding.User;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/7/6.
 */

public class DatabindingActivivity extends BaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, DatabindingActivivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUserBinding binding =  DataBindingUtil.setContentView(this,R.layout.activity_user);

        User user = new User("zhangsan","zhangsan");
        binding.setUser(user);





    }
}
