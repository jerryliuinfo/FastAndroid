package com.apache.fastandroid.demo.kt.lambda

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentLambdaBinding
import com.apache.fastandroid.demo.bean.Person
import com.apache.fastandroid.demo.kt.KotlinKnowledgeFragment2
import com.apache.fastandroid.demo.kt.inline.Doggo
import com.apache.fastandroid.demo.kt.inline.DoggoId
import com.tesla.framework.kt.MyDialog
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.view_animator_activity_main.percent
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

        mBinding.btnReceiver2.setOnClickListener {
            lambdaReceiver2()
        }
    }

    private fun lambdaReceiver2() {
        val totalSum: Person.(Person) -> Int = { other -> age.plus(other.age) }
        val totalAge = totalSum(Person("jim",10),Person("lily",5) )
        println("totalAge:$totalAge")

        formatPerson {
            println("age is: ${age}")
        }
    }

    fun formatPerson(init:Person.() -> Unit):Person{
        val person = Person("Jim",10)
        person.init()
        return person
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


        val items = listOf(1,2,3,4)
        val result = items.foldElement(0) { acc, item ->
            val result = acc + item
            println("acc:$acc, item:$item, result:$result")
            result
        }
        println("result:$result")

        //函数类型实例化
        val intFunc:(Int) -> Int = KotlinKnowledgeFragment2.IntTransformer()
        println("intFunc1:${intFunc(3)}")

        val stringTransformer:(String) -> List<String> = KotlinKnowledgeFragment2.ValueTransformer()
        val stringResult = stringTransformer.invoke("hello")

        val personTransformer:(Person) -> List<Person> = KotlinKnowledgeFragment2.ValueTransformer()
        val personResult = personTransformer.invoke(Person("Lucy",18))
        println("stringResult:${stringResult}, personResult:$personResult" )

        /**
         * Lambda 表达式的完整语法形式如下：
         * lambda 表达式总是括在花括号中。
        完整语法形式的参数声明放在花括号内，并有可选的类型标注。
        函数体跟在一个 -> 之后。
        如果推断出的该 lambda 的返回类型不是 Unit，那么该 lambda 主体中的最后一个（或可能是单个）表达式会视为返回值。
         */
        val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

        //如果将所有可选标注都留下，看起来如下：
        val sum2 =  { x: Int, y: Int -> x + y }

        println("sum:${sum(2,3)}")
        println("sum2:${sum2(2,4)}")
    }

    fun <T,R> Collection<T>.foldElement(initValue:R, combine:(acc:R, nextElement:T) -> R):R{
        var accumulator:R = initValue
        for (element in this){
            accumulator = combine(accumulator,element)
        }
        return accumulator
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

