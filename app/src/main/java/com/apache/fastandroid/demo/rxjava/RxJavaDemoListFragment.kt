package com.apache.fastandroid.demo.rxjava

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment


/**
 * Created by Jerry on 2021/9/9.
 */
class RxJavaDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("扔物线", "扔物线",RxJavaDemoFragment::class.java),
            ViewItemBean("扔物线", "南尘",RxJavaDemoFragment2::class.java)
        )
    }

}