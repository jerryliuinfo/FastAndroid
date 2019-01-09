package com.tesla.framework.component.activityforresult;

import android.app.Fragment;
import android.content.Intent;
import android.util.SparseArray;
import com.tesla.framework.common.util.log.NLog;
import java.util.Random;

/**
 * Created by 01370340 on 2018/12/4.
 */
public class RouterFragment extends Fragment {

    private SparseArray<ActivityLauncher.Callback> mCallbacks = new SparseArray<>();

    private Random mCodeGenerator = new Random();


    public static RouterFragment newInstance(){
        return new RouterFragment();
    }


    public void startActivityForResult(Intent intent, ActivityLauncher.Callback callback ){
        int requestCode = makeRequestCode();
        NLog.d("RouterFragment startActivityForResult requestCode = %s", requestCode);
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
        ActivityLauncher.Callback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        NLog.d("RouterFragment onActivityResult requestCode = %s, resultCode = %s", requestCode,resultCode);
        if (callback != null){
            callback.onActivityResult(requestCode,resultCode, data);
        }
    }
}
