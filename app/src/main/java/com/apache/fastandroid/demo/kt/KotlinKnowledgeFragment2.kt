package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.BuildConfig
import com.apache.fastandroid.databinding.KtGrammer2Binding
import com.apache.fastandroid.databinding.KtGrammerBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.bean.Dog
import com.apache.fastandroid.demo.kt.bean.JavaMain
import com.apache.fastandroid.demo.kt.bean.KotlinMain
import com.apache.fastandroid.demo.kt.bean.printName
import com.apache.fastandroid.demo.kt.sealed.PlayerUI
import com.apache.fastandroid.demo.kt.sealed.PlayerViewType
import com.apache.fastandroid.demo.kt.sealed.User
import com.kingja.loadsir.core.LoadSir
import com.microsoft.office.outlook.magnifierlib.frame.FrameCalculator
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVMFragment
import kotlinx.android.synthetic.main.kt_grammer.*
import java.io.File
import java.nio.charset.Charset
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinKnowledgeFragment2:BaseVMFragment<KtGrammer2Binding>(KtGrammer2Binding::inflate) {
    companion object{
        private const val TAG = "KotlinKnowledgeFragment"
    }



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnLetRun.setOnClickListener {
            testLetRun()
        }

        viewBinding.btnAlsoApply.setOnClickListener {
            testAlsoApply()
        }
        viewBinding.btnWith.setOnClickListener {
            testWith()
        }

        viewBinding.btnCommonOperator.setOnClickListener {
            commonOperation()
        }

        viewBinding.btnOperatorOverload.setOnClickListener {
            testOperatorOverload()
        }
        viewBinding.btnZhongzui.setOnClickListener {
            zhongZuiExpression()
        }
        viewBinding.btnTypeAlias.setOnClickListener {
            typeAliasUsage()
        }

    }

    /**
     *  @SinceKotlin("1.1") public actual typealias LinkedHashMap<K, V> = java.util.LinkedHashMap<K, V> kotlin中的HashMap 就是映射的java的HashMap
     *  使用 typealias的好处就是 将来替换 HashMap的实现时就直接替换 kt包中的实现就行，调用者不需要变化
     */
    private fun typeAliasUsage() {
        val a:File = A("build")

        val map = HashMap<String,String>()
    }

    private fun zhongZuiExpression() {
        var result1 = 5.vs(6)
        var result2 = 6.vs(5)
        println("result1:$result1, result2:$result2")
    }

    infix fun Int.vs(num:Int):Boolean = this - num < 0


    /**
     * 运算符重载
     */
    private fun testOperatorOverload(){
        for (i in 1..100 step 20){
            println("testOperatorOverload $i")
        }
    }

    /**
     * let 和 run
     * 相同点:都是返回闭包，而不是调用者本身
     * 区别是 let 的闭包中是可以参数的，这个参数为 调用者, 而run 是通过this来获取调用者的对象
     */
    private fun testLetRun(){
        val user = UserBean("Tom")
        val letResult =  user.let {
            println("let:${it.javaClass}")
        }

        val runResult =  user.run {
            println("let:${this.javaClass}")
        }
        println(letResult)
        println(runResult)
    }

    /**
     * also 和 apply
     * 相同点:都是返回调用者本身,而不是闭包
     * 区别是 also 的闭包中是带参数的，apply 的闭包没有带参数，通过this来访问
     */
    private fun testAlsoApply(){
        val user = UserBean("Tom")
        val letResult =  user.also {
            println("also:${it.javaClass}")
        }

         user.apply {
            println("apply:${this.javaClass}")
        }.name = "jerry"
        println(letResult)
        println(user)

    }

    /**
     * with 比较特殊，不是以扩展方法的形式存在的，而是一个顶级函数
     * 通常用语对view做一些设置，例如color, size等
     */
    private fun testWith(){
        val user = UserBean("Tom")
        with(user){
            this.name = "with"
        }
        println("testWith:${user.name}")

    }

    private fun commonOperation(){
        val list = arrayListOf<String>( "Tom","Jerry","KittyNan", "Hansomboy")
        val singleOrNullResult = list.singleOrNull {
            it.length > 5
        }
        println("firstOrNull:${list.lastOrNull()}, singleOrNullResult:$singleOrNullResult")

        //any 判断集合中是否有指定的元素
        val anyResult = list.any {
            it.length > 5
        }

        //判断集合中的是否都满足条件
        val allResult =  list.all {
            it.length > 5
        }
        val noneResult =  list.none {
            it.length > 5
        }

        val countResult =  list.count {
            it.length > 5
        }
        val reduceResult =  list.reduce { acc, s ->
            return@reduce "$acc $s"
        }
        Logger.d("singleOrNullResult:$singleOrNullResult,anyResult:$anyResult, allResult:$allResult, " +
                "noneResult:$noneResult,countResult:$countResult,reduceResult:$reduceResult")
    }

}

//用 A 来代表 File
typealias A = File