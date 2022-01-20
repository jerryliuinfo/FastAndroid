package com.apache.fastandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.apache.artemis_annotation.AptTest;
import com.apache.artemis_annotation.BindPath;
import com.apache.fastandroid.annotations.CostTime;
import com.apache.fastandroid.bean.UserBean;
import com.apache.fastandroid.databinding.ActivityMainBinding;
import com.apache.fastandroid.demo.DemoListActivity;
import com.apache.fastandroid.demo.hawk.HawkDemoFragment;
import com.apache.fastandroid.home.HomeFragment;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.logger.Logger;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.eventbus.FastBus;
import com.tesla.framework.ui.activity.BaseVmActivity;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;


@AptTest(path = "main")
@BindPath("login/login")
public class MainActivity extends BaseVmActivity<ActivityMainBinding> implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ActionBarDrawerToggle drawerToggle;
    private int selecteId = -1;

    public static void launch(Activity from, UserBean userBean){
        Intent intent = new Intent(from, MainActivity.class);
        intent.putExtra("userBean",userBean);
        from.startActivity(intent);
    }



    @Override
    public ActivityMainBinding bindView() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @CostTime
    @Override
    public void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();
        loadMenuData();
        MenuItem menuItem = mBinding.navigationView.getMenu().getItem(0);
        menuItem.setChecked(true);
        onMenuItemClicked(menuItem.getItemId(),menuItem.getTitle().toString());


        Context context = getApplicationContext();
        Logger.d(String.format("context file dir:%s, cache:%s",context.getFilesDir(),context.getCacheDir()));
        Logger.d(String.format("external file dir:%s, cache:%s",context.getExternalFilesDir(null),context.getExternalCacheDir()));


        DemoListActivity.launch(this);
//        FragmentContainerActivity.launch(this,ApiDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, MMKVFragment.class,null);
        FragmentContainerActivity.launch(this, HawkDemoFragment.class,null);


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String key = intent.getStringExtra("key");
        Parcelable user = intent.getParcelableExtra("user");
        if (user != null){
            UserBean userBean = (UserBean) user;
            NLog.d(TAG, "onNewIntent key: %s",key);

            NLog.d(TAG, "userBean: %s",userBean);
        }
    }



    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    private void loadMenuData(){
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selecteId", selecteId);

    }

    private void setupDrawer(Bundle savedInstanceState) {
        drawerToggle = new ActionBarDrawerToggle(this, mBinding.drawer,
                getToolbar(), R.string.draw_open, R.string.draw_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        mBinding.drawer.addDrawerListener(drawerToggle);
    }

    private void setupNavigationView(){
        mBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FastLog.d(TAG, "onNavigationItemSelected item title = %s", item.getTitle());
                item.setChecked(true);
                onMenuItemClicked(item.getItemId(), item.getTitle().toString());
                return true;
            }
        });

    }


    public void onMenuItemClicked(int itemId, String title){

        Fragment fragment = null;
        switch (itemId){
            case R.id.nav_item_home:
                fragment = HomeFragment.newInstance();
                break;

            case R.id.nav_item_demo:

                startActivity(new Intent(this,DemoListActivity.class));
                return;
        }


        if (fragment != null && selecteId != itemId){
            getSupportActionBar().setTitle(title);
            getSupportFragmentManager().beginTransaction().replace(R.id.lay_content,fragment, "MainFragment").commit();
        }

        closeDrawer();

        selecteId = itemId;
    }


    public boolean isDrawerOpened() {
        return mBinding.drawer.isDrawerOpen(Gravity.LEFT) || mBinding.drawer.isDrawerOpen(Gravity.RIGHT);
    }

    public void closeDrawer() {
        if (isDrawerOpened()){
            mBinding.drawer.closeDrawers();
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (drawerToggle != null){
            drawerToggle.syncState();
        }
    }


    private boolean canFinish = false;
    @Override
    public boolean onBackClick() {
        if (!canFinish) {
            canFinish = true;
            ToastUtils.showShort("再按一次退出");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    canFinish = false;
                }

            }, 1500);
            return true;
        }

        return super.onBackClick();
    }

    private MutableLiveData<Boolean> result = new MutableLiveData<>();

    @Override
    public void onClick(View v) {
        result.setValue(!result.getValue());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FastBus.getInstance().unregiste(this);
    }




}

