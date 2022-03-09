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
                ViewItemBean("LiveData最佳实践️", "LiveData最佳实践️", LiveDataBestPracticeFragment::class.java),
                ViewItemBean("LiveDat不恰当用法","LiveDat不恰当用法", LiveDataWrongUsageFragment::class.java)
        )
    }


}