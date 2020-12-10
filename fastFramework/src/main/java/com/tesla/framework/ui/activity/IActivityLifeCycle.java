package com.tesla.framework.ui.activity;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 01370340 on 2017/11/28.
 */

public interface IActivityLifeCycle {

    void onCreate(Bundle savedInstanceState);



     void onStart();

     void onRestart();

     void onResume();

     void onPause();

     void onStop();

     void onDestroy();

     void finish();

    void onActivityResult(int requestCode, int resultCode, Intent data);



    void onSaveInstanceState(Bundle outState);


}
