package com.tesla.framework.component.permissionnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by Jerry on 2019/1/23.
 *
 */
public class PermissionsHelper {
    private static final String TAG = PermissionsFragment.class.getName();

    private FragmentActivity mActivity;

    public PermissionsHelper(FragmentActivity activity){
        this.mActivity = activity;
    }

    public static PermissionsHelper newInstance(FragmentActivity activity){
        return new PermissionsHelper(activity);
    }

    public void requestPermisiion(String[] permissions, IPermissionListenerWrap.IPermissionsHelper callback){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            callback.onPermissionGranted(permissions);
            return;
        }
        getRouterFragment(mActivity).requestPermissions(permissions,callback);
    }


    private PermissionsFragment getRouterFragment(Activity activity){
        FragmentManager fragmentManager = activity.getFragmentManager();
        PermissionsFragment fragment = (PermissionsFragment) findFragmentByTag(activity);
        if (fragment == null){
            fragment = PermissionsFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment,TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public Fragment findFragmentByTag(Activity activity){
        return activity.getFragmentManager().findFragmentByTag(TAG);
    }


    /**
     * 用户勾选不再显示并点击拒绝，弹出打开设置页面申请权限，也可以自定义实现
     *
     * @param context Context
     * @param title   弹窗标题
     * @param message 申请权限解释说明
     * @param confirm 确认按钮的文字，默认OK
     * @param cancel  取消按钮呢的文字，默认不显示取消按钮
     */

    public static void requestDialogAgain(final Activity context, @NonNull String title, @NonNull String message, String confirm, String cancel) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message);
            builder.setPositiveButton(confirm == null ? "OK" : confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startSettingActivity(context);
                    dialog.dismiss();
                }
            });
            if (null != cancel) {
                builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开设置页面打开权限
     *
     * @param context
     */
    public static void startSettingActivity(@NonNull Activity context) {

        try {
            Intent intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +
                            context.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            context.startActivityForResult(intent, 10); //这里的requestCode和onActivityResult中requestCode要一致
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
