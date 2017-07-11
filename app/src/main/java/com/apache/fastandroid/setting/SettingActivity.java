package com.apache.fastandroid.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.apache.fastandroid.R;

/**
 * Created by jerryliu on 2017/6/24.
 */

public class SettingActivity extends AppCompatActivity {
    public static void launch(Activity from){
        from.startActivity(new Intent(from,SettingActivity.class));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("设置");
        SettingFragment settingFragment = new SettingFragment();
        getFragmentManager().beginTransaction()
                .add(android.R.id.content,settingFragment)
                .commit();
    }

    public static class SettingFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // 加载xml资源文件
            addPreferencesFromResource(R.xml.fragment_setting);
        }
    }

}
