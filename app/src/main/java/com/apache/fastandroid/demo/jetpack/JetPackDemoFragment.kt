package com.apache.fastandroid.demo.jetpack

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.jetpack.lifecycle.JetPackLifeCycleListFragment
import com.apache.fastandroid.jetpack.livedata.*
import com.apache.fastandroid.jetpack.viewmodel.JetPackViewModelListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class JetPackDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("LifeCycle", "LifeCycle", JetPackLifeCycleListFragment::class.java)
                ,ViewItemBean("LiveData", "LiveData", JetPackLiveDataListFragment::class.java)
                ,ViewItemBean("ViewModel", "ViewModel", JetPackViewModelListFragment::class.java)
        )
    }
}