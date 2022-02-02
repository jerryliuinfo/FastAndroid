package com.apache.fastandroid.demo.blacktech;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.R;
import com.tesla.framework.component.logger.Logger;
import com.tesla.framework.ui.fragment.BaseFragment;
import com.xuexiang.xaop.DebugLog;
import com.xuexiang.xaop.Intercept;
import com.xuexiang.xaop.SingleClick;

/**
 * Created by Jerry on 2022/2/2.
 */
public class ClickDebounceFragmentNew extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_debounce_click;
    }

    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {
        super.layoutInit(inflater, savedInstanceState);

        findViewById(R.id.btn_debounce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnClick(v);
            }
        });
    }


    /**
     * 设置5秒内响应一次点击
     */
    @SingleClick(5000)
    @DebugLog(priority = Log.ERROR)
    @Intercept(3)
    public void handleOnClick(View v) {
        Logger.d("点击响应！");
    }
}
