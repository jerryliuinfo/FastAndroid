package com.apache.fastandroid.demo.widget

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.performance.practice.AnrWatchDogDemoFragment
import com.apache.fastandroid.demo.widget.banner.BannerDemoFragment
import com.apache.fastandroid.demo.widget.banner.BannerDemoFragment2
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_practice_demo.*

/**
 * Created by Jerry on 2020/11/11.
 */
class WidgetDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                //https://github.com/youth5201314/banner
//                ViewItemBean("Banner", "Banner", BannerDemoFragment::class.java),
                ViewItemBean("Banner", "Banner", BannerDemoFragment2::class.java)
        )
    }


}