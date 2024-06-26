package com.apache.fastandroid.jetpack

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.databinding.DatabindingDemoFragment
import com.apache.fastandroid.demo.paging.article.ui.PagingArticleDemoActivity
import com.apache.fastandroid.demo.paging.poster.ui.PagingPosterDemoActivity
import com.apache.fastandroid.demo.room.RoomDemoFragment
import com.apache.fastandroid.jetpack.coroutine.CoroutineDemoListFragment
import com.apache.fastandroid.jetpack.flow.FlowDemoListFragment
import com.apache.fastandroid.jetpack.hit.HitDemoActivity
import com.apache.fastandroid.jetpack.lifecycle.LifeCycleDemoListFragment
import com.apache.fastandroid.jetpack.livedata.JetPackLiveDataListFragment
import com.apache.fastandroid.jetpack.navigation.NavigationDemoActivity
import com.apache.fastandroid.jetpack.navigation.NavigationDrawerDemoActivity2
import com.apache.fastandroid.jetpack.navigation.advance.AdvanceNavigationActivity
import com.apache.fastandroid.jetpack.navigation.drawer.NavigationDrawerDemoActivity
import com.apache.fastandroid.jetpack.viewmodel.ViewModelDemoFragment
import com.apache.fastandroid.jetpack.workmanager.WorkManagerDemoFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class JetPackDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("LifeCycle应用", "LifeCycle应用", LifeCycleDemoListFragment::class.java)
                ,ViewItemBean("LiveData", "LiveData", JetPackLiveDataListFragment::class.java)
                ,ViewItemBean("ViewModel", "ViewModel", ViewModelDemoFragment::class.java)
                ,ViewItemBean("DataBindg", "DataBindg", DatabindingDemoFragment::class.java)
                ,ViewItemBean("Navigation", "Navigation", activity= NavigationDemoActivity::class.java)
                ,ViewItemBean("NavigationDrawerLayout", "NavigationDrawerLayout", activity= NavigationDrawerDemoActivity::class.java)
                ,ViewItemBean("Navigation2", "左边固定显示Naviagtion", activity= NavigationDrawerDemoActivity2::class.java)
                ,ViewItemBean("AdvanceNavigation", "AdvanceNavigation", activity= AdvanceNavigationActivity::class.java)
                ,ViewItemBean("Room", "Room", RoomDemoFragment::class.java)
                ,ViewItemBean("Coroutine", "Coroutine", CoroutineDemoListFragment::class.java)
                ,ViewItemBean("Flow", "Flow", FlowDemoListFragment::class.java)

                ,ViewItemBean("Hit", "Hit", activity=HitDemoActivity::class.java)
                ,ViewItemBean("WorkManager", "WorkManager", WorkManagerDemoFragment::class.java)
                ,ViewItemBean("Paging", "Paging",  activity = PagingArticleDemoActivity::class.java)
                ,ViewItemBean("Paging", "Paging用法2",  activity = PagingPosterDemoActivity::class.java)

        )
    }
}