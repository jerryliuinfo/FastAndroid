package com.apache.fastandroid.demo.rxjava

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment


/**
 * Created by Jerry on 2021/9/9.
 */
class RxJavaDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("扔物线", "扔物线",RxJavaDemoFragment::class.java)
            ,ViewItemBean("RxJava操作符", "RxJava操作符",RxJava3OperatorDemoFragment::class.java)
            ,ViewItemBean("RxJava应用实战", "RxJava应用实战",RxJava3PracticeFragment::class.java)
        )
    }

}