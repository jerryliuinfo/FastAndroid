package com.tesla.framework.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.tesla.framework.support.permission.IPermissionsObserver;
import com.tesla.framework.support.permission.IPermissionsSubject;

import java.util.List;

/**
 * 用户注册回调BaseActivity的生命周期及相关的方法，自行添加
 * 1.可以在Activity生命周期方法内做一些业务处理
 * 2.按home键，返回键,toolbar向左返回箭头处理
 * 3.主题配置
 *
 *
 * Created by JerryLiu on 17/04/08.
 */
public class BaseActivityHelper implements IPermissionsSubject,IActivityHelper {


    private BaseActivity mActivity;

    protected void bindActivity(BaseActivity activity) {
        this.mActivity = activity;
    }

    protected BaseActivity getActivity() {
        return mActivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
    }

    public void onPostCreate(Bundle savedInstanceState) {

    }

    public View findViewById(int id) {
        return mActivity.findViewById(id);
    }

    protected void onStart() {

    }

    protected void onRestart() {

    }

    protected void onResume() {

    }

    protected void onPause() {

    }

    protected void onStop() {

    }

    public void onDestroy() {

    }

    public void finish() {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onSaveInstanceState(Bundle outState) {

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

    private List<IPermissionsObserver> observerList;
    @Override
    public void attach(IPermissionsObserver observer) {
        if (observerList != null && !observerList.contains(observer)){
            observerList.add(observer);
        }
    }

    @Override
    public void detach(IPermissionsObserver observer) {
        if (observerList != null && observerList.contains(observer)){
            observerList.remove(observer);
        }
    }

    @Override
    public void notifyActivityResult(int requestCode, String[] permissions, int[] grantResults) {

    }

}
