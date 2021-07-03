package com.apache.fastandroid.jetpack.viewmodel

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackViewModelListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("ViewModel", "ViewModel横竖屏切换恢复数据", ViewModelRestoreDataFragment::class.java)
        )
    }


}