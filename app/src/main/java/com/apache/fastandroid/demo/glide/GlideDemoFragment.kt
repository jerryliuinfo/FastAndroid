package com.apache.fastandroid.demo.glide

import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/6/24.
 */
class GlideDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("基本用法", "基本用法",GlideBasicUsageFragment::class.java),
                ViewItemBean("缓存", "缓存",GlideCacheUsageFragment::class.java)
        )
    }

}