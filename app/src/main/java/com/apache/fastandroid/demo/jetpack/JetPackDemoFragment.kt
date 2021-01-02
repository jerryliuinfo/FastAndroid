package com.apache.fastandroid.demo.jetpack

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.jetpack.lifecycle.JetPackLifeCycleFragment
import com.apache.fastandroid.jetpack.lifecycle.traditional.TraditionalLifeCycleFragment
import com.apache.fastandroid.jetpack.livedata.LiveDataBusFragment
import com.apache.fastandroid.jetpack.livedata.LiveDataFragment
import com.apache.fastandroid.jetpack.livedata.SingleTonLiveDataFragment
import com.apache.fastandroid.jetpack.livedataviewmodel.LiveDataViewModelFragment
import com.apache.fastandroid.jetpack.viewmodel.ViewModelFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class JetPackDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("Normal", "传统生命周期监听", TraditionalLifeCycleFragment::class.java),
                ViewItemBean("JetPack", "LifeCycle", JetPackLifeCycleFragment::class.java),
                ViewItemBean("JetPack", "ViewModel", ViewModelFragment::class.java),

                ViewItemBean("LiveData基本用法", "LiveData", LiveDataFragment::class.java),
                ViewItemBean("LiveData多页面共享数据", "LiveData", SingleTonLiveDataFragment::class.java),
                ViewItemBean("LiveData粘性", "LiveData", LiveDataFragment::class.java),
                ViewItemBean("LiveDataBus", "LiveData", LiveDataBusFragment::class.java),


                ViewItemBean("JetPack", "LiveDataViewModel", LiveDataViewModelFragment::class.java),
                ViewItemBean("JetPack", "LiveDataViewModel", LiveDataViewModelFragment::class.java)
        )
    }
}