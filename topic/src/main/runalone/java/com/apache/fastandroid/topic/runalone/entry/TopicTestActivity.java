package com.apache.fastandroid.topic.runalone.entry;

import android.os.Bundle;
import android.view.View;
import com.apache.fastandroid.artemis.modularization.provider.Protocols;
import com.apache.fastandroid.topic.R;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.widget.ToastUtils;

/**
 * author: 01370340
 * data: 2019/6/13
 * description:
 */
public class TopicTestActivity extends BaseActivity {

    @Override
    public int inflateContentView() {
        return R.layout.topic_activity_test;
    }

    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg =  Protocols.getTopicProtocal().getUserA("haha");
                ToastUtils.showToast(TopicTestActivity.this,msg );
            }
        });
    }
}
