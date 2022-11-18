package com.apache.fastandroid.demo.guide

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class GuideDemoListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("Activity", "Activity属性", ActivityPropertyDemoFragment::class.java)


        )
    }
}