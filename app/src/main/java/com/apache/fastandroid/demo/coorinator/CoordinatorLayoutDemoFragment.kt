package com.apache.fastandroid.demo.coorinator

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2020/11/11.
 * 参考:
 * https://blog.mindorks.com/using-coordinator-layout-in-android
 * https://blog.csdn.net/Peterpan1223/article/details/102828133
 *
 */
class CoordinatorLayoutDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("CoordinateLayout", "scroll", CoordinatorBasicFragment::class.java,addTitleBar = false),
                ViewItemBean("CoordinateLayout", "enterAlways", CoordinatorEnterAlwaysFragment::class.java,addTitleBar = false),
                ViewItemBean("CoordinateLayout", "enterAlwaysCollapsed", CoordinatorEnterAlwaysCollpaseFragment::class.java,addTitleBar = false),
                ViewItemBean("CoordinateLayout", "exitUntilCollapse", CoordinatorExitUntilCollapseFragment::class.java,addTitleBar = false),

        )
    }
}