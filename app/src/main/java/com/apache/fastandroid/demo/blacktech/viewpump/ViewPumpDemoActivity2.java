package com.apache.fastandroid.demo.blacktech.viewpump;

import android.os.Bundle;

import com.apache.fastandroid.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.ViewPumpAppCompatDelegate;


public class ViewPumpDemoActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blacktech_view_pump);
    }

    private AppCompatDelegate appCompatDelegate = null;

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        if (appCompatDelegate == null) {
            appCompatDelegate = new ViewPumpAppCompatDelegate(
                    super.getDelegate(),
                    this
            );
        }
        return appCompatDelegate;
    }
}
