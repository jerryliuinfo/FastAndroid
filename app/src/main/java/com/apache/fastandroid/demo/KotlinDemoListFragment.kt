package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.kt.*
import com.apache.fastandroid.demo.kt.collection.CollectionDemoFragment
import com.apache.fastandroid.demo.kt.collection.CollectionDemoFragment2
import com.apache.fastandroid.demo.kt.delegate.KotlinDelegateFragment
import com.apache.fastandroid.demo.kt.generics.GenericDemoFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinDemoListFragment:BaseListFragment()
{
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Kotlin语法","Kotlin语法",KotlinKnowledgeFragment::class.java)
            ,ViewItemBean("Kotlin语法2","Kotlin语法2", KotlinKnowledgeFragment2::class.java)
            ,ViewItemBean("Kotlin官方文档","Kotlin官方文档", KotlinOfficalGramerFragment::class.java)
            ,ViewItemBean("类与对象","类与对象", KotlinClassAndObjectFragment::class.java)
            ,ViewItemBean("Kotlin委托","Kotlin委托",KotlinDelegateFragment::class.java)
            ,ViewItemBean("Kotlin集合","Kotlin集合", CollectionDemoFragment::class.java)
            ,ViewItemBean("Collection","Collection", CollectionDemoFragment2::class.java)
            ,ViewItemBean("MathUtil","MathUtil", MathUtilDemoFragment::class.java)
            ,ViewItemBean("Kotlin语法扩展函数","Kotlin语法扩展函数", KotlinExtensionsFragment::class.java)
            ,ViewItemBean("范型","范型", GenericDemoFragment::class.java)
            ,ViewItemBean("Kotlin热门问题","StackOverFlow最热门的8个问题", KotlinHotQuestionFragment::class.java)

        )
    }
}