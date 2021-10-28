package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.kt.CouroutineDemoFragment
import com.apache.fastandroid.demo.kt.KotlinKnowledgeFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinDemoListFragment:BaseListFragment()
{
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Kotlin语法","Kotlin语法",KotlinKnowledgeFragment::class.java)
            ,ViewItemBean("协程","协程",CouroutineDemoFragment::class.java)
        )
    }
}