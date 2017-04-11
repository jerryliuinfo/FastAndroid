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
import com.apache.fastandroid.ui.fragment.FavoriteFragment;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.widget.swipeback.SwipeActivityHelper;

public class MainActivity extends BaseActivity implements SwipeActivityHelper.EnableSwipeback{
    public static final String TAG = MainActivity.class.getSimpleName();
    public static void launch(Activity from){
        from.startActivity(new Intent(from,MainActivity.class));
    }


    @ViewInject(id = R.id.drawer)
    DrawerLayout mDrawerLayout;


    @ViewInject(id = R.id.navigation_view)
    NavigationView mNavigationView;


    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();


        /*//MainFragment.newFragment();
        Fragment fragment = TestTabFragment.newFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_content, fragment)
                .commit();
        getSupportActionBar().setTitle("news");*/

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
                onMenuItemClicked(item);
                return false;
            }
        });
    }

    public void onMenuItemClicked(MenuItem item){
        Fragment fragment = null;
        fragment = FavoriteFragment.newFragment(item);

        getSupportActionBar().setTitle(item.getTitle());
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_content,fragment, "MainFragment").commit();
        closeDrawer();

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

    @Override
    public boolean canSwipe() {
        return false;
    }
}
