package com.apache.fastandroid;

import android.os.Bundle;

import com.tesla.framework.support.annotation.AnnotationHelper;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by Jerry on 2019/1/31.
 */
public class AnnotationTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnnotationHelper.inject(this);
        setContentView(R.layout.activity_annotation);

    }
}
