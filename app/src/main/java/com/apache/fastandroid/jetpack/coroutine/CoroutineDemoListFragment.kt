package com.apache.fastandroid.jetpack.coroutine

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.kt.coroutine.BennyHuoCoroutineDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineDemoFragment2

/**
 * Created by Jerry on 2020/10/31.
 */
class CoroutineDemoListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("协程","协程", CoroutineDemoFragment::class.java)
            ,ViewItemBean("协程2","协程2", CoroutineDemoFragment2::class.java)
            ,ViewItemBean("协程3","BennyHuo", BennyHuoCoroutineDemoFragment::class.java)
        )
    }


}