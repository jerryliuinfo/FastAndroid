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


                ViewItemBean("LiveData粘性", "粘性事件", LiveDataStickEventFragment::class.java),
                ViewItemBean("TransformMap", "在将LiveData对象分派给观察者之前对存储在其中的值进行更改", LiveDataTransformMapFragment::class.java),
                ViewItemBean("TransformSwitchMap", "将一个LiveData对象转换成另外一个LiveData对象", LiveDataTransformSwitchMapFragment::class.java),
                ViewItemBean("TransformSwitchMap2", "LiveData TransformSwitchMap2", LiveDataTransformSwitchMapFragment2::class.java),
                ViewItemBean("MediaLiveData", "MediaLiveData", MediatorLiveDataFragment::class.java),
                ViewItemBean("MediatorLiveData", "合并多个 LiveData 源", LiveDataTransformSwitchMapFragment2::class.java),
                ViewItemBean("LiveDataBus", "基于LiveData的事件总线", LiveDataBusFragment::class.java),
                ViewItemBean("NetworkLiveData", "基于LiveData的网络监听", LiveDataNetworkObserverFragment::class.java)
        )
    }


}