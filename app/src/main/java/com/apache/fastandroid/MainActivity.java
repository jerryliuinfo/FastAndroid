package com.apache.fastandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.apache.fastandroid.setting.SettingFragment;
import com.apache.fastandroid.ui.fragment.pic.PicTabsFragment;
import com.apache.fastandroid.video.VideoTabsFragment;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.network.NetworkHelper;
import com.tesla.framework.common.util.view.StatusBarUtil;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;

public class MainActivity extends BaseActivity{
    public static final String TAG = MainActivity.class.getSimpleName();
    public static void launch(Activity from){
        from.startActivity(new Intent(from,MainActivity.class));
    }


    @ViewInject(id = R.id.drawer)
    DrawerLayout mDrawerLayout;


    @ViewInject(id = R.id.navigation_view)
    NavigationView mNavigationView;


    private ActionBarDrawerToggle drawerToggle;

    private int selecteId;

    private static NetworkHelper.NetworkInductor mNetworkInductor = new NetworkHelper.NetworkInductor() {
        @Override
        public void onNetworkChanged(NetworkHelper.NetworkStatus status) {
            NLog.d(TAG, "onNetworkChanged status = %s", status);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();



        onMenuItemClicked(R.id.nav_item_pic, ResUtil.getString(R.string.nav_pic));

        NetworkHelper.getInstance().addNetworkInductor(mNetworkInductor);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkHelper.getInstance().removeNetworkInductor(mNetworkInductor);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selecteId", selecteId);

    }

    private void setupDrawer(Bundle savedInstanceState) {
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                getToolbar(), R.string.draw_open, R.string.draw_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    private void setupNavigationView(){
        View headerView = mNavigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                onMenuItemClicked(item.getItemId(), item.getTitle().toString());
                return false;
            }
        });
    }

    public void onMenuItemClicked(int itemId, String title){
        Fragment fragment = null;
        switch (itemId){
            case R.id.nav_item_pic:
                fragment = PicTabsFragment.newFragment();
                break;
            case R.id.nav_item_video:
                fragment = VideoTabsFragment.newFragment();
                break;
            case R.id.nav_item_music:
                fragment = VideoTabsFragment.newFragment();
                break;
            case R.id.nav_item_setting:
                SettingFragment.launch(this);
                closeDrawer();

                selecteId = itemId;
                return;
        }

        /*
        if (itemId == R.id.nav_item_pic){

        }else if (itemId == R.id.nav_item_video){

        }else if (itemId == R.id.nav_item_music){

        }else if (itemId == R.id.nav_item_setting){
            SettingFragment.launch(this);
            closeDrawer();

            selecteId = itemId;
            return;
        }*/
        if (fragment != null){
            getSupportActionBar().setTitle(title);
            getSupportFragmentManager().beginTransaction().replace(R.id.lay_content,fragment, "MainFragment").commit();
        }


        closeDrawer();

        selecteId = itemId;
    }

    public boolean isDrawerOpened() {
        return mDrawerLayout.isDrawerOpen(Gravity.LEFT) || mDrawerLayout.isDrawerOpen(Gravity.RIGHT);
    }

    public void closeDrawer() {
        if (isDrawerOpened()){
            mDrawerLayout.closeDrawers();
        }

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (drawerToggle != null)
            drawerToggle.syncState();
    }


    @Override
    protected void setStatusBar() {
        int mStatusBarColor = ResUtil.getColor(R.color.colorPrimary);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer), mStatusBarColor, 112);

    }

    //test cherry-pick
}
