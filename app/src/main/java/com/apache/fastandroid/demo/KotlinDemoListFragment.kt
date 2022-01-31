package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.kt.coroutine.CouroutineDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CouroutineDemoFragment2
import com.apache.fastandroid.demo.kt.KotlinKnowledgeFragment
import com.apache.fastandroid.demo.kt.KotlinKnowledgeFragment2

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinDemoListFragment:BaseListFragment()
{
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Kotlin语法","Kotlin语法",KotlinKnowledgeFragment::class.java)
            ,ViewItemBean("Kotlin语法2","Kotlin语法2", KotlinKnowledgeFragment2::class.java)
            ,ViewItemBean("协程","协程", CouroutineDemoFragment::class.java)
            ,ViewItemBean("协程2","协程2", CouroutineDemoFragment2::class.java)
        )
    }
}