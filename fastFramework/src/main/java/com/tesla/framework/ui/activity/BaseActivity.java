package com.tesla.framework.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.R;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.component.network.NetworkStateManager;
import com.tesla.framework.support.inject.InjectUtility;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew;
import com.tesla.framework.ui.widget.CustomToolbar;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


/**
 * Created by JerryLiu on 17/04/08.
 */

public abstract class BaseActivity extends AppCompatActivity implements CustomToolbar.OnToolbarDoubleClickListener {

    static final String TAG = "Activity-Base";

    private BaseActivityHelper mHelper;

    // 当有Fragment Attach到这个Activity的时候，就会保存
    private Map<String, WeakReference<BaseStatusFragmentNew>> fragmentRefs;


    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;


    /**
     * 判断当前Activity是否在前台。
     */
    protected boolean isActive = false;

    /**
     * 当前Activity的实例。
     */
    protected Activity activity = null;

    private WeakReference<Activity> weakRefActivity = null;


    private View rootView;

    @ViewInject(idStr = "toolbar")
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentRefs = new HashMap<>();
        super.onCreate(savedInstanceState);
        activity = this;
        weakRefActivity = new WeakReference(this);

        if (inflateContentView() > 0) {
            setContentView(inflateContentView());
        }

        getLifecycle().addObserver(NetworkStateManager.getInstance());
//        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
//        BarUtils.setStatusBarLightMode(this, true);
        bindView();
        layoutInit(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    protected void bindView(){

    }


    public void showContent(Class<? extends BaseStatusFragmentNew> target) {
        showContent(target, null);
    }

    public void showContent(Class<? extends BaseStatusFragmentNew> target, Bundle bundle) {
        try {
            BaseStatusFragmentNew fragment = target.newInstance();
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

        rootView = view;
        InjectUtility.initInjectedView(this, this, this.rootView);

        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null)
            setSupportActionBar(mToolbar);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



    }

    public void addFragment(String tag, BaseStatusFragmentNew fragment) {
        fragmentRefs.put(tag, new WeakReference<>(fragment));
    }

    public void removeFragment(String tag) {
        fragmentRefs.remove(tag);
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
            WeakReference<BaseStatusFragmentNew> fragmentRef = fragmentRefs.get(key);
            BaseStatusFragmentNew fragment = fragmentRef.get();
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
            WeakReference<BaseStatusFragmentNew> fragmentRef = fragmentRefs.get(key);
            BaseStatusFragmentNew fragment = fragmentRef.get();
            if (fragment != null && fragment.onBackClick())
                return true;
        }

        finish();

        return true;
    }


    @Override
    public boolean OnToolbarDoubleClick() {
        Set<String> keys = fragmentRefs.keySet();
        for (String key : keys) {
            WeakReference<BaseStatusFragmentNew> fragmentRef = fragmentRefs.get(key);
            BaseStatusFragmentNew fragment = fragmentRef.get();
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


    protected void setToolbarTitle(String msg) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }


    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((FrameworkApplication) this.getApplicationContext());
        }
        return mApplicationProvider.get(modelClass);
    }
}
