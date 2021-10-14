package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.agentweb.AgentWebDemoListFragment
import com.apache.fastandroid.demo.glide.GlideDemoFragment
import com.apache.fastandroid.demo.mmkv.MMKVFragment
import com.apache.fastandroid.demo.widget.banner.BannerDemoFragment2
import com.apache.fastandroid.demo.recycleview.BaseRecycleViewAdapterDemoListFragment
import com.apache.fastandroid.demo.rxjava.RxJavaDemoFragment
import com.apache.fastandroid.demo.widget.qmui.QMUIDemoFragment
import com.apache.fastandroid.demo.widget.supertextview.SuperTextViewDemoListFragment
import com.bumptech.glide.Glide

/**
 * Created by Jerry on 2020/11/11.
 */
class OpenSourceDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("Glide", "Glide", GlideDemoFragment::class.java),
                ViewItemBean("Rxjava", "Rxjava操作符", RxJavaDemoFragment::class.java),
                ViewItemBean("AgentWeb", "AgentWeb", AgentWebDemoListFragment::class.java)

        )
    }


}