package com.apache.fastandroid.ui.activity;

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

import com.apache.fastandroid.R;
import com.apache.fastandroid.ui.fragment.pic.PicTabsFragment;
import com.apache.fastandroid.ui.fragment.video.VideoTabsFragment;
import com.tesla.framework.common.util.ResUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();



        onMenuItemClicked(R.id.nav_item_pic, ResUtil.getString(R.string.nav_pic));
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
        mDrawerLayout.setDrawerListener(drawerToggle);
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
                onMenuItemClicked(item.getItemId(), item.getTitle().toString());
                return false;
            }
        });
    }

    public void onMenuItemClicked(int itemId, String title){
        Fragment fragment = null;
        if (itemId == R.id.nav_item_pic){
            fragment = PicTabsFragment.newFragment();
        }else if (itemId == R.id.nav_item_video){
            fragment = VideoTabsFragment.newFragment();
        }else if (itemId == R.id.nav_item_music){
            fragment = VideoTabsFragment.newFragment();
        }


        getSupportActionBar().setTitle(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_content,fragment, "MainFragment").commit();
        closeDrawer();

        selecteId = itemId;

    }

    public boolean isDrawerOpened() {
        return mDrawerLayout.isDrawerOpen(Gravity.LEFT) || mDrawerLayout.isDrawerOpen(Gravity.RIGHT);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (drawerToggle != null)
            drawerToggle.syncState();
    }


}
