package com.apache.fastandroid.demo.jni

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2022/6/5.
 * https://github.com/android/ndk-samples
 */
class JniDemoListFragment:BaseListFragment(){
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Java 调用 C", "Java 调用 C", JniJavaCallCDemoFragment::class.java)
            ,ViewItemBean("C 调用 Java", "C 调用 Java", JniCCallJavaDemoFragment::class.java)

        )
    }


}