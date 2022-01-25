package com.apache.fastandroid.demo.jetpack

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.databinding.DatabindingFragmentDemo
import com.apache.fastandroid.demo.navigation.NavigationDemoActivity
import com.apache.fastandroid.jetpack.lifecycle.JetPackLifeCycleDemoFragment
import com.apache.fastandroid.jetpack.livedata.*
import com.apache.fastandroid.jetpack.viewmodel.JetPackViewModelListFragment
import com.apache.fastandroid.jetpack.workmanager.WorkManagerListDemo

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
                ,ViewItemBean("Navigation", "Navigation", activity=NavigationDemoActivity::class.java)
//                ,ViewItemBean("Room", "Room", RoomDemoFragment::class.java)
                ,ViewItemBean("WorkManager", "WorkManager", WorkManagerListDemo::class.java)
                ,ViewItemBean("ViewModelFactory", "ViewModelFactory", WorkManagerListDemo::class.java)
        )
    }
}