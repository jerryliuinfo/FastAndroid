package com.apache.fastandroid.jetpack.livedata

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackLiveDataListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("LiveData基本用法", "LiveData", LiveDataBasicFragment::class.java),
                ViewItemBean("LiveData多页面共享数据", "LiveData", SingleTonLiveDataFragment::class.java),
                ViewItemBean("LiveData单例", "LiveData单例", SingleTonLiveData3Fragment::class.java),


                ViewItemBean("LiveData粘性", "LiveData", LiveDataBasicFragment::class.java),
                ViewItemBean("LiveData TransformMap", "LiveData TransformMap", LiveDataTransformMapFragment::class.java),
                ViewItemBean("LiveDataBus", "LiveData", LiveDataBusFragment::class.java)
        )
    }


}