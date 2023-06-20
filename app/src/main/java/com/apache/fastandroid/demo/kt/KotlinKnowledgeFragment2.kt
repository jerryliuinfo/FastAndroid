package com.apache.fastandroid.demo.kt

import android.app.ActivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.KtGrammer2Binding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.bean.KotlinMain
import com.apache.fastandroid.demo.kt.genericity.*
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.fromJson2
import com.tesla.framework.kt.getMySystemService
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.io.File

/**
 * Created by Jerry on 2021/10/18.
 * done
 */
class KotlinKnowledgeFragment2:BaseBindingFragment<KtGrammer2Binding>(KtGrammer2Binding::inflate) {
    companion object{
        private const val TAG = "KotlinKnowledgeFragment2"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnLet.setOnClickListener {
            letUsage()
        }

        mBinding.btnWith.setOnClickListener {
            withUsage()
        }

        mBinding.btnRun.setOnClickListener {
            runUsage()
        }

        mBinding.btnApply.setOnClickListener {
            applyUsage()
        }

        mBinding.btnAlso.setOnClickListener {
            alsoUsage()
        }

        mBinding.btnOperatorOverload.setOnClickListener {
            testOperatorOverload()
        }
        mBinding.btnZhongzui.setOnClickListener {
            zhongZuiExpression()
        }
        mBinding.btnTypeAlias.setOnClickListener {
            typeAliasUsage()
        }

        mBinding.btnGetterSetter.setOnClickListener {
           getSetUsage()
        }

        mBinding.btnLambdaInterrupt.setOnClickListener {
            lambdaInterrupt()
        }
        mBinding.btnNeilian.setOnClickListener {
            inlineUsage2()
        }

        mBinding.btnNoInline.setOnClickListener {
            noInlineUsage()
        }


        mBinding.btnNonCrossInline.setOnClickListener {
            guide()
        }

        mBinding.btnCrossInline.setOnClickListener {
            crossInlineUsage()
            guide2()
        }

        mBinding.btnAnonymous.setOnClickListener {
            anonymousFun()
        }

        mBinding.btnKtGenericity.setOnClickListener {
            val genericity = KtGenericity<GenericityAImpl>()
            genericity.add(GenericityAImpl())
        }

        mBinding.btnKtRealGenericity.setOnClickListener {

            val userBean = UserBean("jerry",10)
            var json = GsonUtils.toJson(userBean)

            //Java 泛型
            JavaGeneric.fromJson(json, UserBean::class.java)
            //Kotlin 泛型
            val userBean2 = Gson().fromJson2<UserBean>(json)
            println("json: $json, userBean2 name:${userBean2.name}, age:${userBean2.age}")

            val manage:ActivityManager? = activity?.getMySystemService<ActivityManager>()
        }

        mBinding.btnKtClassRealGenericity.setOnClickListener {

            val p1 = GenericView.invoke<GenericView.Presenter>().presenter
            //p2 其实是通过 P1 的形式创建的
            val p2 = GenericView<GenericView.Presenter>().presenter

            println("p1:$p1, p2:$p2")

            p1.test()
            p2.test()
        }

        mBinding.btnEvisOperator.setOnClickListener {
            evisOperatorUsage()
        }

    }



    private fun evisOperatorUsage() {
        arguments?.getString("key")?.takeIf { it.isNotEmpty() } ?: kotlin.run {
            toast("url为空")
        }
    }


    private fun anonymousFun(){
        val test = fun (){
            println("uitest")
            //这里只会 return 调匿名函数本身
            return
        }
        test.invoke()
    }



    /**
     *
     * crossinline 用于避免非本地返回。
     * crossinline 不允许 inline 的 Lambda 中断外部函数执行,
     * 不会输出 hello2
     */
    private fun crossInlineUsage() {
        crossinlineFun {
            println("test1")
            return@crossinlineFun
            println("hello1")
        }
        println("hello2")
    }


    /**
     * 反编译看到的是:
     *  public final void guide() {
            String var1 = "guide start";

            String var4 = "teach";
       }

       不会输出 "guide end", 因为 lambda 中 return掉了，也称 允许本地返回
     */
    fun guide(){
        println("guide start")
        teach {
            print("teach")
            return
        }
        println("guide end")

    }


    /**
     * crossInlineTeach 的lambda 中加上 crossInline 之后就不能return 掉了
     *
     * public final void guide2() {
        String var1 = "guide start";
        String var4 = "teach";
        var1 = "guide end";
    }
     *
     */
    fun guide2(){
        println("guide start")
        crossInlineTeach {
            print("teach")
                //return is not allowed here
//            return
        }
        println("guide end")
    }

    fun guide3(){
        println("guide start")
        crossInlineTeach {
            print("teach")
            //return is not allowed here
            return@crossInlineTeach
        }
        println("guide end")
    }


    inline fun teach(abc:() -> Unit){
        abc()
    }

    inline fun crossInlineTeach(crossinline abc:() -> Unit){
        abc()
    }


    /**
     *  内部 lambda 是不允许中断外部函数执行的,所以 会打印出下面的hello
     */
    private fun lambdaInterrupt(){
        lambdaFunc {
            println("test1")
            return@lambdaFunc
        }
        lambdaFunc("Hello"){
            it.length
        }
    }

    private fun lambdaFunc(l: () -> Unit){
        l.invoke()
    }

    private fun lambdaFunc(param:String,l: (String) -> Int){
        val length = l.invoke(param)
        Logger.d("lambdaFunc length:$length")
    }

    private inline fun inlineFun(block: () -> Unit){
        block.invoke()
    }

    private inline fun crossinlineFun(crossinline block: () -> Unit){
        block.invoke()
    }


    /**
     * inline 的 lambda 可以中断外部函数调用, 因此不会输出后面的 hello
     */
    private  fun inlineUsage2(){
        inlineFun {
            println("test2")
            return
        }
        println("hello")
    }

    /**
     * 反编译结果
     *
     * public void guide() {
        System.out.print("guide start");
        System.out.print("teach abc");
        teach(new Function() {
            @Override
            public void invoke() {
            System.out.print("teach xyz");
            }
        });
        System.out.print("guide end");
    }
     */
    private fun noInlineUsage() {
        print("guide start")
        teach({
            print("teach abc")
        }, {
            print("teach xyz")
        })
        print("guide end")
    }

    /**
     * 第一个参数 允许inline，第 2 个lambda 参数不 inline
     * @param abc Function0<Unit>
     * @param xyz Function0<Unit>
     */
    inline fun teach(abc: () -> Unit, noinline xyz: () -> Unit) {
        abc()
        xyz()
    }




    private fun getSetUsage(){
        println(string)
        string = "world"
        println(string)

        println("msg:$msg")
    }

    var string:String ?= null
        get() {
            return field + "get"
        }
        set(value) {
            field = "$value set"
        }

    val msg:String ?= null
        get() {
            return "$field, hello"
        }

    /**
     *  @SinceKotlin("1.1") public actual typealias LinkedHashMap<K, V> = java.util.LinkedHashMap<K, V> kotlin中的HashMap 就是映射的java的HashMap
     *  使用 typealias的好处就是 将来替换 HashMap的实现时就直接替换 kt包中的实现就行，调用者不需要变化
     */
    private fun typeAliasUsage() {
        val a:File = A("build")
        trainUsers(listOf())


        val map = HashMap<String,String>()
    }

    fun trainUsers(userList: UserList){

    }

    /**
     * 中缀表达式
     */
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
        //..  重载了 rangeTo
        for (i in 1..100 step 20){
            println("testOperatorOverload $i")
        }
    }


    private fun getTextView():TextView?{
        return null
    }

    /**
     * 特点：
     *   1. 函数体内使用 it 指代当前对象
     *   2. 返回值为函数块的最后一行代码或指定 return 表达式
     *   3. 可以在调用 let 前判空处理
     *
     *  适用的场景
     *   1. 适用 let 函数处理需要针对一个可 null 的对象统一做判空处理(常用)
     *   2. 需要去明确一个变量所处特定的作用于范围内可适用
     */
    private fun letUsage(){
        getTextView()?.let {
            it.setTextColor(requireContext().getColor(R.color.red_100))
            it.text = "Hello"
        }


        //通过 let 改变返回值来做链式调用
        val origin = "abc"
        origin.let {
            println("The origin string is $it")
            it.reversed()
        }.let {
            println("The reversed string is $it")
            it.length
        }.let {
            println("The length of the string is $it")
        }
    }

    /**
     * with 比较特殊，不是以扩展方法的形式存在的，而是一个顶级函数
     * 特点：
     *    1. 在函数块内可以通过 this 指代当前对象
     *    2. 返回值为函数块的最后一行代码或指定 return 表达式
     *
     * 适用场景:
     *  适用于调用同一个了诶的多个方法时，可以省去类名重复，直接调用类的方法即可，常用
     *  于 RecycleView 的 onBindViewHolder 方法中将 Model 属性映射到 UI 中
     *
     *
     */
    private fun withUsage(){
        val user = UserBean("Tom")
        with(user){
            this.name = "with"
        }
        println("testWith:${user.name}")

    }


    /**
     * 特点:
     *  1. 在函数块内可以通过 this 指代当前对象
     *  2. 返回值为函数块的最后一行代码或指定 return 表达式
     *  3. 可以在调用 let 前判空处理
     *
     * 适用场景: 是 let 和 with 两个的函数结合体可， 适用于 let 和 with的任何场景，它弥补了
     * let 函数在函数体类必须使用 it 参数对象， 在 run 函数中可以像 with 一样可以省略，直接
     * 访问属性的公有属性和方法，另一方面弥补了 with 函数传入对象的判空问题，在run 函数中可以
     * 像 let 一样在调用前做判空处理
     *
     *
     */
    private fun runUsage(){
        getTextView()?.run {
            setTextColor(requireContext().getColor(R.color.red_100))
            text = "Hello"
        }

    }


    /**
     * 特点:
     *  1.在函数块内可以通过 this 指代当前对象或省略
     *  2.返回值是对象本身(this)
     *  3.可以在调用 apply 前做判空处理
     *
     *  适用场景:
     *  * 适用于 run 函数的任何场景，一般用于连续调用对象的多个方法
     */
    private fun applyUsage(){
        getTextView()?.apply {
            setTextColor(requireContext().getColor(R.color.red_100))
            text = "Hello"
        }

    }

    /**
     *  1.在函数块内可以通过 it 指代当前对象或省略
     *  2.返回值是对象本身(this)
     *  3.可以在调用 also 前做判空处理
     *
     *
     */
    private fun alsoUsage(){
       val instance =  KotlinMain.instance.getKotlinMain()
        Logger.d("instance:$instance")

    }



}

//用 A 来代表 File
typealias A = File

typealias Success = (String) -> Void

typealias UserList = List<UserBean>
