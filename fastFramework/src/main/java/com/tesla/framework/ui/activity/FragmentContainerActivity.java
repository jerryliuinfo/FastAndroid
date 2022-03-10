package com.tesla.framework.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tesla.framework.R;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.component.logger.Logger;
import com.tesla.framework.databinding.CommUiFragmentContainerBinding;

import java.io.Serializable;
import java.lang.reflect.Method;

import androidx.fragment.app.Fragment;


public class FragmentContainerActivity extends BaseVmActivity<com.tesla.framework.databinding.CommUiFragmentContainerBinding> {


    public static final String FRAGMENT_TAG = "FRAGMENT_CONTAINER";


    public static final String EXTRA_CLASS_NAME = "className";
    public static final String EXTRA_ARGS = "args";

    /**
     * 启动一个界面
     *
     * @param activity
     * @param clazz
     * @param args
     */
    public static void launch(Activity activity, Class<? extends Fragment> clazz, FragmentArgs args) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra(EXTRA_CLASS_NAME, clazz.getName());
        if (args != null)
            intent.putExtra(EXTRA_ARGS, args);
        activity.startActivity(intent);
    }

    public static void launchForResult(Fragment fragment, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        launchForResult((BaseVmActivity) fragment.getActivity(),clazz,args,requestCode);
    }

    public static void launchForResult(BaseVmActivity from, Class<? extends Fragment> clazz, FragmentArgs args, int requestCode) {
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        intent.putExtra(EXTRA_CLASS_NAME, clazz.getName());
        if (args != null)
            intent.putExtra(EXTRA_ARGS, args);
        from.startActivityForResult(intent, requestCode);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String className = getIntent().getStringExtra(EXTRA_CLASS_NAME);
        if (TextUtils.isEmpty(className)) {
            finish();
            return;
        }
        Logger.d(String.format("FragmentContainerActivity onCreate this:%s", this));

        int contentId = R.layout.comm_ui_fragment_container;

        FragmentArgs values = (FragmentArgs) getIntent().getSerializableExtra(EXTRA_ARGS);

        Fragment fragment = null;
        if (savedInstanceState == null) {
            try {
                Class clazz = Class.forName(className);
                fragment = (Fragment) clazz.newInstance();
                // 设置参数给Fragment
                if (values != null && values.getValues().size() > 0) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[] { Bundle.class });
                        method.invoke(fragment, FragmentArgs.transToBundle(values));
                    } catch (Exception e) {
                        FastLog.printStackTrace(e);
                    }
                }

            } catch (Exception e) {
                FastLog.printStackTrace(e);
                finish();
                return;
            }
        }

        super.onCreate(savedInstanceState);
//        setContentView(contentId);

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
        }

        if (values != null && values.get("title") != null){
            Serializable serializable = values.get("title");
            if (serializable != null && serializable instanceof String){
                setToolbarTitle((String) serializable);
            }
        }

    }

    @Override
    public CommUiFragmentContainerBinding bindView() {
        return CommUiFragmentContainerBinding.inflate(getLayoutInflater());
    }





}
