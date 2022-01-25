package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.BuildConfig
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.databinding.KtGrammerBinding
import com.apache.fastandroid.demo.kt.bean.Dog
import com.apache.fastandroid.demo.kt.bean.JavaMain
import com.apache.fastandroid.demo.kt.bean.KotlinMain
import com.apache.fastandroid.demo.kt.bean.printName
import com.kingja.loadsir.core.LoadSir
import com.microsoft.office.outlook.magnifierlib.frame.FrameCalculator
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.inlineFunction
import com.tesla.framework.ui.fragment.BaseFragment
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
class KotlinKnowledgeFragment:BaseVMFragment<KtGrammerBinding>(KtGrammerBinding::inflate) {
    companion object{
        private const val TAG = "KotlinKnowledgeFragment"
    }


    private lateinit var mFrameCalculator:FrameCalculator

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_on_each.setOnClickListener {
            onEach()
        }
        btn_method_cost.setOnClickListener {
            val costTime = measureTimeMillis {
                Thread.sleep(100)
            }
            Logger.d("costTime:${costTime}")

            println()
        }
        btn_coerceAtLeast.setOnClickListener {
            Logger.d("${3.coerceAtLeast(5)}")
        }


        mFrameCalculator = FrameCalculator{
            Logger.d("frame: ${it}")
        }
        btn_high_order_function.setOnClickListener {
            highOrderFunction()
        }
        btn_clazz_paramter.setOnClickListener {
            passJavaClass(JavaMain::class.java)
            passKotlinClass(KotlinMain::class)
            println(KotlinMain.instance.hashCode())

        }
        btn_java_kotlin_call_each_other.setOnClickListener {
            format("")
        }
        btn_nested_function.setOnClickListener {
            nestedFunction()
        }

        btn_expand_function.setOnClickListener {
            val file = File(requireContext().filesDir, "test.txt")
            file.writeText("hello:${Random.nextInt(10)}")

            println(file.readText())

            //给LoadSir 增加一个方法
            if (LoadSir.Builder().debug()){

            }
        }

        btn_expand_function2.setOnClickListener {
            /**
             * 输出的 是 animal，而不是 dog，因为kotlin 的扩展方法是静态地给一个类添加方法，
             * 是不具备动态运行时的多态效应,扩展函数会被编译成一个静态函数
             */
            Dog().printName(Dog())
        }
        btn_lambda.setOnClickListener {
            lambdaUsage()
        }

        btn_inline.setOnClickListener {
            inlineUsage()
            nonInlineUsage()
        }

        //关键字冲突 用 反引号转义
        println(JavaMain.`in`)

        defaultParamFun("hello")
        defaultParamFun()

    }

    private fun nonInlineUsage() {
        onlyIf(true){
            println("no inline 打印日志")
        }
    }

    private fun inlineUsage() {
        onlyIf2(true){
            println("inline 打印日志")
        }
    }

    private fun highOrderFunction() {
        //这里加了两个 ：： 就变成了对象，一个函数类型的对象，kotlin 中函数可以作为参数传递的本质是：函数可以作为对象存在
        val result1 =  a(::b)
        val result2 = ::b
        println( "result1:$result1, result2:$result2")

        onlyIf(true){
            println("打印日志")
        }


        val runnable = Runnable{
            println("Runnable run")
        }

//        val function: () -> Unit
        //加了两个 ：： 就变成了对象
       val function = runnable::run

        onlyIf(true,function)
    }




    fun onlyIf(debug:Boolean, block:() -> Unit){
        if (debug){
            block()
        }
    }

    inline fun onlyIf2(debug:Boolean, block:() -> Unit){
        if (debug){
            block()
        }
    }


    private fun lambdaUsage() {
       echo.invoke("Zhangtao")
       echo("Jerry")


    }

    val echo = { name:String ->
        println(name)
    }

    val lambda = {

    }

    /**
     * 用途：某些条件下触发地柜的函数，不希望被外部函数访问到的函数
     * 一般情况下不推荐使用，因为会降低代码可读性，一般用于特殊场景，比如递归,不希望被外部函数调用
     */
    private fun nestedFunction(){
        val str = "Hello world"
        fun say(count: Int = 5){
            println(str)
            if (count > 0){
                say(count - 1)
            }
        }
        say()
    }



    private fun defaultParamFun(msg:String = "默认值"){
        println(msg)
    }

    private fun format(str:String){
        val fmt1 = JavaMain.format(str)
        //上面一行虽然不会报错 但是调用 fmt1.length 会报错
//        println(fmt1.length)
        //这里会报错， 因为返回的是null 但是接收的是 非空
        val fmt2:String = JavaMain.format(str)
        val fmt3:String? = JavaMain.format(str)
    }


    fun a(function: (Int) -> String):String{
        return function(1)
    }

    fun b(param:Int):String{
        return param.toString()
    }


    private fun onEach(vararg numbs: Int):String{
       mutableSetOf("aa","bb","cc").onEach {
           if (it == "bb"){
               return@onEach
           }
           NLog.d(TAG, it)
       }
        return "Hello"
    }


    /**
     * 传入的是 java 类型的类
     */
    private fun passJavaClass(clz: Class<JavaMain>) {
        println(clz.simpleName)
    }


    /**
     * 传递的是 Kotlin类型的类
     */
    private fun passKotlinClass(clz: KClass<KotlinMain>) {
        println(clz.simpleName)
    }

    fun File.readTextha(charset: Charset= Charset.defaultCharset()):String = readBytes().toString(charset)
    fun LoadSir.Builder.debug() = BuildConfig.DEBUG

}