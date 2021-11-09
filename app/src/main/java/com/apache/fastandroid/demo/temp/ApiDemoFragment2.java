package com.apache.fastandroid.demo.temp;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.MainActivity;
import com.apache.fastandroid.R;
import com.apache.fastandroid.demo.temp.bean.TclUserBean;
import com.blankj.utilcode.util.SPUtils;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.support.ICommonCallback;
import com.tesla.framework.support.KidsException;
import com.tesla.framework.ui.fragment.BaseFragment;

/**
 * Created by Jerry on 2021/11/3.
 */
public class ApiDemoFragment2 extends BaseFragment {
    @Override
    public int inflateContentView() {
        return R.layout.temp_api_usage_demo;
    }

    private SPUtils spUtils = SPUtils.getInstance();

    private static final String KEY_AUTHORIZED = "authorized";
    private static final String KEY_ACCOUNT_ID = "accountId";
    private static final String KEY_PHONE = "phone";

    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);


        findViewById(R.id.btn_content_provider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                queryTclUserInfo(new ICommonCallback<TclUserBean>() {
                    @Override
                    public void onSucceed(TclUserBean tclUserBean) {
                        NLog.d(TAG, "queryTclUserInfo onSucceed: %s",tclUserBean);
                    }

                    @Override
                    public void onFailed(KidsException exception) {
                        NLog.d(TAG, "queryTclUserInfo onFailed: %s",exception);
                    }
                });
            }
        });
    }


    private void queryTclUserInfo(ICommonCallback<TclUserBean> callback){
        String phone = SPUtils.getInstance().getString(KEY_PHONE);
        String accountId = SPUtils.getInstance().getString(KEY_ACCOUNT_ID);
        if (!SPUtils.getInstance().getBoolean(KEY_AUTHORIZED,false)
                || TextUtils.isEmpty(phone) || TextUtils.isEmpty(accountId)){
            //Todo showDialog
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("是否同意获取tcl账号信息")
                    .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            queryUserInfo(callback);
                        }
                    }).setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else {

            callback.onSucceed(new TclUserBean(accountId,phone));
        }

    }

    private String[] COLUM_NAME = new String[]{"accountId", "phone","email"};

    private void queryUserInfo(ICommonCallback<TclUserBean> callback) {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri_user = Uri.parse("content://com.tcl.account/userInfo");
        Cursor cursor = contentResolver.query(uri_user, COLUM_NAME, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String accountId = cursor.getString(cursor.getColumnIndex(COLUM_NAME[0]));
                String phone = cursor.getString(cursor.getColumnIndex(COLUM_NAME[1]));
                NLog.d(MainActivity.TAG, "accountId: %s, phone:%s", accountId, phone);
                spUtils.put(KEY_AUTHORIZED,true);
                if (!TextUtils.isEmpty(accountId)){
                    spUtils.put(KEY_ACCOUNT_ID, accountId);
                }
                if (!TextUtils.isEmpty(phone)){
                    spUtils.put(KEY_PHONE, phone);
                }
                if (callback != null) {
                    callback.onSucceed(new TclUserBean(accountId, phone));
                }
            }
        } else {
            callback.onFailed(KidsException.newException("failed to laod tcl account"));
        }
    }
}
