package com.apache.fastandroid.demo.kt.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentJuejinKtBinding
import com.apache.fastandroid.demo.kt.extends.PingMsg
import com.apache.fastandroid.demo.kt.func.JvmOverloadsDemo
import com.apache.fastandroid.demo.kt.hignorder.FuncAsParamDemo
import com.tesla.framework.component.logger.Logger

import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class JuejinKtDemoListFragment :
    BaseBindingFragment<FragmentJuejinKtBinding>(FragmentJuejinKtBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnJvmOverloads.setOnClickListener {
            jvmOverloadsUsage()
        }

        mBinding.btnDefaultParam.setOnClickListener {
            defaultParamUsage()
        }

        mBinding.btnVarargs.setOnClickListener {
            varagsUsage()
        }

        mBinding.btnUnitNothing.setOnClickListener {
            unitNothingUsage()
        }

        mBinding.btnInit.setOnClickListener {
            initUsage()
        }

        mBinding.btnNestedInnerClass.setOnClickListener {
            nestedInnerClassUsage()
        }

        mBinding.btnExtendsDataclass.setOnClickListener {
            extendsDataClassUsage()
        }

        mBinding.btnFuncType.setOnClickListener {
            funcTypeUsage()
        }

        mBinding.btnFuncAsTypes.setOnClickListener {
            funcAsParams()
        }

        mBinding.btnFuncAsReturn.setOnClickListener {
            funcAsReturn()
        }



    }


    /**
     * 函数可以作为其他函数的参数
     */
    private fun funcAsParams() {
        val demo = FuncAsParamDemo()
        val identityResult = demo.sum(1, 2, demo.identity)
        val squareResult = demo.sum(1, 2, demo.square)
        val cubeResult = demo.sum(1, 2, demo.cube)
        val identityResult2 = demo.sum(1, 2){
            it
        }
        println("identityResult:$identityResult, identityResult2:$identityResult2,squareResult:$squareResult, cubeResult:$cubeResult")
    }


    /**
     * 函数可以作为其他函数的返回值
     */
    private fun funcAsReturn() {
        val demo = FuncAsParamDemo()
        //这个返回值是一个函数
        val identityFunc = demo.sum("identity")

        val result = identityFunc(1,2)
        println("funcAsReturn result:$result")


    }


    private fun funcTypeUsage() {
        val sum1 = sum(1, 2)
        val sum2 = sum2(1, 2)
        val sum3 = sum.invoke(1, 2)
        println("funcTypeUsage sum1:$sum1, sum2:$sum2, sum3:$sum3")
    }

    //函数类型 函数被赋值给变量， 变量 sum 是函数类型，它接收两个 int 类型的参数，返回 一个 int 类型的参数
    val sum = { x: Int, y: Int ->
        x + y
    }

    val sum2: (Int, Int) -> Int = { x, y -> x + y }


    /**
     *  data class 不能被继承，如果想要在属于同一类型的多个数据类之间共享属性、方法呢，可以考虑使用抽象类或者接口
     *
     */
    private fun extendsDataClassUsage() {
        val ping = PingMsg("pingping")
        println("ping action:${ping.action}")

        val pang = PingMsg("pangpang")
        println("pang action:${pang.action}")
    }


    private fun nestedInnerClassUsage() {
        val nested = NestedInnerClassDemo.Nested()
        nested.test()

        val inner = NestedInnerClassDemo().Inner()
        inner.test()
    }

    private fun initUsage() {
        val obj = ConstructDemo("Hello", "world")
        obj.foo()
    }

    class ConstructDemo(str: String) {
        init {
            println("init1 str:$str....")
        }

        init {
            println("init2 str:$str....")
        }

        constructor(str1: String, str2: String) : this(str1) {
            println("$str1 $str2")
        }

        fun foo() {
            println("this is foo function")
        }
    }

    private fun unitNothingUsage() {
        // 返回 Unit, Unit 是一种类型，会返回 Unit 的单例， 而Nothing 则永远不会返回任何东西
        Logger.d(returnUnit())
        Logger.d(returnNothing())

    }

    private fun varagsUsage() {
        val list = toList("java", "kotlin", 1)
        Logger.d("varagsUsage list:$list")

        //也可已传递数组
        val array = arrayOf("java", "kotlin", "groovy")
        val list2 = toList(array)
        Logger.d("varagsUsage list2:$list2")

    }


    private fun returnUnit() {

    }

    private fun returnNothing(): Nothing {
        while (true) {
            println("dosomething")
        }
    }

    /**
     * 默认参数用法
     */
    private fun defaultParamUsage() {
        sum(1, 2, 3)
        sum(1, z = 4)
    }

    private fun sum(x: Int, y: Int = 0, z: Int = 1): Int {
        return x + y + z
    }

    private fun <T> toList(vararg items: T): List<T> {
        val result = ArrayList<T>()

        for (item in items) {
            result.add(item)
        }
        return result
    }


    /**
     * java 调用 kotlin 的默认默认参数方法
     * 由于使用了默认参数后，可以避免重载。但是 java 却无法调用，因为对 java 而言只有一个方法是可见的，
     * 它是所有参数都存在的完整签名参数，如果希望也向 java 调用者暴露多个重载，可以使用 JvmOverloads
     */
    private fun jvmOverloadsUsage() {
        JvmOverloadsDemo.callJavaDefaultParamFunc()
    }
}