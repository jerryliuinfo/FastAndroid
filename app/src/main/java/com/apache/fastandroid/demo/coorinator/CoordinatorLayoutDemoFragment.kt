package com.apache.fastandroid.demo.coorinator

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.*

/**
 * Created by Jerry on 2020/11/11.
 */
class CoordinatorLayoutDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("基本使用", "基本使用", CoordinatorBasicFragment::class.java),
                ViewItemBean("CollapsingToolbarLayout", "CollapsingToolbarLayout", CoordinatorCollapsingToolbarFragment::class.java),

        )
    }
}