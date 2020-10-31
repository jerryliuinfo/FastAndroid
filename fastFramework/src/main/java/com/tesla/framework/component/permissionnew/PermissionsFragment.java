package com.tesla.framework.component.permissionnew;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.util.SparseArray;

import java.util.Random;

/**
 * Created by Jerry on 2019/1/23.
 */
public class PermissionsFragment extends Fragment {



    private SparseArray<IPermissionListenerWrap.IPermissionsHelper> mCallbacks = new SparseArray<>();

    private Random mCodeGenerator = new Random();

    public PermissionsFragment(){

    }

    public static PermissionsFragment newInstance(){
        return new PermissionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void requestPermissions(@NonNull String[] permissions, IPermissionListenerWrap.IPermissionsHelper callback){
        int requestCode = makeRequestCode();
        mCallbacks.put(requestCode,callback);
        requestPermissions(permissions, requestCode);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermissionsResult(requestCode,permissions,grantResults);
    }

    /**
     * @param requestCode
     * @param grantResults
     */
    private void handlePermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults){
        IPermissionListenerWrap.IPermissionsHelper callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        if (callback == null){
            return;
        }
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED && i == permissions.length - 1){
                callback.onPermissionGranted(permissions);
                return;
            }
            //有权限被拒绝
            else {
                break;
            }
        }
        boolean[] alwaysDenied = new boolean[permissions.length];
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
                alwaysDenied[i] = false;
            } else {
                alwaysDenied[i] = true;
            }
        }
        callback.onPermissionDenied(permissions, grantResults, alwaysDenied);



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


}
