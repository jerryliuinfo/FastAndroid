package com.apache.fastandroid.demo.kt.lambda

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentLambdaBinding
import com.apache.fastandroid.demo.kt.inline.Doggo
import com.apache.fastandroid.demo.kt.inline.DoggoId
import com.tesla.framework.kt.MyDialog
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlin.random.Random

/**
 * Created by Jerry on 2022/7/14.
 */
class LambdaDemoFragment:BaseBindingFragment<FragmentLambdaBinding>(FragmentLambdaBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnNoParam.setOnClickListener {
            noParm.invoke()
        }
        mBinding.btnHaveParam.setOnClickListener {
            haveParam()
        }

        mBinding.btnAnonymousFunc.setOnClickListener {
            anoymousUsage()
        }

        mBinding.btnReferenceFunc.setOnClickListener {
            referenceFunUsage()
        }

        mBinding.btnReferenceFunc2.setOnClickListener {
            referenceFunUsage2()
        }

        mBinding.btnLambdaExample.setOnClickListener {
            lambdaInstance()
        }

        mBinding.btnTypeAlias.setOnClickListener {
            typeAliasUsage()
        }
        mBinding.btnHighOrderFunction.setOnClickListener {
            highOrderFunction()
        }
        mBinding.btnLambdaReceiver.setOnClickListener {
            lambdaWithReceiver()
        }

    }

    private fun lambdaWithReceiver() {
        MyDialog(requireContext()).show {
            message("custom message")
        }
    }

    private fun highOrderFunction() {
        request("1", onLoad = {
            println("onLoad -->")
        }, onSuccess = { result ->
            println("onSuccess result:$result")
        }, onFailed = { code,msg,_ ->
            println("onFailed code:$code, msg:$msg")

        })
    }

    private fun fun1(f:(Int) -> Unit){

    }

    private fun fun2(f:(Int) -> Unit){

    }

    private fun fun3(f:IntFun){
        f(3)
    }

    private fun fun4(f:IntFun){
        f(4)
    }

    fun foo(p:Predicate<Int>) = p(42)

    private fun typeAliasUsage() {
        fun1 {
            println(it)
        }
        fun2 {
            println(it)
        }
        fun3 {
            println(it)
        }
        fun4 {
            println(it)
        }

        val f:(Int) -> Boolean = {it > 0}
        println(foo(f))

        val p:Predicate<Int> = {it > 0}
        val result1 = listOf(1,2,-3,4,-5).filter(p)

        val p2:Predicate<String> = {it.length > 4}
        val result2 = arrayOf("Java", "Kotlin", "Python", "C", "C++","OuyangPeng").filter(p2)

        println("result1:$result1, result2:$result2")

        val manager = TypeAliasMananger().apply {
            setCallback {result ->
                println("result:$result")
            }

            trigger()
            train(listOf(Doggo(DoggoId(1))))

        }


         val callback:TypeAliasCallback = {i:Int-> println(i) }


    }


    private fun request(id:String,
                        onLoad:() -> Unit = {},
                        onSuccess:(id:String) -> Unit,
                        onFailed:(errorCode:Int?, errorMsg:String?, throwable:Throwable? ) -> Unit = { _,_,_ -> }
    ){
        onLoad()
        val randomInt = Random.nextInt(10 ) > 5
        if (randomInt){
            onSuccess("$id: $randomInt")
        }else{
            onFailed(100, "exception occured",null)
        }

    }



    private fun lambdaInstance() {
        val add = cal(3,4){ a,b ->
            a + b
        }
        val add2 = cal(3,4,::sum)
        toast("add:$add, add2:$add2")
    }


    private class Test{
        fun doSomething(){
            println("do something")
        }

        fun doTest(f:(Test) -> Unit){
            f(this)
        }
    }

    private fun referenceFunUsage2() {
        val test = Test()
        test.doTest {
            it.doSomething()
        }

        test.doTest (Test::doSomething)
    }


    private fun cal(a:Int, b:Int, f:(Int,Int) ->Int ):Int{
        return f(a,b)
    }

    private fun sum(a:Int, b:Int):Int{
        return a + b
    }

    private fun multiple(a:Int, b:Int):Int{
        return a * b
    }

    private fun referenceFunUsage() {
        val sumResult = cal(2,3,::sum)
        val multipleResult = cal(2,3,::multiple)
        toast("sum:$sumResult, multiple:$multipleResult")

    }





    private fun anoymousUsage() {
        val d = fun (a:Int, b:Int):Int{
            return a + b
        }
        toast(d(1,2).toString())
    }

    val noParm = {
        toast("no param")
    }

    private fun haveParam(){
        val sum:(Int,Int) -> Int = { a, b ->
            a + b
        }

        //等价于
        val sum2 = { a:Int, b:Int ->
            a + b
        }

        //等价于函数
        fun sum3(a:Int, b:Int):Int{
            return a + b
        }
        toast("sum:${sum(1,2)},sum2:${sum2(1,2)}, sum3:${sum3(1,2)}")
    }





}

typealias IntFun = (Int) -> Unit

typealias Predicate<T> = (T) -> Boolean

