package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.kt.KotlinExtensionsFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineDemoFragment2
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
            ,ViewItemBean("Kotlin语法扩展函数","Kotlin语法扩展函数", KotlinExtensionsFragment::class.java)
            ,ViewItemBean("协程","协程", CoroutineDemoFragment::class.java)
            ,ViewItemBean("协程2","协程2", CoroutineDemoFragment2::class.java)
        )
    }
}