package com.apache.fastandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;

import com.apache.fastandroid.annotations.CostTime;
import com.apache.fastandroid.databinding.ActivityMainNewBinding;
import com.apache.fastandroid.demo.bean.UserBean;
import com.apache.fastandroid.demo.jetpack.JetPackDemoFragment;
import com.blankj.utilcode.util.ToastUtils;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.eventbus.FastBus;
import com.tesla.framework.ui.activity.BaseVmActivity;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

import java.io.Serializable;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends BaseVmActivity<ActivityMainNewBinding> implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private ActionBarDrawerToggle drawerToggle;
    private int selecteId = -1;

    public static void launch(Activity from, UserBean userBean){
        Intent intent = new Intent(from, MainActivity.class);
        intent.putExtra("userBean",userBean);
        from.startActivity(intent);
    }



    @Override
    public ActivityMainNewBinding bindView() {
        return ActivityMainNewBinding.inflate(getLayoutInflater());
    }

    private NavController mNavController;

    @CostTime
    @Override
    public void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();
        loadMenuData();

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        mNavController = hostFragment.getNavController();



        AppBarConfiguration configuration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        configuration = new AppBarConfiguration.Builder(R.id.home_dest,R.id.demo_dest).setOpenableLayout(mBinding.drawer).build();

        setupActionBar(mNavController,configuration);
        setupNavigationMenu(mNavController);


        FragmentContainerActivity.launch(this, JetPackDemoFragment.class,null);
    }

    private void setupActionBar(NavController navController,
                               AppBarConfiguration appBarConfig) {

        /**
         * 当导航的目的地发生变化时， NavigationUI 将会自动切换展示的 label
         * 使用 AppBarConfiguration 时，它将会决定是显示 抽屉图标还是 向上箭头?
         */
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfig);

    }


    private void setupNavigationMenu( NavController navController) {
        NavigationUI.setupWithNavController(mBinding.navigationView, navController);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String key = intent.getStringExtra("key");
        Serializable user = intent.getSerializableExtra("user");
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, mBinding.drawer,
                toolbar, R.string.draw_open, R.string.draw_close) {

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
        /*mBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FastLog.d(TAG, "onNavigationItemSelected item title = %s", item.getTitle());
                item.setChecked(true);
                onMenuItemClicked(item.getItemId(), item.getTitle().toString());
                return true;
            }
        });*/



    }


    public void onMenuItemClicked(int itemId, String title){

      /*  Fragment fragment = null;
        switch (itemId){
            case R.id.nav_item_home:
                fragment = HomeFragment.newInstance();
                break;

            case R.id.nav_item_demo:

//                startActivity(new Intent(this,DemoListActivity.class));
                return;
        }


        if (fragment != null && selecteId != itemId){
            getSupportActionBar().setTitle(title);
            getSupportFragmentManager().beginTransaction().replace(R.id.lay_content,fragment, "MainFragment").commit();
        }

        closeDrawer();

        selecteId = itemId;*/
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

