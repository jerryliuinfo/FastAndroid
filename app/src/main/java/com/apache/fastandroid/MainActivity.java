package com.apache.fastandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apache.fastandroid.artemis.ArtemisContext;
import com.apache.fastandroid.artemis.base.MyBaseActivity;
import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.apache.fastandroid.artemis.bridge.RouterMap;
import com.apache.fastandroid.artemis.util.activitytask.ActivityLifeCallback;
import com.apache.fastandroid.setting.SettingFragment;
import com.apache.fastandroid.topic.news.MainNewsTabsFragment;
import com.apache.fastandroid.topic.support.utils.MainLog;
import com.apache.fastandroid.video.VideoTabsFragment;
import com.apache.fastandroid.wallpaper.WallPaperFragment;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.network.NetworkHelper;
import com.tesla.framework.common.util.view.StatusBarUtil;
import com.tesla.framework.component.bridge.ModularizationDelegate;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.widget.CircleImageView;
public class MainActivity extends MyBaseActivity{
    public static void launch(Activity from){
        from.startActivity(new Intent(from,MainActivity.class));
    }
    @ViewInject(id = R.id.drawer)
    DrawerLayout mDrawerLayout;

    @ViewInject(id = R.id.navigation_view)
    NavigationView mNavigationView;


    private ActionBarDrawerToggle drawerToggle;

    private int selecteId = -1;

    private  NetworkHelper.NetworkInductor mNetworkInductor = new NetworkHelper.NetworkInductor() {
        @Override
        public void onNetworkChanged(NetworkHelper.NetworkStatus status) {
            NLog.d(TAG, "onNetworkChanged status = %s", status);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NLog.d(TAG,"onCreate");

        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();
        loadMenuData();
        MenuItem menuItem = mNavigationView.getMenu().getItem(0);
        menuItem.setChecked(true);
        onMenuItemClicked(menuItem.getItemId(),menuItem.getTitle().toString());

        NetworkHelper.getInstance().addNetworkInductor(mNetworkInductor);
        MainLog.d("top activity = %s", ActivityLifeCallback.get().topActivity());
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ("".equals(intent.getAction())){

        }
    }

    private void loadMenuData(){
        View headView = mNavigationView.getHeaderView(0);
        CircleImageView circleImageView =  headView.findViewById(R.id.iv_user_avator);
        TextView tv_username = headView.findViewById(R.id.tv_username);
        ImageView iv_arrow =  headView.findViewById(R.id.iv_arrow);
        View layout_user= headView.findViewById(R.id.layout_user);
        try {
            ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":startLoginedUserListActivity",null,null,new Object[]{this});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ArtemisContext.getUserBean() != null){
            tv_username.setText(ArtemisContext.getUserBean().getName());
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
                break;
            case R.id.nav_item_wallpaer:
                fragment = WallPaperFragment.newFragment();
                break;
            case R.id.nav_item_pic:
                //fragment = PicTabsFragment.newFragment();
                fragment = MainNewsTabsFragment.newFragment();
                break;
            case R.id.nav_item_video:
                fragment = VideoTabsFragment.newFragment();
                break;
            case R.id.nav_item_music:
                fragment = VideoTabsFragment.newFragment();
                break;
            case R.id.nav_item_topic2:
                //fragment = TestFragment.newFragment();
                //fragment = TestRecycleHeaderViewFragment.newFragment();
                fragment = (Fragment) ARouter.getInstance().build(RouterMap.TOPIC.TOPIC_INFORMAGTION_FRAGMENT).navigation();
                break;
            case R.id.nav_item_topic_home:

                ARouter.getInstance().build(RouterMap.TOPIC.TOPIC_HOMEACTIVITY).navigation();
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


    private boolean canFinish = false;
    @Override
    public boolean onBackClick() {
        if (!canFinish) {
            canFinish = true;

            showMessage("再按一次退出");

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


   /* private boolean mDataChanged = false;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCollectionList(RefreshCollectionListEvent event){
        NovelLog.d("refreshCollectionList--------->");

        setToolbarTitle(String.valueOf(event.count));

    }*/

   public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();
        NLog.d(TAG,"onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        NLog.d(TAG,"onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NLog.d(TAG,"onRestart");
    }


    @Override
    protected void onPause() {
        super.onPause();
        NLog.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        NLog.d(TAG,"onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NLog.d(TAG,"onDestroy");

        NetworkHelper.getInstance().removeNetworkInductor(mNetworkInductor);

        //EventBus.getDefault().unregister(this);
    }


}
