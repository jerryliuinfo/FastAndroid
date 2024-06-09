package com.apache.fastandroid.jetpack.livedata

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2020/10/31.
 * done
 */
class JetPackLiveDataListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("LiveData基本用法", "LiveData基本用法", LiveDataBasicFragment::class.java),
                ViewItemBean("LiveData最佳实践️", "手动刷新ui", LiveDataBestPracticeFragment::class.java),
                ViewItemBean("LiveData最佳实践2️", "ViewModel注入到Layout文件中", LiveDataBestPracticeFragment2::class.java),
                ViewItemBean("LiveDat不恰当用法","LiveDat不恰当用法", LiveDataWrongUsageFragment::class.java),
                ViewItemBean("LiveDat高级用法","LiveDat高级用法", LiveDataSensorUsageFragment::class.java),
                ViewItemBean("LiveDat 和加载视图绑定","LiveDat 和加载视图绑定", LiveDataLoaderFragment::class.java),
        )
    }


}