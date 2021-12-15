package com.apache.fastandroid.demo.component

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/12/13.
 */
class LoadSirDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf<ViewItemBean>(
            ViewItemBean()
        )
    }
}