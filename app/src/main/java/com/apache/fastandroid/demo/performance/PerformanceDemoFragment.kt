package com.apache.fastandroid.demo.performance

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment
import com.apache.fastandroid.demo.performance.practice.AnrWatchDogDemoFragment
import com.apache.fastandroid.jetpack.lifecycle.JetPackLifeCycleFragment
import com.apache.fastandroid.jetpack.lifecycle.traditional.TraditionalLifeCycleFragment
import com.apache.fastandroid.jetpack.livedata.LiveDataFragment
import com.apache.fastandroid.jetpack.livedataviewmodel.LiveDataViewModelFragment
import com.apache.fastandroid.jetpack.viewmodel.ViewModelFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class PerformanceDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("卡顿监控", "AnrWatchDog", AnrWatchDogDemoFragment::class.java)
        )
    }
}