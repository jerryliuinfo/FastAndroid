package com.apache.fastandroid.novel.bookshelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.apache.fastandroid.novel.R;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by 01370340 on 2017/11/4.
 */

public class ReadActivity extends BaseActivity {

    public static void start(Activity from) {
        from.startActivity(new Intent(from,ReadActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.novel_activitty_detail);
    }
}
