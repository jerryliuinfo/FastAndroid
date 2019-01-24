package com.tesla.framework.component.permissionnew;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;

import java.util.Random;

/**
 * Created by Jerry on 2019/1/23.
 */
public class EachPermissionFragment extends Fragment {

    private FragmentActivity mActivity;

    private SparseArray<IPermissionListenerWrap.IEachPermissionListener> mEachCallbacks = new SparseArray<>();

    private SparseArray<IPermissionListenerWrap.IPermissionsHelper> mCallbacks = new SparseArray<>();

    private Random mCodeGenerator = new Random();

    public EachPermissionFragment(){

    }

    public static EachPermissionFragment newInstance(){
        return new EachPermissionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mActivity = (FragmentActivity) getActivity();
    }


    public void requestPermission(@NonNull String[] permissions, IPermissionListenerWrap.IPermissionsHelper callback){
        int requestCode = makeRequestCode();
        mCallbacks.put(requestCode,callback);
        requestPermissions(permissions, requestCode);
    }

    public void requestEachPermissions(@NonNull String[] permissions, IPermissionListenerWrap.IEachPermissionListener callback) {
        int requestCode = makeRequestCode();
        mEachCallbacks.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermissionsResult(requestCode,grantResults);
        handleEachPermissionsResult(requestCode,permissions,grantResults);
    }

    /**
     * 一次回调
     * @param requestCode
     * @param grantResults
     */
    private void handlePermissionsResult(int requestCode, @NonNull int[] grantResults){
        IPermissionListenerWrap.IPermissionsHelper callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        if (callback == null){
            return;
        }
        boolean allGranted = false;
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                allGranted = false;
                break;
            }
            allGranted = true;
        }
        callback.onAccepted(allGranted);
    }

    /**
     * 多次回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    private void handleEachPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        IPermissionListenerWrap.IEachPermissionListener callback = mEachCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);
        if (callback == null){
            return;
        }
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                callback.onAccepted(new Permission(permissions[i],true));
            }else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,permissions[i])){
                    callback.onAccepted(new Permission(permissions[i],false,true));
                }else {
                    callback.onAccepted(new Permission(permissions[i],false,false));
                }
            }
        }
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
