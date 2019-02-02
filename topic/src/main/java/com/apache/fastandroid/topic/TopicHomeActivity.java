package com.apache.fastandroid.topic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apache.fastandroid.artemis.bridge.RouterMap;

/**
 * Created by Jerry on 2019/1/26.
 */
@Route(path = RouterMap.TOPIC.TOPIC_HOMEACTIVITY)
public class TopicHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_home);
    }
}