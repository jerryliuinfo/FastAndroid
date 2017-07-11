package com.apache.fastandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.apache.fastandroid.R;
import com.apache.fastandroid.base.BizFragment;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.fragment.ABaseFragment;

import java.lang.reflect.Method;

/**
 */
public class FastAndroidCommonActivity extends BaseActivity  {

    public static final String FRAGMENT_TAG = "FRAGMENT_CONTAINER";

    private int contentId;
    private int overrideTheme = -1;

    /**
     * 启动一个界面
     *
     * @param activity
     * @param clazz
     * @param args
     */
    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args) {
        Intent intent = new Intent(activity, FastAndroidCommonActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        activity.startActivity(intent);
    }

    public static void launchForResult(Fragment fragment, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        if (fragment.getActivity() == null)
            return;
        launchForResult(fragment.getActivity(),clazz,args,requestCode);
    }

    public static void launchForResult(Activity from, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {

        if (from == null){
            return;
        }
        Intent intent = new Intent(from, FastAndroidCommonActivity.class);
        intent.putExtra("className", clazz.getName());
        if (args != null)
            intent.putExtra("args", args);
        from.startActivityForResult(intent, requestCode);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contentId = savedInstanceState == null ? R.layout.ui_fragment_container
                                               : savedInstanceState.getInt("contentId");
        overrideTheme = savedInstanceState == null ? -1
                                                   : savedInstanceState.getInt("overrideTheme");

        Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                String className = getIntent().getStringExtra("className");
                if (TextUtils.isEmpty(className)) {
                    super.onCreate(savedInstanceState);
                    finish();
                    return;
                }

                FragmentArgs values = (FragmentArgs) getIntent().getSerializableExtra("args");

                Class clazz = Class.forName(className);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[] { Bundle.class });
                        method.invoke(fragment, FragmentArgs.transToBundle(values));
                    } catch (Exception e) {
                    }
                }
                // 重写Activity的主题
                try {
                    Method method = clazz.getMethod("setActivityTheme");
                    if (method != null) {
                        int theme = Integer.parseInt(method.invoke(fragment).toString());
                        if (theme > 0) {
                            overrideTheme = theme;
                        }
                    }
                } catch (Exception e) {
                }
                // 重写Activity的contentView
                try {
                    Method method = clazz.getMethod("inflateActivityContentView");
                    if (method != null) {
                        int fragmentConfigId = Integer.parseInt(method.invoke(fragment).toString());
                        if (fragmentConfigId > 0) {
                            contentId = fragmentConfigId;
                        }
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.onCreate(savedInstanceState);
                finish();
                return;
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(contentId);

        if (fragment != null) {
            if (!(fragment instanceof ABaseFragment) || ((ABaseFragment) fragment).inflateContentView() > 0) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().add(fragment, FRAGMENT_TAG).commit();
            }
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowHomeEnabled(false);

        BizFragment.createBizFragment(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("contentId", contentId);
        outState.putInt("overrideTheme", overrideTheme);
    }

    @Override
    protected int configTheme() {
        if (overrideTheme > 0)
            return overrideTheme;

        return super.configTheme();
    }


}
