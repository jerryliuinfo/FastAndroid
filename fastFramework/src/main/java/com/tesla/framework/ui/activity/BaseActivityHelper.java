package com.tesla.framework.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.tesla.framework.common.util.activitytask.ActivityTaskMgr;
import com.tesla.framework.component.permission.IPermissionsObserver;
import com.tesla.framework.component.permission.IPermissionsSubject;
import com.tesla.framework.component.permission.PermissionActivityHelper;

/**
 * 用户注册回调BaseActivity的生命周期及相关的方法，自行添加
 * 1.可以在Activity生命周期方法内做一些业务处理
 * 2.按home键，返回键,toolbar向左返回箭头处理
 * 3.主题配置
 *
 *
 * Created by JerryLiu on 17/04/08.
 */
public class BaseActivityHelper implements IPermissionsSubject,IActivityLifeCycle {
    //当前Activity
    private BaseActivity mActivity;

    private PermissionActivityHelper mPermissionActivityHelper = new PermissionActivityHelper();

    protected void bindActivity(BaseActivity activity) {
        this.mActivity = activity;
    }

    protected BaseActivity getActivity() {
        return mActivity;
    }

    public View findViewById(int id) {
        return mActivity.findViewById(id);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        ActivityTaskMgr.getInstance().pushToActivityStack(mActivity);
        if (mPermissionActivityHelper != null){
            mPermissionActivityHelper.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        ActivityTaskMgr.getInstance().popFromActivityStack(mActivity);
    }

    public void finish() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }


    // 这三个方法暂不支持
//    public void setContentView(int layoutResID) {
//
//    }
//
//    public void setContentView(View view) {
//
//    }
//
//    public void setContentView(View view, ViewGroup.LayoutParams params) {
//
//    }

    protected boolean onHomeClick() {
        return false;
    }

    public boolean onBackClick() {
        return false;
    }

    protected int configTheme() {
        return 0;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }



    @Override
    public void attach(IPermissionsObserver observer) {
        mPermissionActivityHelper.attach(observer);
    }

    @Override
    public void detach(IPermissionsObserver observer) {
        mPermissionActivityHelper.detach(observer);
    }

    @Override
    public void notifyActivityResult(int requestCode, String[] permissions, int[] grantResults) {
        mPermissionActivityHelper.notifyActivityResult(requestCode,permissions,grantResults);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        notifyActivityResult(requestCode,permissions,grantResults);
    }

}
