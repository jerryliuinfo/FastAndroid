package com.apache.fastandroid.demo.widget.property

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2022/6/14.
 */
class UIPropertyDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("animateLayoutChanges", "animateLayoutChanges", AnimateLayoutChangesDemoFragment::class.java),
        )
    }

}