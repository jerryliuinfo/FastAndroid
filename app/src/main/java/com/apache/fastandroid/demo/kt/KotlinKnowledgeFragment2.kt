package com.apache.fastandroid.demo.kt

import android.app.ActivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.OnKeyListener
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.KtGrammer2Binding
import com.apache.fastandroid.demo.bean.Person
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.bean.KotlinMain
import com.apache.fastandroid.demo.kt.delegate.BaseImpl
import com.apache.fastandroid.demo.kt.delegate.Derived
import com.apache.fastandroid.demo.kt.delegate.Example
import com.apache.fastandroid.demo.kt.genericity.GenericView
import com.apache.fastandroid.demo.kt.genericity.GenericityAImpl
import com.apache.fastandroid.demo.kt.genericity.JavaGeneric
import com.apache.fastandroid.demo.kt.genericity.KtGenericity
import com.apache.fastandroid.demo.kt.staticusage.Foo
import com.apache.fastandroid.demo.kt.staticusage.topLevelFun1
import com.apache.fastandroid.demo.widget.listadapter.AlbumListAdapter
import com.apache.fastandroid.network.model.result.Result
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.fromJson2
import com.tesla.framework.kt.getMySystemService
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_best_practice_livedata.time
import retrofit2.HttpException
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Collections
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2021/10/18.
 * done
 */
class KotlinKnowledgeFragment2 :
    BaseBindingFragment<KtGrammer2Binding>(KtGrammer2Binding::inflate) {
    companion object {
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

            val userBean = UserBean("jerry", 10)
            var json = GsonUtils.toJson(userBean)

            //Java 泛型
            JavaGeneric.fromJson(json, UserBean::class.java)
            //Kotlin 泛型
            val userBean2 = Gson().fromJson2<UserBean>(json)
            println("json: $json, userBean2 name:${userBean2.name}, age:${userBean2.age}")

            val manage: ActivityManager? = activity?.getMySystemService<ActivityManager>()
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



        mBinding.btnArrayInit.setOnClickListener {
            initArrayUsage()
        }

        mBinding.btnOperatorOverLoading.setOnClickListener {
            operatorOverLoadingUsage()
        }
        mBinding.btnIfNotNull.setOnClickListener {
            ifNotNullUsage()
        }

        mBinding.btnTrapDataclass.setOnClickListener {
            dataClassTrap()
        }

        mBinding.btnTryCatch.setOnClickListener {
            tryCatchUsage()
        }

        mBinding.btnTryCatch.setOnClickListener {
            tryCatchUsage()
        }

        mBinding.btnTryWithResource.setOnClickListener {
            tryWithResourceUsage()
        }

        mBinding.btnObjectExpression.setOnClickListener {
            objectExpressionUsage()
        }

        mBinding.btnIntegerDivision.setOnClickListener {
            integerDivisionUsage()
        }

        mBinding.btnTrim.setOnClickListener {
            trimUsage()
        }

        mBinding.btnTypeConvert.setOnClickListener {
            typeConvertUsage()
        }

        mBinding.btnGetWhenResult.setOnClickListener {
            getWhenResult()
        }

        mBinding.btnReturnBreak.setOnClickListener {
            returnBreak()
        }

        mBinding.btnTryIsExpression.setOnClickListener {
            tryIsExpression()
        }

        mBinding.btnDelegate.setOnClickListener {
            delegateUsage()
        }

        mBinding.btnFunction.setOnClickListener {
            functionUsage()
        }

        mBinding.btnInfix.setOnClickListener {
            infixUsage()
        }


    }



    class IntTransformer:(Int) -> Int{
        override operator fun invoke(p1: Int): Int {
            return p1 + 1
        }
    }

    class  ValueTransformer<T>:(T) -> List<T>{
        override fun invoke(p1: T): List<T> {
            return arrayListOf(p1,p1)
        }

    }




    /**
     * 中缀表示法
        标有 infix 关键字的函数也可以使用中缀表示法（忽略该调用的点与圆括号）调用。 中缀函数必须满足以下要求：
        它们必须是成员函数或扩展函数。
        它们必须只有一个参数。
        其参数不得接受可变数量的参数且不能有默认值。
     */
    private fun infixUsage() {
       val result =  "hello" repeatSelf 2
        println("infixUsage result:$result")
    }

    fun <T> singletonList(item:T):List<T>{
        return Collections.singletonList(item)
    }

    infix fun String.repeatSelf(times:Int):String{
        return repeat(times)
    }
    private fun functionUsage() {
        //可变数量的参数（varargs）
        val a = intArrayOf(1, 2, 3)
        val list = asList("Hello", *a.toTypedArray())
        println("list:$list")
    }

    private fun <T> asList(vararg ts:T ):List<T>{
        val list = ArrayList<T>()
        for (t in ts){
            list.add(t)
        }
        return list
    }

    /**
     * 实现继承的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它
     */
    private fun delegateUsage() {
        val b = BaseImpl(10)

        Derived(b).print()


        //覆盖由委托实现的接口成员
        val derived = Derived(b)
        derived.printLine()

        //以这种方式重写的成员不会在委托对象的成员中调用 ，委托对象的成员只能访问其自身对接口成员实现：
        //所以这里输出的是  BaseImpl: x = 10， 而不是 Message of Derived
        println(derived.message)

        propertyDelegate()
    }

    var topLevelInt: Int = 15
    //给扩展属性设置委托
    var BaseImpl.excDelegated:Int by ::topLevelInt
    /**
     *
     * https://book.kotlincn.net/text/delegated-properties.html
     * 属性委托
     * 延迟属性（lazy properties）: 其值只在首次访问时计算。
       可观察属性（observable properties）: 监听器会收到有关此属性变更的通知。
       把多个属性储存在一个映射（map）中，而不是每个存在单独的字段中。
     */

    private fun propertyDelegate(){
        val example = Example()
        println("message before write:${example.message}")

        example.message = "hello"
        println("message after write:${example.message}")

        /**
         * 延迟属性: lazy() 是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托。
         * 第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果。 后续调用 get() 只是返回记录的结果。

         */
        val lazyValue:String by lazy {
            println("computed")
            "hello"
        }
        println("propertyDelegate11:lazyValue:$lazyValue")
        println("propertyDelegate22:lazyValue:$lazyValue")

        //可观察属性 Observable properties

        var name:String by Delegates.observable("null"){ prop, old, new ->
            println("name changed prop:$prop, $old to $new")
        }
        name = "first"
        println("name111:$name")

        name = "second"
        println("name222:$name")


        //委托给另一个属性


        class ClassWithDelegate(val anotherClassInt: Int)
        class MyClass(var memInt:Int, val anotherClass:ClassWithDelegate){

            var newName:String = "hello"

            @Deprecated("use newName instead")
            var oldName:String by this::newName


            var delegateToMem:Int by this::memInt
            var delegateToTopLevel:Int by ::topLevelInt

            val delegateToAnotherClass:Int by anotherClass::anotherClassInt

        }

        val myClass = MyClass(10, ClassWithDelegate((20)))
        println("delegateToMem:${myClass.delegateToMem}, delegateToTopLevel:${myClass.delegateToTopLevel},delegateToAnotherClass:${myClass.delegateToAnotherClass},excDelegated:${BaseImpl(1).excDelegated}")


        //想要以一种向后兼容的方式重命名一个属性的时候：引入一个新的属性、 使用 @Deprecated 注解来注解旧的属性、并委托其实现。

        myClass.oldName = "world"
        println("newName: ${myClass.newName}")

        //将属性储存在映射中
        class User(val map:Map<String,Any?>){
            val name:String by map
            val age:Int by map

        }

        val user = User(mapOf("name" to "Jim", "age" to 15))
        println("name:${user.name}, age:${user.age}")


        fun condition(str:String):Boolean{
            return str.length > 5
        }

        //局部委托属性

        fun localPartyDelegate(computeFoo:() -> String){
            //memoizedFoo 变量只会在第一次访问时计算。 如果 someCondition 失败，那么该变量根本不会计算。
            val memoizedFoo:String by lazy {
                println("init foo")
                "hello world" }

            if (condition("hi") && memoizedFoo.length > 5){
                println("length is bigger than 5")
            }
        }
        localPartyDelegate {
            "world world"
        }



        val owner = Owner()
        owner.varResource = Resource("hello")
        println("read resouce:${owner.varResource}")

    }

    class Resource(name:String)
    class Owner{
        var varResource:Resource by ResourceDelegate()
    }

    /**
     * 对于一个只读属性（即 val 声明的），委托必须提供一个操作符函数 getValue()
     * 对于一个可变属性（即 var 声明的），委托必须额外提供一个操作符函数 setValue()
     * @property resource Resource
     * @constructor
     */
    class ResourceDelegate(private var resource: Resource = Resource("Default")){
        operator fun getValue(thisRef:Owner, property:KProperty<*>):Resource{
            println("ResourceDelegate getValue resource:$resource")
            return resource
        }

        operator fun setValue(thisRef:Owner, property:KProperty<*>, value:Any?){
            println("ResourceDelegate setValue value:$value")

            if (value is Resource){
                resource = value
            }
        }
    }



    /**
     * try-表达式的返回值是 try 块中的最后一个表达式或者是（所有）catch 块中的最后一个表达式。
     * finally 块中的内容不会影响表达式的结果。
       try-表达式的返回值是 try 块中的最后一个表达式或者是（所有）catch 块中的最后一个表达式。
       finally 块中的内容不会影响表达式的结果。
     */
    private fun tryIsExpression() {
        val input = ""
        val result:Int? = try {
            input.toInt()
        }catch (e:NumberFormatException){
            null
        }finally {
            100
        }
        println("tryIsExpression result:$result")


        val rectangle = Rectangle()
        rectangle.setterWithAnnotation


    }

    open class Shape {
        open val vertexCount: Int = 0
    }

    class Rectangle : Shape() {
        override val vertexCount: Int = 10

        var setterWithAnnotation: Any? = null
            @Inject set // 用 Inject 注解此 setter
    }



    /**
     * return 默认从最直接包围它的函数或者匿名函数返回。
       break 终止最直接包围它的循环。
       continue 继续下一次最直接包围它的循环。
     */
    private fun returnBreak() {


        //标签限定 break
        loop@ for (i in 1..5){
            for (j in 1..5){
                if (j > 5){
                    print("$i:$j")
                    //标签限定的 break 跳转到刚好位于该标签指定的循环后面的执行点。 continue 继续标签指定的循环的下一次迭代。
                    break@loop
                }
            }
        }
        //返回到标签
        returnToLabel()
        //局部返回
        partlyReturn()
        //使用隐式标签返回
        partlyReturnByImplictLabel()
    }


    /**
     * 返回到标签

        Kotlin 中函数可以使用函数字面量、局部函数与对象表达式实现嵌套。 标签限定的 return 允许我们从外层函数返回。
       最重要的一个用途就是从 lambda 表达式中返回。回想一下我们这么写的时候，
       这个 return 表达式从最直接包围它的函数——foo 中返回：


     */
    private fun returnToLabel(){
        println("-------------returnToLabel-------------->")
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // 非局部直接返回到 returnToLabel() 的调用者
            print(it)
        }
        println("this point is unreachable")
    }

    /**
     * 如需从 lambda 表达式中返回，可给它加标签并用以限定 return。
     * 现在，它只会从 lambda 表达式中返回。通常情况下使用隐式标签更方便，因为该标签与接受该 lambda 的函数同名。

     */
    private fun partlyReturn(){
        println("-------------partlyReturn-------------->")
        listOf(1, 2, 3, 4, 5).forEach lit@{
            if (it == 3) return@lit// 非局部直接返回到 returnToLabel() 的调用者
            print(it)
        }
        println("\ndone with explicit label")
    }

    /**
     * 通常情况下使用隐式标签更方便，因为该标签与接受该 lambda 的函数同名。
     */
    private fun partlyReturnByImplictLabel(){
        println("-------------partlyReturnByImplictLabel-------------->")
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach// 非局部直接返回到 returnToLabel() 的调用者
            print(it)
        }
        println("\ndone with explicit label222")
    }



    /**
     * 将 when 的主语（subject，译注：指 when 所判断的表达式）捕获到变量中
     */
    private fun getWhenResult() {


        fun executeRequest():Result<String>{
            return Result.Success("ok")
        }

        when(val response = executeRequest()){
            is Result.Success -> response.value
            else -> throw Exception("failure")
        }
    }

    /**
     * 类型转换
     */
    private fun typeConvertUsage() {

        fun getStringLength(obj:Any):String?{
            if (obj is String && obj.length > 0){
                return obj
            }
            return null
        }
        //不安全转换,因为 getStringLength() 的返回值有可能是空
        val str1 = getStringLength(1) as String
        val str2:String? = getStringLength(1) as? String


        val maxLimt = 10
        val a = 1
        val b = 2
        val maxOrLimit = if (maxLimt > a) maxLimt else if (a > b) a else b
    }

    private fun trimUsage() {
        var text = """ABC
                |123
                |456"""

        //删掉原始字符串中的前导空格
        val trimMarginResult = text.trimMargin()
        //输出 ABC\n123\n456
        println("trimMarginResult:$trimMarginResult")

        text = """
               #  Kotlin
               #  Java
           """
        println("trimMarginResult2:${text.trimMargin("#")}")

        text.trimIndent()


        Array(5){
            (it * it).toString()
        }

        doubleArrayOf()

    }

    /**
     * 整数除法:整数间的除法总是返回整数。会丢弃任何小数部分。
     *
     */
    private fun integerDivisionUsage() {
        val x = 5 / 2
//        println(x == 2.5) // ERROR: Operator '==' cannot be applied to 'Int' and 'Double'
        println("5 / 2: ${x}")


        val x2 = 5 / 2 .toDouble() //2.5

        //如需返回浮点类型，请将其中的一个参数显式转换为浮点类型：
        println("5 / 2: ${x2}")


    }

    private fun objectExpressionUsage() {
        fun rentPrice(standardDays: Int, festivityDays: Int, specialDays: Int) {
            val dayRates = object {
                var standard: Int = 30 * standardDays
                var festivity: Int = 50 * festivityDays
                var special: Int = 100 * specialDays
            }
            val total = dayRates.standard + dayRates.festivity + dayRates.special
            println("total price:${total}")
        }
        rentPrice(10, 2, 1)
    }

    data class Model(val name: String, val email: String)

    private fun tryWithResourceUsage() {
        val stream = Files.newInputStream(Paths.get("/some/file.txt"))
        stream.buffered().reader().use { reader ->
            println(reader.readText())
        }


    }

    fun <T1,T2> foo(){

    }
    inline fun <reified T : Any> Gson.fromJson(json: JsonElement): T =
        this.fromJson(json, T::class.java)

    /**
     * try-catch 表达式
     */
    private fun tryCatchUsage() {
        val result = try {
            "test"
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }
        println("tryCatchUsage result:$result")
    }


    private fun ifNotNullUsage() {

        val files = File("Test").listFiles()
        //if not null
        println(files?.size) // 如果 files 不是 null，那么输出其大小（size）

        // 如果 files 为 null，那么输出“empty”
        println(files?.size ?: "empty")

        val fileSize = files?.size ?: kotlin.run {
            val someSize = 2
            someSize * 2
        }
        println(fileSize)

        //if null 执行一个语句
        val emailMap = mapOf<String, String>()
        val email = emailMap[""] ?: throw IllegalStateException("email is Missing")

        //在可能会空的集合中取第一元素
        val emailList: List<String> = listOf()
        val firstEmail = emailList.firstOrNull() ?: "not have an element"


        //映射可空值（如果非空的话）
        fun transformValue(obj: Any): String? {
            if (obj is String) {
                return obj
            }
            return null
        }

        val mapped: String = emailMap[""]?.let {
            transformValue(it)
        } ?: "defaultValue "
        println("email:$email, firstEmail:$firstEmail, mapped:$mapped")
    }


    class Test {
        operator fun get(param: String): String {
            return if (param.length > 5) "hello" else "world"
        }
    }

    private fun dataClassTrap() {
        val person = Person(name = "zhangsan")
        val deserialize = GsonUtils.toJson(person)
        val serialize = GsonUtils.fromJson(deserialize, Person::class.java)

        Logger.d("deserialize:${deserialize}, serialize:$serialize")
    }

    data class Point(val x: Int, val y: Int) {
        val list = mutableListOf<Point>()
    }

    operator fun Point.unaryMinus() = Point(-x, -y)

    //a + b
    operator fun Point.plus(point: Point): Point = Point(x + point.x, y + point.y)

    //a -b
    operator fun Point.minus(point: Point): Point = Point(x - point.x, y - point.y)

    // a * b
    operator fun Point.times(point: Point): Point = Point(x * point.x, y * point.y)

    // a / b
    operator fun Point.div(point: Point): Point = Point(x / point.x, y / point.y)

    //a ..b
    operator fun Point.rangeTo(point: Point): Point = Point(x / point.x, y / point.y)


    //a += b
    operator fun Point.plusAssign(point: Point) {
        this.list.add(point)
    }

    //a -= b
    operator fun Point.minusAssign(point: Point) {
        this.list.remove(point)
    }

    //a *= b
    operator fun Point.timesAssign(point: Point) {
    }

    //a /= b
    operator fun Point.divAssign(point: Point) {
    }


    val point = Point(10, 20)
    val point2 = Point(1, 2)
    private fun operatorOverLoadingUsage() {

        println("-Point:${-point}")

        //2元操作运算符
        println("+result:${point + point2}, -result:${point - point2}, * result:${point * point2}, / result:${point / point2}")


    }

    private fun initArrayUsage() {
        val messageArray = Array<String>(10) {
            "Message #$it"
        }
        println(messageArray)


        val messageList = List(10) {
            "Message #$it"
        }
        println(messageList)

    }


    private fun evisOperatorUsage() {
        arguments?.getString("key")?.takeIf { it.isNotEmpty() } ?: kotlin.run {
            toast("url为空")
        }
    }


    /**
     * stringLengthFunc 包含一个对匿名函数的引用，该函数将 String当做输入，
     * 将输入 String 的长度作为 Int 类型的返回
     *
     */
    val stringLengthFunc: (String) -> Int = { input ->
        input.length
    }

    private fun anonymousFun() {
        val test = fun() {
            println("uitest")
            //这里只会 return 调匿名函数本身
            return
        }
        test.invoke()

        val result: Int = stringLengthFunc("hello world")
        val result2: Int = stringLengthFunc.invoke("hello world")
        println()
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
    fun guide() {
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
    fun guide2() {
        println("guide start")
        crossInlineTeach {
            print("teach")
            //return is not allowed here
//            return
        }
        println("guide end")
    }

    fun guide3() {
        println("guide start")
        crossInlineTeach {
            print("teach")
            //return is not allowed here
            return@crossInlineTeach
        }
        println("guide end")
    }


    inline fun teach(abc: () -> Unit) {
        abc()
    }

    inline fun crossInlineTeach(crossinline abc: () -> Unit) {
        abc()
    }


    /**
     *  内部 lambda 是不允许中断外部函数执行的,所以 会打印出下面的hello
     */
    private fun lambdaInterrupt() {
        lambdaFunc {
            println("test1")
            return@lambdaFunc
        }
        lambdaFunc("Hello") {
            it.length
        }
    }

    private fun lambdaFunc(l: () -> Unit) {
        l.invoke()
    }

    private fun lambdaFunc(param: String, l: (String) -> Int) {
        val length = l.invoke(param)
        Logger.d("lambdaFunc length:$length")
    }

    private inline fun inlineFun(block: () -> Unit) {
        block.invoke()
    }

    private inline fun crossinlineFun(crossinline block: () -> Unit) {
        block.invoke()
    }


    /**
     * inline 的 lambda 可以中断外部函数调用, 因此不会输出后面的 hello
     */
    private fun inlineUsage2() {
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


    private fun getSetUsage() {
        println(string)
        string = "world"
        println(string)

        println("msg:$msg")
    }

    var string: String? = null
        get() {
            return field + "get"
        }
        set(value) {
            field = "$value set"
        }

    val msg: String? = null
        get() {
            return "$field, hello"
        }





    /**
     *  @SinceKotlin("1.1") public actual typealias LinkedHashMap<K, V> = java.util.LinkedHashMap<K, V> kotlin中的HashMap 就是映射的java的HashMap
     *  使用 typealias的好处就是 将来替换 HashMap的实现时就直接替换 kt包中的实现就行，调用者不需要变化
     */
    private fun typeAliasUsage() {
        val a: File = A("build")
        trainUsers(listOf())

        //使用 HashMap<K, V> 替代 java.util.HashMap<K, V>
        val map = HashMap<String, String>()

        val adapter = AlbumListAdapter{}
        adapter.onItemClickListener = { user ->
            println("onItemClick user:$user")
        }


        //为函数类型提供另外的别名：


    }
    //使用 UserList 替代 List<UserBean>
    fun trainUsers(userList: UserList) {

    }

    /**
     * 中缀表达式
     */
    private fun zhongZuiExpression() {
        var result1 = 5.vs(6)
        var result2 = 6.vs(5)
        println("result1:$result1, result2:$result2")
    }

    infix fun Int.vs(num: Int): Boolean = this - num < 0


    /**
     * 运算符重载
     */
    private fun testOperatorOverload() {
        //..  重载了 rangeTo
        for (i in 1..100 step 20) {
            println("testOperatorOverload $i")
        }
    }


    private fun getTextView(): TextView? {
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
    private fun letUsage() {
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

        println("testReturn:${letReturn()}")

    }

    private fun letReturn(): String? {
        fun test(): String? {
            return "Hello"
        }

        test()?.let {
            return it
        }
        return null

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
    private fun withUsage() {
        val user = UserBean("Tom")
        with(user) {
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
    private fun runUsage() {
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
    private fun applyUsage() {
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
    private fun alsoUsage() {
        val instance = KotlinMain.instance.getKotlinMain()
        Logger.d("instance:$instance")

    }


}

typealias MyHandler = (Int,String,Any) -> Unit


//用 A 来代表 File
typealias A = File


typealias UserList = List<UserBean>

typealias MouseClickHandler = (String, Int) -> Unit

