package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.kt.CodeStandardsFragment
import com.apache.fastandroid.demo.kt.KotlinExtensionsFragment
import com.apache.fastandroid.demo.kt.KotlinHotQuestionFragment
import com.apache.fastandroid.demo.kt.KotlinKnowledgeFragment
import com.apache.fastandroid.demo.kt.KotlinKnowledgeFragment2
import com.apache.fastandroid.demo.kt.KotlinOfficalGramerFragment
import com.apache.fastandroid.demo.kt.collection.CollectionDemoFragment
import com.apache.fastandroid.demo.kt.delegate.KotlinDelegateFragment
import com.apache.fastandroid.demo.kt.generics.GenericDemoFragment
import com.apache.fastandroid.demo.kt.lambda.LambdaDemoFragment
import com.apache.fastandroid.demo.kt.practice.JuejinKtDemoListFragment
import com.apache.fastandroid.demo.kt.practice.KotlinPracticeDemoFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinDemoListFragment:BaseListFragment()
{
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(

            ViewItemBean("Kotlin官方文档","Kotlin官方文档",KotlinOfficalGramerFragment::class.java),
            ViewItemBean("Kotlin语法","Kotlin语法",KotlinKnowledgeFragment::class.java)
            ,ViewItemBean("Lambda","Lambda", LambdaDemoFragment::class.java)
            ,ViewItemBean("Kotlin语法2","Kotlin语法2", KotlinKnowledgeFragment2::class.java)
            ,ViewItemBean("编码规范","编码规范", CodeStandardsFragment::class.java)
            ,ViewItemBean("Kotlin委托","Kotlin委托",KotlinDelegateFragment::class.java)
            ,ViewItemBean("Kotlin 开发实践","基于Kotlin的 Android app开发实践",JuejinKtDemoListFragment::class.java)
            ,ViewItemBean("Kotlin 基础","Kotlin 基础",JuejinKtDemoListFragment::class.java)
            ,ViewItemBean("Ktx 扩展库","Ktx 扩展库", KotlinExtensionsFragment::class.java)


            ,ViewItemBean("Kotlin集合","Kotlin集合", CollectionDemoFragment::class.java)
            ,ViewItemBean("范型","范型", GenericDemoFragment::class.java)
            ,ViewItemBean("Kotlin热门问题","StackOverFlow最热门的8个问题", KotlinHotQuestionFragment::class.java)
            ,ViewItemBean("Kotlin 编程实践","Kotlin 编程实践", KotlinPracticeDemoFragment::class.java)

        )
    }
}