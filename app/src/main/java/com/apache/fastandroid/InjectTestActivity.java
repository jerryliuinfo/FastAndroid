package com.apache.fastandroid;

import android.os.Bundle;

import com.tesla.framework.support.annotation.AnnotationHelper;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by Jerry on 2019/1/31.
 */
public class InjectTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        AnnotationHelper.inject(this);

    }
}
