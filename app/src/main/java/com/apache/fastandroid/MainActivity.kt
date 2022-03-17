package com.apache.fastandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.apache.fastandroid.annotations.CostTime;
import com.apache.fastandroid.databinding.ActivityMainNewBinding;
import com.apache.fastandroid.demo.bean.UserBean;
import com.blankj.utilcode.util.ToastUtils;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.eventbus.FastBus;
import com.tesla.framework.ui.activity.BaseVmActivity;

import java.io.Serializable;

import androidx.appcompat.app.ActionBarDrawerToggle;
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
        loadMenuData();

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        mNavController = hostFragment.getNavController();
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        configuration = new AppBarConfiguration.Builder(R.id.home_dest,R.id.demo_dest).setOpenableLayout(mBinding.drawer).build();

        setupActionBar(mNavController,configuration);
        setupNavigationMenu(mNavController);


//        FragmentContainerActivity.launch(this, JetPackDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, RoomDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, SaveStateHandleFragment.class,null);
//        FragmentContainerActivity.launch(this, SunFlowerTabsFragment.class,null);
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
        drawerToggle = new ActionBarDrawerToggle(this, mBinding.drawer,
                mBinding.toolbar, R.string.draw_open, R.string.draw_close) {

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

