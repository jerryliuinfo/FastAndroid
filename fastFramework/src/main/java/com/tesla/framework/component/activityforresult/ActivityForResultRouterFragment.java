package com.tesla.framework.component.activityforresult;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import com.tesla.framework.common.util.log.NLog;
import java.util.Random;

/**
 * Created by 01370340 on 2018/12/4.
 */
public class ActivityForResultRouterFragment extends Fragment {

    private SparseArray<ActivityResultHelper.ActivityForResultCallback> mCallbacks = new SparseArray<>();

    private Random mCodeGenerator = new Random();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static ActivityForResultRouterFragment newInstance(){
        return new ActivityForResultRouterFragment();
    }


    public void startActivityForResult(Intent intent, ActivityResultHelper.ActivityForResultCallback callback ){
        int requestCode = makeRequestCode();
        NLog.d("ActivityForResultRouterFragment startActivityForResult requestCode = %s", requestCode);
        mCallbacks.put(requestCode,callback);
        startActivityForResult(intent,requestCode);
    }

    private int makeRequestCode(){
        int requestCode;
        int tryCount = 0;
        do {
            tryCount++;
            requestCode = mCodeGenerator.nextInt(0x0000FFFF);
        }while (mCallbacks.indexOfKey(requestCode) >= 0 && tryCount < 10);
        return requestCode;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultHelper.ActivityForResultCallback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        NLog.d("ActivityForResultRouterFragment onActivityResult requestCode = %s, resultCode = %s", requestCode,resultCode);
        if (callback != null){
            callback.onActivityResult(requestCode,resultCode, data);
        }
    }
}
