package com.apache.fastandroid.demo.kt

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.apache.fastandroid.BuildConfig
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.KtGrammerBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.annotation.ImAPlant
import com.apache.fastandroid.demo.kt.annotation.Plant
import com.apache.fastandroid.demo.kt.bean.*
import com.apache.fastandroid.demo.kt.delegate.DelegateList
import com.apache.fastandroid.demo.kt.delegate.People
import com.apache.fastandroid.demo.kt.inline.PreferenceManager
import com.apache.fastandroid.demo.kt.inline.onlyIf
import com.apache.fastandroid.demo.kt.inline.onlyIf2
import com.apache.fastandroid.demo.kt.operatoroverload.*
import com.apache.fastandroid.demo.kt.refied.RefiedDemo
import com.apache.fastandroid.demo.kt.sealed.*
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.util.DateUtil
import com.kingja.loadsir.core.LoadSir
import com.microsoft.office.outlook.magnifierlib.frame.FrameCalculator
import com.tesla.framework.common.util.HideTextWatcher
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.maxCustomize
import com.tesla.framework.kt.plusAssign
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.nio.charset.Charset
import kotlin.collections.ArrayList
import kotlin.coroutines.Continuation
import kotlin.math.cos
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinKnowledgeFragment:BaseVBFragment<KtGrammerBinding>(KtGrammerBinding::inflate) {
    companion object{
        private const val TAG = "KotlinKnowledgeFragment"
    }


    private lateinit var mFrameCalculator:FrameCalculator

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnSAM.setOnClickListener {
            samUsage()
        }

        viewBinding.btnCreateHighOrderFunc.setOnClickListener {
            createHighOrderFunc()
        }
        viewBinding.btnJoinTostring.setOnClickListener {
            joinToStringUsage()
        }

        viewBinding.btnLableBreak.setOnClickListener {
            labelBreak()
        }
        viewBinding.btnAnnotation.setOnClickListener {
            annotationUsage()
        }
        viewBinding.btnCheck.setOnClickListener {
            checkFuncUsage()
        }

        viewBinding.btnDelegate.setOnClickListener {
            delegate()
        }

        viewBinding.btnRefied.setOnClickListener {
            refiedTest()
        }

        viewBinding.btnObject.setOnClickListener {
            objectExpression()
        }
        viewBinding.btnOnEach.setOnClickListener {
            onEach()
        }
        viewBinding.btnMethodCost.setOnClickListener {
            val costTime = measureTimeMillis {
                Thread.sleep(100)
            }
            Logger.d("costTime:${costTime}")

            println()
        }
        viewBinding.btnCoerceAtLeast.setOnClickListener {
            Logger.d("${3.coerceAtLeast(5)}")
        }


        mFrameCalculator = FrameCalculator{
            Logger.d("frame: ${it}")
        }
        viewBinding.btnHighOrderFunction.setOnClickListener {
            highOrderFunction()
            println(highOrderFun2())
        }
        viewBinding.btnExtensionHighOrderFunction.setOnClickListener {
            println(extenseHighOrderFunction())
        }
        viewBinding.btnClazzParamter.setOnClickListener {
            passJavaClass(JavaMain::class.java)
            passKotlinClass(KotlinMain::class)
            println(KotlinMain.instance.hashCode())

        }
        viewBinding.btnJavaKotlinCallEachOther.setOnClickListener {
            format("")
        }
        viewBinding.btnNestedFunction.setOnClickListener {
            nestedFunction()
        }

        viewBinding.btnExpandFunction.setOnClickListener {
            val file = File(requireContext().filesDir, "uitest.txt")
            file.writeText("hello:${Random.nextInt(10)}")

            println(file.readText())

            //给LoadSir 增加一个方法
            if (LoadSir.Builder().debug()){

            }
        }

        viewBinding.btnExpandFunction2.setOnClickListener {
            /**
             * 输出的 是 animal，而不是 dog，因为kotlin 的扩展方法是静态地给一个类添加方法，
             * 是不具备动态运行时的多态效应,扩展函数会被编译成一个静态函数
             */
            Dog().printName(Dog())
        }
        viewBinding.btnLambda.setOnClickListener {
            lambdaUsage()
        }

        viewBinding.btnInline.setOnClickListener {
            inlineUsage()
            nonInlineUsage()
        }
        viewBinding.btnCampanionObject.setOnClickListener {
            Logger.d("SingleObject: ${SingleObject.get()}")
            Logger.d("SingleObject2: ${SingleObject.get()}")
        }
        viewBinding.btnKeywordBy.setOnClickListener {
            ByTest.Zoo(ByTest.Cat()).bark()

        }
        viewBinding.btnByDelegate.setOnClickListener {
            ByTest.ZooBy(ByTest.Cat()).bark()
        }


        viewBinding.btnSealedClass.setOnClickListener {
           sealedClass()
        }

        viewBinding.btnSealedClass2.setOnClickListener {
            var user = User(1, "name")
            PlayerUI.get().showPlayer(user)
             user = User(1, "name", PlayerViewType.GREEN)
            PlayerUI.get().showPlayer(user)
            user = User(1, "name", PlayerViewType.VIP("VIP播放器","VIP播放器内容"))
            PlayerUI.get().showPlayer(user)

        }
        viewBinding.btnDeconstruction.setOnClickListener {
            val user = UserBean("Tom",11)
            val (name,age, nick2) = user
            Logger.d("name:$name, age:$age,nick2:$nick2")

            val map = mapOf<String,String>("key1" to "value1", "key2" to "value2")
            map.forEach {
                Logger.d("${it.key}:${it.value}")
            }
        }
        viewBinding.btnIterator.setOnClickListener {

            iteratorFun()
        }
        viewBinding.btnWithIndex.setOnClickListener {
            val list = arrayListOf("aa", "bb", "cc")
            for ((index, value ) in list.withIndex()){
                Logger.d("with index: ${index}:${value}")

            }
        }


        viewBinding.btnTakeUnless.setOnClickListener {
            testUnless("Hello Kitty","Kit")
            testUnless("Hello Tom", "Kit")
        }

        viewBinding.btnTakeIf.setOnClickListener {
            testTakeIf("Hello Kitty","Kit")
            testTakeIf("Hello Tom", "Kit")
        }
        viewBinding.btnOperator.setOnClickListener {
            testOperator()
            testOperation2()
        }

        viewBinding.btnCustomOperator.setOnClickListener {
           testMyOperator()
        }


        viewBinding.btnWhenOperator.setOnClickListener {
            testWhenOperator()
        }

        viewBinding.btnGenericParameter.setOnClickListener {
            println(genericFun("四大发明", "火药","指南针"))

            val array1:Array<Int> = arrayOf(1,2,3)
            val array2:Array<Double> = arrayOf(1.1,2.2,3.3)
            //报错
//            setArrayNum(array1)
            setArrayNum2(array1)
            setArrayNum2(array2)
        }
        viewBinding.btnTailFun.setOnClickListener {
            var startTime = System.currentTimeMillis()
            var result = findXPoint()
            println("findXPoint1 result:$result, cost time: ${System.currentTimeMillis() - startTime} ms")
            startTime = System.currentTimeMillis()
            result = findXPoint2()
            println("findXPoint2 result:$result, cost time: ${System.currentTimeMillis() - startTime} ms")
        }

        viewBinding.btnObjectProperty.setOnClickListener {
            println("dataTime:${DateUtil.nowDateTime}, date:${DateUtil.nowDate}, time:${DateUtil.nowTime}")
        }

        viewBinding.btnProxyMode.setOnClickListener {
            proxyMode()
        }

        viewBinding.btnNestedClass.setOnClickListener {
            nestedClass()
        }

        viewBinding.btnTemplateClass.setOnClickListener {
            templateClass()
        }

        viewBinding.edittext.addTextChangedListener(HideTextWatcher(viewBinding.edittext))

        viewBinding.btnSuspend.setOnClickListener {
            MainScope().launch {
                testSuspend()
            }
        }
        viewBinding.btnOperatorOverload2.setOnClickListener {
            operatationOverload()
        }

        //关键字冲突 用 反引号转义
        println(JavaMain.`in`)
    }

    private fun samUsage() {
        val runnable = Runnable { println("I am a runnable") }

    }

    private fun createHighOrderFunc() {
        val fruit = Fruit("apple")
        myWith(fruit.name){
//            var capitize =  replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            var capitize =  capitalize()
            println(capitize)
        }
    }

    inline fun myWith(name: String, block: String.() -> Unit){
        name.block()
    }

    private fun joinToStringUsage() {
        val fruit = mutableListOf(Fruit("Apple"),Fruit("Pear"),Fruit("banana"))
        val result = fruit.map { it.name }.filter {
            it.contains("a")
        }.joinToString(", ") { it }
        println(result)
    }

    private fun lambdaUsage2(){
        val waterFilter = {dirty:Int -> dirty / 2}
    }

    private fun labelBreak() {
        outloop@ for (i in 1..100){
            print(i)
            for (j in 1..100){
                if (i > 10){
                    break@outloop
                }
            }
        }
    }

    private fun annotationUsage() {
        val classObj = Plant::class
        for (a in classObj.annotations){
            val annotationClass = a.annotationClass
            println("simpleName:${annotationClass.simpleName}, isOpen:${annotationClass.isOpen}, isCompanion:${annotationClass.isCompanion}")
        }

        val myAnnotationObject = classObj.findAnnotation<ImAPlant>()
        println(myAnnotationObject)
    }

    private fun checkFuncUsage() {
        val age = 18
        check(age > 10)
    }

    fun intRange(){
        IntRange(1,6).map {  }
        println(1..6)
    }

    fun interface Transformer<T,U>{
        fun transform()
    }


    private fun animatorKt() {
        val animator = ObjectAnimator.ofFloat(0f,1f)
        animator.doOnStart {
            println("doOnStart")
        }
        animator.doOnEnd {
            println("doOnEnd")
        }
        animator.start()

    }

    private fun delegate() {
        val delegateList = DelegateList<String>().apply {
            add("one")
            add("two")
        }
        delegateList.remove("one")
        var deletedItem = delegateList.deletedItem
        println("delegate deletedItem:${deletedItem}")

        val people = People("jerry", "liu")
        people.name = "libel"
        people.lastname= "ou"
        println("delegate updateCount:${people.updateCount}")
    }

    private fun refiedTest() {
        val refiedDemo = RefiedDemo()
        refiedDemo.printStringType()
        refiedDemo.printIntType()



        val intCall: Int = refiedDemo.calculate(123643f)
        val floatCall: Float = refiedDemo.calculate(123643f)
        println("intCall:${intCall}, floatCall:${floatCall}")

    }


    fun objectExpression(){


    }


    /**
     * 如果在API中发现某个类有unaryPlus()、unaryMinus()、not()方法，
     * 那就说明可对该类的实例使用单目前缀运算符+、-、!进行运算。
     */
    private fun operatationOverload() {
        val choir = Choir()
        var singer = Singer("朗朗")
        choir += singer
        println(choir.singers)
        println("contains:${choir.contains(singer)}, not contains:${!choir.contains(Singer("朗朗"))}")

        val a = 10
        val b = -a
        //Returns the negative of this value
        val c = a.unaryMinus()
        //Returns this value
        val d = a.unaryPlus()
        println()

        var point1 = Point(5,5)
        val point2 = Point(1,2)
        //plus
//        var plus = point1 + point2
        var minus = point1 - point2
        var times = point1 * point2
        var div = point1 / point2
        var rem = point1 % point2
        var compareTo = point1 > point2

        point1 += point2
//        println("+:${plus}, -:${minus}, *:${times}, /:${div}, %:${rem}, >:${compareTo}, +=:${point1}")
        println(" -:${minus}, *:${times}, /:${div}, %:${rem}, >:${compareTo}, +=:${point1}")

        val textView = TextView(context).apply {
            text = "dynamic text"
            setTextColor(context.getColor(R.color.black))
        }
        viewBinding.linerlayout += textView
    }


    private suspend fun testSuspend():Repo{
        val user = ApiEngine.getApiServiceKt().getArticleById(10)
        return user.data
    }

    private suspend fun testSuspend2(completion:Continuation<Any?>):Repo{
        val user = ApiEngine.getApiServiceKt().getArticleById(10)
        return user.data
    }

    private fun templateClass() {
        var river = when(count ++ %4){
            0 -> River("小溪",100)
            1 -> River("瀑布",99.9f)
            2 -> River("三间",50.5f)
            else -> River("大河","一千")
        }
        println(river.getInfo())
    }

    private fun sealedClass() {
        var season = when(count ++ %4){
            0 -> SeasonNameSealed.Spring("春天")
            1 -> SeasonNameSealed.Summer("夏天")
            2 -> SeasonNameSealed.Autumn("秋天")
            else -> SeasonNameSealed.Spring("冬天")
        }

        val text = when(season){
            is SeasonNameSealed.Spring -> season.name
            is SeasonNameSealed.Summer -> season.name
            is SeasonNameSealed.Autumn -> season.name
            is SeasonNameSealed.Winter -> season.name
        }
        println("season by sealed class:$text")

        doAction1(SealedResult.Success(10))
        doAction1(SealedResult.ERROR(IllegalArgumentException("invalid param")))

        doAction2(SealedResult2.Success(10))
        doAction2(SealedResult2.Error.NonRecoverableError(IllegalArgumentException("NonRecoverableError -->")))
        doAction2(SealedResult2.Error.RecoverableError(IllegalArgumentException("RecoverableError -->")))

    }

    private fun nestedClass() {
    }

    private fun proxyMode() {
        var fow = when(count ++ % 6)
        {
            0 -> WildFow("老鹰",Bird.MAILE, BehaviorFly())
            1 -> WildFow("凤凰", behavior = BehaviorFly())
            2 -> WildFow("大雁",Bird.FEMAILE, BehaviorFly())
            3 -> WildFow("企鹅", behavior = BehaviorSwim())
            4 -> WildFow("鸵鸟",Bird.MAILE, BehaviorRun())
            5 -> WildFow("鸳鸯",behavior = BehaviorRun())
            else -> WildFow("老鹰",Bird.MAILE, BehaviorRun())
        }
        println(fow.name)
    }


    var count:Int = 0
    private fun testWhenOperator() {
        when(count){
            1,3,5,7,9 -> println("case1")
            in 11..18 -> println("case2")
            !in 6..10 -> println("case3")
            else -> println("case4")
        }
        count = (count +1) % 20

        val name:String ? = null
        val length = name?.length?: -1
    }

    private fun <T> genericFun(tag:String, vararg otherInfo :T?):String{
        var str = "$tag "
        for (item in otherInfo){
            str = "$str ${item.toString()}"
        }
        return str
    }


    private fun setArrayNum(array: Array<Number>){
        var result = "数组元素: "
        for (item in array){
            result = "$result$item"
        }
        println(result)
    }

     private inline fun <reified T:Number> setArrayNum2(array: Array<T>){
        var result = "数组元素: "
        for (item in array){
            result = "$result$item"
        }
        println(result)
    }

    fun findXPoint(x:Double = 1.0):Double = if (x == cos(x)) x else findXPoint(cos(x))
    tailrec fun findXPoint2(x:Double = 1.0):Double = if (x == cos(x)) x else findXPoint2(cos(x))


    private fun testOperator(){
        val list = arrayListOf<Char>('a','b','c','d')
        list.map {
           it - 'a'
        }.filter {
            it > 0
        }
            //返回符号 闭包的第一个值
            .find {
            it > 1
        }
        Logger.d("list:$list")

    }

    private fun testOperation2(){
        val a = arrayOf("4","0","7","i","f","w","0","9")
        val index = arrayOf(5,4,9,4,8,3,1,9,2,1,7)

        index.filter {
            println("testOperation2 filter i:$it")
            it < a.size
        }.map {
            println("testOperation2 map i:$it")
            a[it]
        }.reduce { acc, s ->
            return@reduce "$acc $s"
        }
            .also {
                println("testOperation2 密码是:${it}")
            }
    }

    private fun testMyOperator(){
        val list = arrayListOf<Char>('a','b','c','d')
        list.myMap {
            it - 'a'
        }.myFilter {
            it > 0
        }
            //返回符号 闭包的第一个值
            .find {
                it > 1
            }
        Logger.d("list22:$list")
    }




    /**
     * 不满足条件就返回本身，否则返回null
     */
    private fun testUnless(name:String, keyword:String){
        name.indexOf(keyword)
            .takeUnless {
                Logger.d("testTakeUnless it: ${it}")
                it < 0

            }?.let {
                Logger.d( "testTakeUnless has $keyword")
            }
    }

    private fun testTakeIf(name:String, keyword:String){
        name.indexOf(keyword)
            .takeIf {
                Logger.d("testTakeIf it: ${it}")
                it > 0

            }?.let {
                Logger.d( "testTakeIf has $keyword")
            }
    }

    private fun iteratorFun() {
        val builder = StringBuilder()
        for (i in 1..3){
            builder.append(i).append(",")
        }
        Logger.d("in: ${builder.toString()}")
        builder.clear()

        for (i in 2 until 4){
            builder.append(i).append(",")
        }
        Logger.d("until: ${builder.toString()}")
        builder.clear()

        for (i in 5 downTo 2){
            builder.append(i).append(",")
        }
        Logger.d("downTo: ${builder.toString()}")
        builder.clear()

        for (i in 1..6 step 2){
            builder.append(i).append(",")
        }
        Logger.d("in step: ${builder.toString()}")
        builder.clear()

        repeat(3){
            builder.append(it).append(",")
        }
        Logger.d("repeat: ${builder.toString()}")
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

        context?.let {
            val userInfoManager = PreferenceManager(it.getSharedPreferences("userInfo", Context.MODE_PRIVATE))
            userInfoManager.saveToken("abcd1234")
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

    private fun <T> mxCustom(array: Array<T>, greater:(T,T) -> Boolean):T?{
        var max:T? = null
        for (item in array){
            if (max == null || greater(item,max)){
                max = item
            }
        }
        return max
    }


    private fun highOrderFun2():String{
        val array:Array<String> = arrayOf("How", "do","you","do", "I'm    ","Fine")
        count++
        return when(count % 4){
            0 -> "字符串数组的默认最大值为:${array.maxOrNull()}"
            1 -> "字符串数组按长度比较的最大值为:${mxCustom(array) { a, b -> a.length > b.length }}"
            2 -> "字符串数组的默认最大值(使用高阶函数):${mxCustom(array) { a, b -> a > b }}"
            else -> "字符串数组去掉空格的最大值:${mxCustom(array) { a, b -> a.trim().length > b.trim().length }}"
        }
    }

    private fun extenseHighOrderFunction():String {
        val array:Array<String> = arrayOf("How", "do","you","do", "I'm    ","Fine")
        count++
        return when(count % 4){
            0 -> "字符串数组的默认最大值为:${array.maxOrNull()}"
            1 -> "字符串数组按长度比较的最大值为:${array.maxCustomize { a, b -> a.length > b.length }}"
            2 -> "字符串数组的默认最大值(使用高阶函数):${array.maxCustomize { a, b -> a > b }}"
            else -> "字符串数组去掉空格的最大值:${array.maxCustomize{ a, b -> a.trim().length > b.trim().length }}"
        }
    }







    private fun lambdaUsage() {
       echo.invoke("Zhangtao")
       echo("Jerry")
        val waterFilter = {dirty:Int -> dirty / 2}
        println(waterFilter(30))



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



    private fun <T> myfilter(originalList: List<T>, condition:(T) -> Boolean): List<T> {
        val list = mutableListOf<T>()
        for (t in originalList) {
            if (condition(t)) {
                list.add(t)
            }
        }
        return list
    }

    private fun <T,R> myMap(originalList: List<T>, transform:(T) -> R): List<R> {
        val list = mutableListOf<R>()
        for (t in originalList) {
            list.add(transform(t))
        }
        return list
    }


    fun <T, R> Iterable<T>.myMap(transform: (T) -> R): List<R> {
        return myMap(ArrayList<T>(), transform)
    }

    fun <T> Iterable<T>.myFilter(preCondtion: (T) -> Boolean): List<T> {
        return myfilter(ArrayList<T>(), preCondtion)
    }

}