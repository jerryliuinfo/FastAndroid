package com.tesla.framework.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.R;
import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.support.inject.InjectUtility;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.BaseFragment;
import com.tesla.framework.ui.widget.CustomToolbar;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * Created by JerryLiu on 17/04/08.
 */

public abstract class BaseActivity extends AppCompatActivity implements CustomToolbar.OnToolbarDoubleClickListener {

    static final String TAG = "Activity-Base";

    private BaseActivityHelper mHelper;

    private int theme = 0;// 当前界面设置的主题

    private Locale language = null;// 当前界面的语言

    private boolean isDestory;



    // 当有Fragment Attach到这个Activity的时候，就会保存
    private Map<String, WeakReference<BaseFragment>> fragmentRefs;

    private View rootView;

    @ViewInject(idStr = "toolbar")
    Toolbar mToolbar;

    protected int configTheme() {
        if (mHelper != null) {
            int appTheheme = mHelper.configTheme();
            if (appTheheme > 0)
                return appTheheme;
        }
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentRefs = new HashMap<>();

        mHelper = new BaseActivityHelper(this,this);
        if (savedInstanceState == null) {
            theme = configTheme();
            language = new Locale(SettingUtility.getPermanentSettingAsStr("language", Locale.getDefault().getLanguage()),
                    SettingUtility.getPermanentSettingAsStr("language-country", Locale.getDefault().getCountry()));
        } else {
            theme = savedInstanceState.getInt("theme");

            language = new Locale(savedInstanceState.getString("language"), savedInstanceState.getString("language-country"));
        }
        // 设置主题
        if (theme > 0)
            setTheme(theme);
        // 设置语言
        setLanguage(language);

        super.onCreate(savedInstanceState);

        if (inflateContentView() > 0) {
            setContentView(inflateContentView());
        }

        bindView();
        layoutInit(savedInstanceState);
    }

    protected void bindView(){

    }


    public void showContent(Class<? extends BaseFragment> target) {
        showContent(target, null);
    }

    public void showContent(Class<? extends BaseFragment> target, Bundle bundle) {
        try {
            BaseFragment fragment = target.newInstance();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(android.R.id.content, fragment);
            fragmentTransaction.addToBackStack("");
            fragmentTransaction.commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 指定Fragment的LayoutID
     *
     * @return
     */
    public abstract int inflateContentView();


    /**
     * 子类重写这个方法，初始化视图
     */
    public void layoutInit(Bundle savedInstanceState) {

    }


    public Toolbar getToolbar() {
        return mToolbar;
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);

        rootView = view;
        InjectUtility.initInjectedView(this, this, rootView);

        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null)
            setSupportActionBar(mToolbar);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setStatusBar();
        rootView = view;
        InjectUtility.initInjectedView(this, this, this.rootView);

        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null)
            setSupportActionBar(mToolbar);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putInt("theme", theme);
        outState.putString("language", language.getLanguage());
        outState.putString("language-country", language.getCountry());
    }

    public void addFragment(String tag, BaseFragment fragment) {
        fragmentRefs.put(tag, new WeakReference<>(fragment));
    }

    public void removeFragment(String tag) {
        fragmentRefs.remove(tag);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (theme == configTheme()) {

        } else {
            FastLog.i(TAG, "theme changed, reload()");
            reload();
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void setLanguage(Locale locale) {
        Resources resources = getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        config.locale = locale;
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        resources.updateConfiguration(config, dm);
    }


    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        isDestory = true;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mHelper != null) {
            boolean handle = mHelper.onOptionsItemSelected(item);
            if (handle){
                return true;
            }
        }

        if (item.getItemId() == android.R.id.home) {
            if (onHomeClick())
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean onHomeClick() {
        if (getActivityHelper() != null) {
            boolean handle = getActivityHelper().onHomeClick();
            if (handle)
                return true;
        }

        Set<String> keys = fragmentRefs.keySet();
        for (String key : keys) {
            WeakReference<BaseFragment> fragmentRef = fragmentRefs.get(key);
            BaseFragment fragment = fragmentRef.get();
            if (fragment != null && fragment.onHomeClick())
                return true;
        }

        return onBackClick();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mHelper != null) {
            boolean handle = mHelper.onKeyDown(keyCode, event);
            if (handle)
                return true;
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (onBackClick())
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onBackClick() {
        if (getActivityHelper() != null) {
            boolean handle = getActivityHelper().onBackClick();
            if (handle)
                return true;
        }

        Set<String> keys = fragmentRefs.keySet();
        for (String key : keys) {
            WeakReference<BaseFragment> fragmentRef = fragmentRefs.get(key);
            BaseFragment fragment = fragmentRef.get();
            if (fragment != null && fragment.onBackClick())
                return true;
        }

        finish();

        return true;
    }


    @Override
    public void finish() {
        // 2014-09-12 解决ATabTitlePagerFragment的destoryFragments方法报错的BUG
        setDestory(true);

        super.finish();

    }

    public boolean isDestory() {
        return isDestory;
    }

    public void setDestory(boolean destory) {
        this.isDestory = destory;
    }


    @Override
    public boolean OnToolbarDoubleClick() {
        Set<String> keys = fragmentRefs.keySet();
        for (String key : keys) {
            WeakReference<BaseFragment> fragmentRef = fragmentRefs.get(key);
            BaseFragment fragment = fragmentRef.get();
            if (fragment != null && fragment instanceof CustomToolbar.OnToolbarDoubleClickListener) {
                if (((CustomToolbar.OnToolbarDoubleClickListener) fragment).OnToolbarDoubleClick())
                    return true;
            }
        }

        return false;
    }


    public BaseActivityHelper getActivityHelper() {
        return mHelper;
    }

    protected void setStatusBar() {
    }


    protected void setToolbarTitle(String msg) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(msg);

    }


}
