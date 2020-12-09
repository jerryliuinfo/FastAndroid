package com.apache.fastandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import com.apache.artemis_annotation.AptTest;
import com.apache.artemis_annotation.BindPath;
import com.apache.artemis_annotation.BindViewById;
import com.apache.fastandroid.annotations.CheckLogin;
import com.apache.fastandroid.annotations.CostTime;
import com.apache.fastandroid.artemis.AppContext;
import com.apache.fastandroid.artemis.componentService.topic.ITopicService;
import com.apache.fastandroid.bean.UserBean;
import com.apache.fastandroid.jetpack.GpsCallback;
import com.apache.fastandroid.jetpack.GpsEngine;
import com.apache.fastandroid.performance.LaunchTimer;
import com.apache.fastandroid.setting.SettingFragment;
import com.apache.fastandroid.tink.TinkTest;
import com.apache.fastandroid.topic.news.MainNewsTabsFragment;
import com.apache.fastandroid.topic.support.utils.MainLog;
import com.apache.fastandroid.util.MainLogUtil;
import com.apache.fastandroid.wallpaper.WallPaperFragment;
import com.tesla.framework.common.util.FrameworkLogUtil;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.network.NetworkListener;
import com.tesla.framework.common.util.network.NetworkType;
import com.tesla.framework.common.util.view.StatusBarUtil;
import com.tesla.framework.component.eventbus.FastBus;
import com.tesla.framework.component.eventbus.Subscribe;
import com.tesla.framework.component.eventbus.ThreadMode;
import com.tesla.framework.route.Route;
import com.tesla.framework.support.annotation.ProxyTool;
import com.tesla.framework.support.inject.OnClick;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.widget.CircleImageView;
import com.tesla.framework.ui.widget.ToastUtils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.arch.core.util.Function;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

@AptTest(path = "main")
@BindPath("login/login")
public class MainActivity extends BaseActivity implements NetworkListener, View.OnClickListener {

    @BindViewById((R.id.drawer))
    DrawerLayout mDrawerLayout;

    @BindViewById((R.id.navigation_view))
    NavigationView mNavigationView;



    void doJob() {

    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }

    };



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

    MutableLiveData<String> mutableLiveData1;
    MutableLiveData<String> mutableLiveData2;
    MutableLiveData<Boolean> liveDataSwitch;



    @CostTime
    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);



        mutableLiveData1 = new MutableLiveData<>();
        mutableLiveData2 = new MutableLiveData<>();
        //1
        liveDataSwitch = new MutableLiveData<>();

        LiveData transformedLiveData= Transformations.switchMap(liveDataSwitch, new Function<Boolean, LiveData<String>>() {
            @Override
            public LiveData<String> apply(Boolean input) {
                if (input) {
                    return mutableLiveData1;
                } else {
                    return mutableLiveData2;
                }
            }
        });

        transformedLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String s) {
                Log.d(TAG, "onChanged:" + s);
            }
        });
        liveDataSwitch.postValue(false);
        mutableLiveData1.postValue("Android进阶之光11111");
        mutableLiveData2.postValue("Android进阶解密22222");






        ProxyTool.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();
        loadMenuData();
        MenuItem menuItem = mNavigationView.getMenu().getItem(0);
        menuItem.setChecked(true);
        onMenuItemClicked(menuItem.getItemId(),menuItem.getTitle().toString());


        MainLogUtil.d("current classLoader = %s",getClassLoader().toString());
        ClassLoader parentClassLoader = getClassLoader().getParent();
        while (parentClassLoader != null){
            MainLogUtil.d("parent classLoader = %s",parentClassLoader.toString());
            parentClassLoader = parentClassLoader.getParent();
        }
        MainLogUtil.d("Activity.class 由：" + Activity.class.getClassLoader() +" 加载");
        FastBus.getInstance().registe(this);
        doJob();

        int resId = R.color.cardview_dark_background;
        String resourceTypeName =
                getResources().getResourceTypeName(resId);
        String resourceEntryName = getResources().getResourceEntryName(resId);
        FrameworkLogUtil.d("resourceTypeName: %s, resourceEntryName:%s", resourceTypeName,resourceEntryName);

//        startActivity(new Intent(this, DemoListActivity.class));

    }

    @Subscribe(ThreadMode.MAIN)
    public void getUser(UserEvent event){
        MainLogUtil.d("getUser = %s",event);
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
        CircleImageView circleImageView =  headView.findViewById(R.id.iv_user_avator);
        TextView tv_username = headView.findViewById(R.id.tv_username);
        ImageView iv_arrow =  headView.findViewById(R.id.iv_arrow);
        View layout_user= headView.findViewById(R.id.layout_user);


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
                NLog.d(TAG, "onNavigationItemSelected item title = %s", item.getTitle());
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
                /*ProtocalA protocalA = Protocols.getTopicProtocal();
                if (protocalA != null){
                    String msg = protocalA.getUserA("jerry");
                    ToastUtils.showToast(this, msg);
                }else {
                    ToastUtils.showToast(this, "protocalA is null");
                }*/



                break;
            case R.id.nav_item_wallpaer:
                //fragment = WallPaperFragment.newFragment();

                /*CC.obtainBuilder(ModularizationConstans.MODULE_TOPIC_NAME).setContext(this).setActionName("showActivity").build().callAsyncCallbackOnMainThread(new IComponentCallback() {
                    @Override
                    public void onResult(CC cc, CCResult result) {
                        ToastUtils.showToast(MainActivity.this, "go topic activity");
                    }
                });*/
                goToTopicActivity(itemId,title);
                return;
            case R.id.nav_item_pic:
                //fragment = PicTabsFragment.newFragment();
                fragment = MainNewsTabsFragment.newFragment();
                break;
            case R.id.nav_item_video:
                break;
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LaunchTimer.endRecord("onWindowFocusChanged");
    }

    @Override
    protected void setStatusBar() {
        int mStatusBarColor = ResUtil.getColor(R.color.colorPrimary);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer), mStatusBarColor, 112);

    }

    //test cherry-pick


    private boolean canFinish = false;
    @Override
    public boolean onBackClick() {
        if (!canFinish) {
            canFinish = true;
            ToastUtils.showToast(this,"再按一次退出");
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



   public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onConnected(NetworkType networkType) {

    }

    @Override
    public void onDisconnected() {

    }



    private void addOnClickListeners(@IdRes int... ids) {
        if (ids != null) {
            for (@IdRes int id : ids) {
                findViewById(id).setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
       /* try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        MainLogUtil.d("onClick view id = %s",ResUtil.getResourceName(v.getId()));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FastBus.getInstance().unregiste(this);
    }

    public void onTest(View view) {
        TinkTest test = new TinkTest();
        test.test(this);
    }

    public void onFix(View view) {


    }






    private GpsCallback callback;
    /**
     * 第1种方式 统一问题，如果某个activity的onpause方法忘记调用GpsEngine.getInstance().onPauseAction();
     * 就会出问题
     */
    @Override
    protected void onResume() {
        super.onResume();
        GpsEngine.getInstance().onResumeAction();

        //代码入侵
        if (callback != null){
            callback.onResumeAction();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        GpsEngine.getInstance().onPauseAction();
        //代码入侵
        if (callback != null){
            callback.onPauseAction();
        }

    }
}
