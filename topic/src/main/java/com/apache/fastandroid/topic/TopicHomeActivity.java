package com.apache.fastandroid.topic;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.apache.artemis_annotation.BindPath;

@BindPath("topic/main")
public class TopicHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_activity_home);

    }

}
