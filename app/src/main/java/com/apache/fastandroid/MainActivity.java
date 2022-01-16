package com.apache.fastandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apache.artemis_annotation.AptTest;
import com.apache.artemis_annotation.BindPath;
import com.apache.fastandroid.annotations.CostTime;
import com.apache.fastandroid.bean.UserBean;
import com.apache.fastandroid.demo.DemoListActivity;
import com.apache.fastandroid.demo.basic.RecycleViewItemDecorationFragment2;
import com.apache.fastandroid.demo.bean.AuthToken;
import com.apache.fastandroid.home.HomeFragment;
import com.apache.fastandroid.util.AccessDenyException;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.logger.Logger;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.eventbus.FastBus;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    private UserBean userBean;
    @CostTime
    @Override
    public void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setupDrawer(savedInstanceState);
        setupNavigationView();
        loadMenuData();
        MenuItem menuItem = mNavigationView.getMenu().getItem(0);
        menuItem.setChecked(true);
        onMenuItemClicked(menuItem.getItemId(),menuItem.getTitle().toString());

//        FragmentContainerActivity.launch(this, RelearnAndroidDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, RxJavaDemoFragment2.class,null);
//        FragmentContainerActivity.launch(this, MatrixDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, PerformanceDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, GlideBasicUsageFragment.class,null);
//        FragmentContainerActivity.launch(this, HookContextFragment.class,null);
//        FragmentContainerActivity.launch(this, AspectJDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, DrakeetDemoListFragment.class,null);
//        FragmentContainerActivity.launch(this, KnowledgeFragment.class,null);
//        FragmentContainerActivity.launch(this, BlackTechDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, ChainModeDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, ProxyModeDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, SpecifyParentViewDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, ReflectionDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, ApiDemoFragment2.class,null);
//        FragmentContainerActivity.launch(this, CouroutineDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, ConcurrencyDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, DrakeetCommonFragment.class,null);
//        FragmentContainerActivity.launch(this, CoordinatorLayoutDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, TransitionDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, ApiDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, KotlinKnowledgeFragment.class,null);
//        FragmentContainerActivity.launch(this, ConstraintLayoutDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, TaskDispatcherDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, HitPitDemoListFragment.class,null);
//        FragmentContainerActivity.launch(this, LiveDataTransformSwitchMapFragment.class,null);
//        FragmentContainerActivity.launch(this, LoadSirDemoListFragment.class,null);
//        FragmentContainerActivity.launch(this, LoggerDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, CouroutineDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, RxJavaDemoListFragment.class,null);
//        FragmentContainerActivity.launch(this, RecycleViewItemDecorationFragment.class,null);
//        FragmentContainerActivity.launch(this, DrakeetDemoListFragment.class,null);
//        FragmentContainerActivity.launch(this, CustomViewFragment.class,null);
//        FragmentContainerActivity.launch(this, DrawableListFragment.class,null);
//        FragmentContainerActivity.launch(this, SampleCode1DemoFragment.class,null);
//        FragmentContainerActivity.launch(this, KnowledgeFragment.class,null);
//        FragmentContainerActivity.launch(this, DrakeetCommonFragment.class,null);
//        FragmentContainerActivity.launch(this, ViewPumpDemoFragment.class,null);
//        FragmentContainerActivity.launch(this, DrakeetTextviewFragment.class,null);
//        FragmentContainerActivity.launch(this, PermissionMonitorFragment.class,null);
//        FragmentContainerActivity.launch(this, GlideBasicUsageFragment.class,null);
//        FragmentContainerActivity.launch(this, KotlinKnowledgeFragment.class,null);
        FragmentContainerActivity.launch(this, RecycleViewItemDecorationFragment2.class,null);

        Context context = getApplicationContext();
        Logger.d(String.format("context file dir:%s, cache:%s",context.getFilesDir(),context.getCacheDir()));
        Logger.d(String.format("external file dir:%s, cache:%s",context.getExternalFilesDir(null),context.getExternalCacheDir()));


        TextView textView = new TextView(this);
        String str = "Longfu2012";
        textView.setFilters(new InputFilter[]{new MyFilter("")});

    }

    public class MyFilter implements InputFilter {
        String ch = null;
        String str = null;

        public MyFilter(String str) {
            this.str = str;
        }

        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            //最后输入的一个字符
            if (dest.length() < str.length()) {
                //截取未过滤的最后一个字符
                ch = str.substring(dstart + start, dstart + end);
            } else {
                return dest.subSequence(dstart, dend);
            }

            if (ch.equals(source)) {
                Toast.makeText(MainActivity.this, "符合要求",
                        Toast.LENGTH_SHORT).show();
                //符合规定要求的字符以原输入显示
                return dest.subSequence(dstart, dend) + source.toString();
            } else {
                Toast.makeText(MainActivity.this, "不符合要求喔~",
                        Toast.LENGTH_SHORT).show();
                //如果没有按要求输入字符，则该字符被“*”代替，并显示
                return dest.subSequence(dstart, dend) + "*";
            }

        }

    }

    private int retryCount = 0;

    private boolean initializated = false;

    private String getData(){
        Logger.d("getData");
        return "Hello";

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
        View headView = mNavigationView.getHeaderView(0);
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





    private <T> Function<Throwable, ? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> toBeResumed) {
        return new Function<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> apply(Throwable throwable) throws Throwable {
                if (isHttp401Error(throwable)) {
                    return createTokenObvervable().flatMap(new Function<AuthToken, Observable<? extends T>>() {
                        @Override
                        public Observable<? extends T> apply(AuthToken authToken) throws Throwable {
//                            appendText(tvLogs, "refresh token success,token's validity is 10s\nResume last request");

                            return toBeResumed;
                        }
                    });
                }
                // re-throw this error because it's not recoverable from here
                return Observable.error(throwable);
            }

            public boolean isHttp401Error(Throwable throwable) {
                return throwable instanceof AccessDenyException;
            }

        };
    }

    public Observable<AuthToken> createTokenObvervable() {
        return Observable.create(new ObservableOnSubscribe<AuthToken>() {
            @Override
            public void subscribe(ObservableEmitter<AuthToken> emitter) throws Throwable {
                emitter.onNext(new AuthToken("adafsddfsd"));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}

