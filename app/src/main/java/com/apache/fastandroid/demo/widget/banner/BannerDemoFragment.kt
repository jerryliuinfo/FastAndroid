package com.apache.fastandroid.demo.widget.banner

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.DataBean
import com.tesla.framework.ui.fragment.ABaseFragment
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.widget_banner.*

/**
 * Created by Jerry on 2021/4/28.
 */
class BannerDemoFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.widget_banner
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
//        var imageAdapter:ImageAdapter = ImageAdapter(DataBean.getTestData())
//        banner.setAdapter(imageAdapter)
//        banner.setIndicator(CircleIndicator(this))
        banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
    }
}