package com.apache.fastandroid.jetpack

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.databinding.DatabindingFragmentDemo
import com.apache.fastandroid.demo.room.RoomDemoFragment
import com.apache.fastandroid.demo.sunflower.fragement.SunFlowerHomeViewPagerFragment
import com.apache.fastandroid.jetpack.hit.HitDemoFragment
import com.apache.fastandroid.jetpack.navigation.NavigationDemoActivity
import com.apache.fastandroid.jetpack.lifecycle.JetPackLifeCycleDemoFragment
import com.apache.fastandroid.jetpack.livedata.*
import com.apache.fastandroid.jetpack.navigation.NavigationDrawerDemoActivity2
import com.apache.fastandroid.jetpack.navigation.advance.AdvanceNavigationActivity
import com.apache.fastandroid.jetpack.navigation.drawer.NavigationDrawerDemoActivity
import com.apache.fastandroid.jetpack.viewmodel.JetPackViewModelListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class JetPackDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("LifeCycle应用", "LifeCycle应用", JetPackLifeCycleDemoFragment::class.java)
                ,ViewItemBean("LiveData", "LiveData", JetPackLiveDataListFragment::class.java)
                ,ViewItemBean("ViewModel", "ViewModel", JetPackViewModelListFragment::class.java)
                ,ViewItemBean("DataBindg", "DataBindg", DatabindingFragmentDemo::class.java)
                ,ViewItemBean("Navigation", "Navigation", activity= NavigationDemoActivity::class.java)
                ,ViewItemBean("NavigationDrawerLayout", "NavigationDrawerLayout", activity= NavigationDrawerDemoActivity::class.java)
                ,ViewItemBean("Navigation2", "左边固定显示Naviagtion", activity= NavigationDrawerDemoActivity2::class.java)
                ,ViewItemBean("AdvanceNavigation", "AdvanceNavigation", activity= AdvanceNavigationActivity::class.java)
//                ,ViewItemBean("Room", "Room", RoomDemoFragment::class.java)
                ,ViewItemBean("Room", "Room", RoomDemoFragment::class.java)
                ,ViewItemBean("Hit", "Hit", HitDemoFragment::class.java)
                ,ViewItemBean("SunFlower", "SunFlower", SunFlowerHomeViewPagerFragment::class.java,addTitleBar = false)
        )
    }
}