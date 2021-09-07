package com.apache.fastandroid.demo.widget.banner;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.R;
import com.apache.fastandroid.bean.DataBean;
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;

/**
 * Created by Jerry on 2021/4/28.
 */
public class BannerDemoFragment2 extends BaseStatusFragmentNew {
    @Override
    public int inflateContentView() {
        return R.layout.widget_banner;
    }



    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        ImageAdapter imageAdapter = new ImageAdapter(DataBean.getTestData());
        Banner banner = findViewById(R.id.banner);
        banner.setAdapter(imageAdapter);
        banner.setIndicator(new CircleIndicator(getContext()));
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
    }
}
