package com.apache.fastandroid;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apache.artemis_annotation.AptTest;
import com.apache.artemis_annotation.BindPath;
import com.apache.fastandroid.annotations.CheckLogin;
import com.apache.fastandroid.annotations.CostTime;
import com.apache.fastandroid.artemis.AppContext;
import com.apache.fastandroid.artemis.componentService.topic.ITopicService;
import com.apache.fastandroid.bean.BindUserInfo;
import com.apache.fastandroid.bean.UserBean;
import com.apache.fastandroid.demo.DemoListActivity;
import com.apache.fastandroid.demo.nodrawable.NoDrawableFragment;
import com.apache.fastandroid.setting.SettingFragment;
import com.apache.fastandroid.task.DelayInitTask1;
import com.apache.fastandroid.task.DelayInitTask2;
import com.apache.fastandroid.topic.news.MainNewsTabsFragment;
import com.apache.fastandroid.topic.support.utils.MainLog;
import com.apache.fastandroid.util.MainLogUtil;
import com.apache.fastandroid.wallpaper.WallPaperFragment;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.navigation.NavigationView;
import com.optimize.performance.launchstarter.DelayInitDispatcher;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.toast.BadTokenListener;
import com.tesla.framework.common.util.toast.ToastCompat;
import com.tesla.framework.component.eventbus.FastBus;
import com.tesla.framework.route.Route;
import com.tesla.framework.support.inject.OnClick;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

@AptTest(path = "main")
@BindPath("login/login")
public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();


    DrawerLayout mDrawerLayout;

    NavigationView mNavigationView;

    private ActionBarDrawerToggle drawerToggle;

    private int selecteId = -1;


    public static void launch(Activity from, UserBean userBean){
        Intent intent = new Intent(from, MainActivity.class);
        intent.putExtra("userBean",userBean);
        from.startActivity(intent);
    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        super.bindView();

        mDrawerLayout = findViewById(R.id.drawer);
        mNavigationView = findViewById(R.id.navigation_view);
    }

    @CostTime
    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

//        ProxyTool.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();
        loadMenuData();
        MenuItem menuItem = mNavigationView.getMenu().getItem(0);
        menuItem.setChecked(true);
        onMenuItemClicked(menuItem.getItemId(),menuItem.getTitle().toString());

        PermissionX.init(this).permissions(permission.WRITE_EXTERNAL_STORAGE).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if (allGranted) {
                } else {
                    ToastUtils.showShort("These permissions are denied: %s", deniedList);
                }
            }
        });

        BindUserInfo info = new BindUserInfo("title", "www.baidu.com", "Jerry");
        NLog.d(TAG, "info: %s", info);
//        DemoListActivity.launch(this);
//        FragmentContainerActivity.launch(this, BaseRecycleViewAdapterDemoListFragment.class,null);
//        FragmentContainerActivity.launch(this, SuperTextViewDemoListFragment.class,null);
//        FragmentContainerActivity.launch(this, JetPackLifeCycleDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, DatebinDingBasicDemoFragment.class,null);
        FragmentContainerActivity.launch(this, NoDrawableFragment.class,null);


        DelayInitDispatcher dispatcher = new DelayInitDispatcher();
        dispatcher.addTask(new DelayInitTask1()).addTask(new DelayInitTask2()).start();




    }



    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        MainLog.d("called setContentView");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ("".equals(intent.getAction())){

        }
    }


    @OnClick
    private void loadMenuData(){
        View headView = mNavigationView.getHeaderView(0);
        TextView tv_username = headView.findViewById(R.id.tv_username);

        if (AppContext.getUserInfoBean() != null){
            tv_username.setText(AppContext.getUserInfoBean().getName());
        }else {
            tv_username.setText("未登录");
        }
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

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    private void setupNavigationView(){
        View headerView = mNavigationView.getHeaderView(0);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
            case R.id.nav_item_topic:
                fragment = MainNewsTabsFragment.newFragment();
                break;
            case R.id.nav_item_wallpaer:
                goToTopicActivity(itemId,title);
                ToastCompat.makeText(this, "Hello Toast", Toast.LENGTH_LONG).setBadTokenListener(new BadTokenListener() {
                    @Override
                    public void onBadTokenCaught(@NonNull Toast toast) {

                    }
                }).show();
                return;
            case R.id.nav_item_pic:
                //fragment = PicTabsFragment.newFragment();
                fragment = MainNewsTabsFragment.newFragment();
                break;
            case R.id.nav_item_video:
                break;
            case R.id.nav_item_demo:

                startActivity(new Intent(this,DemoListActivity.class));
                return;
            case R.id.nav_item_topic_home:
                //ARouter.getInstance().build(RouterMap.TOPIC.HOMEACTIVITY).withInt("name",1).navigation();
                Route.getInstance().getService(ITopicService.class.getSimpleName());
                closeDrawer();
                return;
            case R.id.nav_item_setting:
                SettingFragment.launch(this);
                closeDrawer();

                selecteId = itemId;
                return;
        }


        if (fragment != null && selecteId != itemId){
            getSupportActionBar().setTitle(title);
            getSupportFragmentManager().beginTransaction().replace(R.id.lay_content,fragment, "MainFragment").commit();
        }

        closeDrawer();

        selecteId = itemId;
    }

    /**
     * 加上这个注解就会自动判断是否已登录，如果未登录则会先跳转到登录页面,已登录，则直接执行业务逻辑
     * @param itemId
     * @param title
     */
    @CheckLogin("")
    public void goToTopicActivity(int itemId, String title){
        MainLogUtil.d("goToTopicActivity");
        Fragment  fragment = WallPaperFragment.newFragment();
        if (fragment != null && selecteId != itemId){
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

        if (drawerToggle != null){
            drawerToggle.syncState();
        }
    }

    @Override
    protected void setStatusBar() {
        int mStatusBarColor = ColorUtils.getColor(R.color.colorPrimary);

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


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FastBus.getInstance().unregiste(this);
    }

}
