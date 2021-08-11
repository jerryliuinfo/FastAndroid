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
                ViewItemBean("缓存", "缓存",GlideCacheUsageFragment::class.java),
                ViewItemBean("回调与监听", "回调与监听",GlideCallbackListenFragment::class.java),
                ViewItemBean("图片转换", "图片转换",GlideTransformFragment::class.java)
        )
    }

}