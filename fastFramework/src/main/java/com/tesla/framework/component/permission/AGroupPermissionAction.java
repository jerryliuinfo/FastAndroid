package com.tesla.framework.component.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.support.action.IAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 申请一组权限
 *
 * Created by wangdan on 16/2/26.
 */
public abstract class AGroupPermissionAction extends IAction implements IPermissionsObserver {

    public static final String TAG = "Permission";

    private IPermissionsSubject subject;
    private String[] permissions;
    private int requestCode;

    public AGroupPermissionAction(Activity context, IAction parent, IPermissionsSubject subject, String[] permissions) {
        super(context, parent);

        this.subject = subject;
        this.permissions = permissions;
        requestCode = Arrays.hashCode(permissions);
    }

    @Override
    public boolean interrupt() {
        boolean interrupt = super.interrupt();

        if (requestCode == 0) {
            return interrupt;
        }
        // 低于SDK23
        else if (Build.VERSION.SDK_INT < 23) {
            return interrupt;
        }

        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            // 授予了权限
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), permissions[i])) {
                FastLog.d(TAG, "已经授予了权限, permission = %s", permissions[i]);
            }
            // 没有或者拒绝了权限
            else if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(getContext(), permissions[i])) {
                deniedList.add(permissions[i]);

                FastLog.d(TAG, "%s permission = %s", "PERMISSION_DENIED", permissions[i]);
            }
        }
        if (deniedList.isEmpty()) {

        }
        else {
            permissions = new String[deniedList.size()];
            deniedList.toArray(permissions);

            interrupt = true;
            doInterrupt();
        }

        return interrupt;
    }



    @Override
    public void doInterrupt() {
        // 对没有权限做出处理，默认申请权限
        if (!handlePermissionNone()) {
            FastLog.d(TAG, "handlePermissionNone(false)");

            requestPermission();
        }
        else {
            FastLog.d(TAG, "handlePermissionNone(true)");
        }
    }

    /**
     * 申请权限时，如果权限是已经被拒绝的，做出处理
     *
     * @return true:请求一次权限，调用requestPermission()
     */
    protected boolean handlePermissionNone() {
        return false;
    }

    /**
     * 权限被拒绝了
     *
     * @param alwaysDenied 勾选了不再提醒
     *
     * @return  true:
     */
    protected void onPermissionDenied(String[] permissions, int[] grantResults, boolean[] alwaysDenied) {

    }

    /**
     * 申请权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    protected void requestPermission() {
        if (subject != null) {
            subject.attach(this);
        }


        // XT1562(Motorola) 这里会上报一个这样的错误，暂时搞不懂为什么
        // java.lang.IllegalArgumentException:Wake lock not active: android.os.Binder@3e69cbf from uid 1000
        try {
            getContext().requestPermissions(permissions, requestCode);
        } catch (IllegalArgumentException e) {
            FastLog.printStackTrace(e);
        }
    }

    /**
     * 处理授权结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        FastLog.d(TAG, "onRequestPermissionsResult , requestCode = " + requestCode);

        if (grantResults != null && grantResults.length > 0) {
            for (int i = 0; i < permissions.length; i++) {
                FastLog.d(TAG, "requestCode = %d, permission = %s, grantResult = %d", requestCode, permissions[i], grantResults[i]);
            }
        }

        if (subject != null) {
            subject.detach(this);
        }

        if (permissions.length != AGroupPermissionAction.this.permissions.length ||
                permissions.length != grantResults.length) {
            return;
        }

        if (requestCode == this.requestCode) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(permissions[i])) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        if (i == permissions.length - 1) {
                            run();
                            return;
                        }
                    }
                    else {
                        break;
                    }
                }
            }

            boolean[] alwaysDenied = new boolean[permissions.length];
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getContext(), permissions[i])) {
                    alwaysDenied[i] = false;
                } else {
                    alwaysDenied[i] = true;
                }
            }
            onPermissionDenied(permissions, grantResults, alwaysDenied);
        }
    }

}
