package com.apache.fastandroid.demo.kt

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.KtOfficialGrammerBinding
import com.apache.fastandroid.demo.kt.bean.Fruit
import com.apache.fastandroid.demo.kt.bit.BitDemo
import com.apache.fastandroid.demo.kt.common.GsonDemo
import com.apache.fastandroid.demo.kt.constructor.Asiatic
import com.apache.fastandroid.demo.kt.constructor.Lion
import com.apache.fastandroid.demo.kt.enum.Colour
import com.apache.fastandroid.demo.kt.enum.State
import com.apache.fastandroid.demo.kt.extensions.*
import com.apache.fastandroid.demo.kt.genericity.MultableStack
import com.apache.fastandroid.demo.kt.genericity.mutableStackOf
import com.apache.fastandroid.demo.kt.hignorder.myWith
import com.apache.fastandroid.demo.kt.sealed.*
import com.tesla.framework.kt.maxAge
import com.tesla.framework.kt.onSingleClick
import com.tesla.framework.kt.times
import com.tesla.framework.ui.fragment.BaseBindingFragmentRef
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Created by Jerry on 2021/10/18.
 * done
 */
class KotlinOfficalGramerFragment : BaseBindingFragmentRef<KtOfficialGrammerBinding>() {
    companion object {
        private const val TAG = "KotlinOfficalGramerFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnElvis.onSingleClick {
            elvisUsage()
        }

        mBinding.btnCirculation.setOnClickListener {
            iterator()
        }
        mBinding.btnWhen.setOnClickListener {
            testWhen()
        }
        mBinding.btnRange.setOnClickListener {
            testRange()
        }
        mBinding.btnNullCheck.setOnClickListener {
            printProduct("6", "7")
            printProduct("a", "7")
            printProduct("a", "b")

            printProduct2("6", "7")
            printProduct2("a", "7")
            printProduct2("a", "b")
        }
        mBinding.btnTypeCast.setOnClickListener {
            typeCheckCast()
        }
        mBinding.btnIfCheck.setOnClickListener {
            ifCheck()
        }
        mBinding.btnSingleExpression.setOnClickListener {
            singleExpression()
        }

        mBinding.btnTry.setOnClickListener {
            tryWithResource()
        }
        mBinding.btnComUsage.setOnClickListener {
            commonUsage()
        }
        mBinding.btnInfix.setOnClickListener {
            infixFunc()
        }
        mBinding.btnGeneric.setOnClickListener {
            genericUsage()
        }
        mBinding.btnConstructor.setOnClickListener {
            constructor()
        }

        mBinding.btnDataclass.setOnClickListener {
            dataClassUsage()
        }
        mBinding.btnEnum.setOnClickListener {
            enumUsage()
        }
        mBinding.btnSealdclass.setOnClickListener {
            sealedClassUsage()
        }

        mBinding.btnHighOrderFunction1.setOnClickListener {
            highOrderFunctionAsParamter()
        }

        mBinding.btnLambda.setOnClickListener {
            lambdaUsage()
        }
        mBinding.btnExtensionFunAndProperty.setOnClickListener {
            extensionFunAndProperty()
        }

        mBinding.btnDestructor.setOnClickListener {
            destructorUsage()
        }

        mBinding.btnReturnBreak.setOnClickListener {
            returnBreakUsage()
        }

        mBinding.btnReturnOutside.setOnClickListener {
            returnFromOutSide()
        }

        mBinding.btnReturnInside.setOnClickListener {
            returnFromInSide()
        }

        mBinding.btnReturnNestedInnerclass.setOnClickListener {
            returnNestedInnerClass()
        }
        mBinding.btnTryReturnValue.setOnClickListener {
            returnValueByTry("2")
            returnValueByTry("hello")
        }

        mBinding.btnCheckRequire.setOnClickListener {
            checkRequireUsage()
        }

        "hello" to User(1, "jerry")


    }

    private var mPerson: Person? = null

    private fun elvisUsage() {
        val user: User? = getUser()
        val length = user?.name?.length ?: -1
        println("length:${length}")

        val name = user?.name ?: "unknow"
        println("name:${name}")

        arguments?.getString("name")?.takeIf {
            it.isNotEmpty()
        } ?: kotlin.run {
            println("empty url")
        }

        val state: User? = getState()
        val result = when (state?.id) {
            1 -> true
            else -> false
        }
        println("result:$result")

        mPerson?.apply {
            firstName = "update"
        } ?: kotlin.run {
            Person("origin").also {
                mPerson = it
            }
        }
        println("mPerson:${mPerson?.firstName}")

        val leng = getMessage()?.length ?: 0
        toast(leng.toString() + " show")

        val list = listOf(1, 2, 3)
        list.forEach {
            return
        }

        for (i in 0 until 10) {
            return
        }


        kotlin.run {
            ""
        }


        val sp = activity?.getSharedPreferences("userInfo",Context.MODE_PRIVATE)?:return
        with(sp.edit()){
            putInt("age", 10)
            apply()
        }
    }

    private fun getMessage(): String? {
        return null
    }

    private fun getState(): User? {
        return null
    }

    private fun getUser(): User? {
        val randomNum = Random.nextInt(10)
        if (randomNum > 5) {
            return User(randomNum, "Tom")
        }
        return null

    }

    private fun checkRequireUsage() {
        val age = 10
        check(age > 10)
        check(age > 10) {
            "check age is less than  ${age}"
        }
        require(age > 10)
        require(age > 10) {
            "require age is less than  ${age}"
        }
    }

    private fun lambda(
        load: () -> Unit = {},
        onSuccess: (user: User) -> Unit = {},
        onError: (errorCode: Int?, msg: String) -> Unit = { _, _ -> }
    ) {
        load()
        when {
            Random.nextInt(10) % 2 == 0 -> onSuccess(User(10, "zhangsan"))
            else -> onError(100, "token invalid")
        }

    }


    private fun test(user: User?) {
        user?.name == "zhangsan" ?: println("not zhangsan")
    }

    private fun returnValueByTry(obj: String) {
        val result = try {
            obj.toInt()
        } catch (e: Exception) {
            null
        }
        println(result)
    }


    private fun returnNestedInnerClass() {
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@loop
                println(it)
            }
        }
        println("done with nested loop")
    }

    fun returnFromOutSide() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // 非局部直接返回到 foo() 的调用者
            println(it)
        }
        println("this point is unreachable")
    }

    fun returnFromInSide() {
        listOf(1, 2, 3, 4, 5).forEach abc@{
            if (it == 3) return@abc // 非局部直接返回到 foo() 的调用者
            println(it)
        }
        println(" done with explicit label")
    }

    private fun returnBreakUsage() {
        loop@ for (i in 1..3) {
            println("returnBreakUsage i:$$i")
            for (j in 1..3) {
                if (j >= 2) {
                    println("j:${j} break -->")
                    break@loop
                }
                println("j:$$j")
            }
        }
        println("returnBreakUsage done")
    }

    private fun destructorUsage() {
        val (x, y, z) = arrayOf(5, 10, 15)
        println("x:$x, y:$y, z:$z")

        val map = mapOf("Alice" to 21, "Bob" to 25)

        for ((name, age) in map) {
            println("name:$name,age:$age")
        }
        val (min, max) = findMinMax(listOf(3, 6, 4, 5))
        println("min:$min, max:$max")

    }

    private fun findMinMax(list: List<Int>): Pair<Int, Int> {
        val min: Int = list.minOrNull() ?: 10
        val max: Int = list.maxOrNull() ?: 10
        return Pair(min, max)
    }


    private fun extensionFunAndProperty() {
        val order = Order(listOf(Item("Bread", 25f), Item("Wine", 29f), Item("Water", 12f)))
        println("max price item name:${order.maxPricedItemName()}, max priced item value:${order.maxPricedItemValue()}, items:${order.commaDelimitedItemNames}")

        val transform: (Item) -> Pair<String, Float> = { item ->
            Pair(item.name, item.price)
        }
        val map = order.items.associate(transform)
        println(map)

        val users = arrayOf(User(28, "zhangsan"), User(14, "lisi"), User(4, "wangwu"))
        val lastMaxAge = users.asIterable().maxAge {
            it.id.toLong()
        }
    }

    private fun lambdaUsage() {
        val upperCase1: (String) -> String = { str: String ->
            str.uppercase()
        }
        val upperCase2: (String) -> String = { str ->
            str.uppercase()
        }
        val upperCase3 = { str: String -> str.uppercase() }
//        val upperCase4 = {str -> str.uppercase()}

        val upperCase5: (String) -> String = { it.uppercase() }
        val upperCase6: (String) -> String = String::uppercase
        println(
            "upperCase1:${upperCase1("Hello")},upperCase2:${upperCase2("Hello")},upperCase3:${
                upperCase3(
                    "Hello"
                )
            }" +
                    ",upperCase5:${upperCase5("Hello")},upperCase6:${upperCase6("Hello")},"
        )
        lambda(load = { println("load")}, onSuccess = {
            println("onsuccess")
        },onError = { _,_ ->
            println("onError")
        })

    }


    /**
     * 函数作为参数
     */
    fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
        return operation(x, y)
    }

    fun sum(x: Int, y: Int) = x + y

    /**
     * 函数作为返回值
     */
    fun operation(): (Int) -> Int {
        return ::square
    }

    fun square(x: Int) = x * x

    /**
     * 函数作为参数
     */
    private fun highOrderFunctionAsParamter() {
        val sumResult = calculate(4, 5, ::sum)
        val mulResult = calculate(4, 5) { x, y ->
            return@calculate x * y
        }
        println("sumResult:${sumResult}, mulResult:${mulResult}")

        val fruit = Fruit("apple")
        myWith(fruit.name) {
//            var capitize =  replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            var capitize = capitalize()
            println(capitize)
        }

        //函数作为返回值
        val func = operation()
        println(func(2))


        //带参数，没有返回值的函数
        var myPrint: (msg: String) -> Unit = { msg -> Log.e("xxx5", msg) }
//带参数，参数为msg，有返回值的函数类型为Strnig
        var hello: (msg: String) -> String = { "hello ${it.uppercase()}!!" }


        //函数作为参数，返回String 但是不需要return 直接将要返回的值放在最后一行
        fun method1(method: () -> String) {
            method.invoke()
        }

        //函数作为参数，Unit表示没有返回值
        fun method2(method: () -> Unit) {
            method.invoke()
        }

        //函数作为参数，有一个输入参数时。不能直接带给它，需要另外一个参数传进来。
        fun <T> method3(msg1: T, method: (msg: T) -> Unit) {
            method.invoke(msg1)
        }


        //函数作为返回值
        fun method4(str: String): () -> Unit {
            Log.e("xxx4", "这一部分不返回，直接运行")
            return {
                var strNew = str.uppercase(Locale.getDefault())
                Log.e("xxx4", strNew)
            }
        }

        fun test(a: Int, b: (num1: Int, num2: Int) -> Int): Int {
            return a + b.invoke(3, 5) //8
        }


        method1 {
            var hello = "hello world"
            Log.e("xxx1", hello)
            hello
        }

        method2 {
            var hello = "hello world"
            Log.e("xxx2", hello)
        }

        method3("hello world") { msg ->
            Log.e("xxx3", msg)
        }

        //函数作为返回值
        var method4 = method4("hello world")
        method4.invoke()

        //使用定义的函数
        myPrint.invoke("hello world")

        var greet = hello("zhang san")
        myPrint.invoke(greet)

        //参数为一个数字 加 一个函数
        test(2) { a: Int, b: Int ->
            var num = (a + b) * 5
            num
        }


    }


    /**
     * 密封类允许您限制继承的使用。一旦声明了一个类密封，
     * 它只能从声明密封类的同一个包中进行子类化。它不能在
     * 声明密封类的包之外被子类化。
     */
    private fun sealedClassUsage() {
        println(greetMammal(Cat("Snowy")))
        println(greetMammal(Human("Jerry", "Enginer")))
    }


    private fun enumUsage() {
        val message = when (State.RUNNING) {
            State.IDLE -> "It's idle"
            State.RUNNING -> "It's running"
            State.FINISHED -> "It's finished"
        }
        println(message)


        val red = Colour.RED
        println(red)
        println("red contains red:${red.containsRed()}")
        println("blue contains red:${Colour.BLUE.containsRed()}")
        println("blue contains red:${Colour.BLUE.containsRed()}")
        println("yellow contains red:${Colour.YELLOW.containsRed()}")
    }

    private fun dataClassUsage() {
        val user1 = User(1, "Alex")
        val user2 = User(1, "Alex")
        val user3 = User(2, "Max")
        println("user == user2: ${user1 == user2}")
        println("user == user3: ${user1 == user3}")

        println("user hashCode: ${user1.hashCode()}, secondUser hashCode: ${user2.hashCode()},thirdUser hashCode: ${user3.hashCode()}")

        println(user1.copy())
        println(user1 === user1.copy())
        println(user1.copy(name = "Max"))
        println(user1.copy(id = 3))
        println("id:${user1.component1()}, name:${user1.component2()}")
    }

    internal class Animal(val name: String)

    internal class Zoo(val animals: List<Animal>) {

        operator fun iterator(): Iterator<Animal> {
            return animals.iterator()
        }
    }


    private fun constructor() {
        val lion: Lion = Asiatic("Rufo")
        lion.sayHello()
    }

    private fun genericUsage() {
        //泛型类
        val stack = MultableStack<String>("aa", "bb", "cc")
        stack.push("dd")
        println(stack)

        //泛型函数
        val stack2 = mutableStackOf("hello", "world")
        println(stack2)

        val generic1 = object : IGeneric<String, Int> {
            override fun apply(t: String): Int {
                return t.length
            }
        }

        val length1 = generic1.apply("zhangsan")
        val length2 = generic1.apply("lisi")
        println("length1:$length1, length2:$length2")

    }


    interface IGeneric<T, R> {
        fun apply(t: T): R
    }


    private class Person(var firstName: String) {
        val likedPeople = mutableListOf<Person>()
        infix fun likes(other: Person) {
            likedPeople.add(other)
        }
    }


    /**
     * infix函数(中缀函数)需要几个条件:
    - 只有一个参数
    - 在方法前必须加infix关键字
    - 必须是成员方法或者扩展方法
     */
    private fun infixFunc() {
        println(2 times "Bye")

        val pair = "1" to "one"
        println(pair)

        infix fun String.onto(other: String) = Pair(this, other)
        val myPair = "2" onto "two"
        println(myPair)

        val lily = Person("lily")
        val lucy = Person("lucy")
        lily likes lucy
        println(lily.likedPeople.size)

        val zhangsanInfo = "zhangsan" to 12
        println("zhangsan name:${zhangsanInfo.first}, age:${zhangsanInfo.second}")

        val triple = Triple("zhangsan", "lisi", "wangwu")
        val nameList = triple.toList()
        println("triple first:${triple.first}, second:${triple.second},third:${triple.third},nameList:${nameList}")

    }

    private fun isOverThan(str: Any): Boolean? {
        if (str is String) {
            return str.length > 3
        }
        return null
    }

    private fun commonUsage() {
        val result: Boolean? = isOverThan("abc")
        if (result == true) {
            println("b is true")
        } else {
            println("b is false or null")
            // `b` 是 false 或者 null
        }

        //交换两个变量
        var a = 1
        var b = 2
        a = b.also { b = a }
        println("a:$a, b:$b")

        //可变参数

        fun printAll(vararg messages: String) {
            for (m in messages) {
                println(m)
            }
        }
        printAll("Hello", "World")

        //== 和 === 检测
        val authors = setOf("aa", "bb", "cc")
        val writers = setOf("cc", "aa", "bb")
        println("==: ${authors == writers}") // true
        println("===: ${authors === writers}") // true

        max(1, 2)

        val value1 = 4.89
        println("roundInt: ${value1.roundToInt()}")

        bitOperation()

        gsonUsage()
    }

    fun gsonUsage() {
        val jsonDemo = GsonDemo()
        jsonDemo.jsonDataIsNull()
        jsonDemo.jsonDataContentIsNull()
        jsonDemo.serializeNull()
        jsonDemo.serializeNotNull()
    }


    private fun bitOperation() {
        //位操作
        val bit = BitDemo()
        val type = bit.type

        var bitResult = type or bit.STATUS_BARS  //0000 0001
        println("bitResult1 :${bitResult}")

        //   0000 0001
        //   0000 0010
        // = 0000 0011

        bitResult = type or bit.NAVIGATION_BARS  //3 = 0000 0011

        println("bitResult2 :${bitResult}")

        //  0000 0011
        //  0000 0100
        // =0000 0111

        bitResult = bitResult or bit.CAPTION_BAR  // 0000 0101
        println("bitResult3 :${bitResult}")

        bitResult = bitResult and bit.CAPTION_BAR.inv()
        println("bitResult4 :${bitResult}")

        bitResult = (bitResult and bit.CAPTION_BAR)
        println("bitResult5 :${bitResult}")


        println("bitResult6 :${(bitResult != 0)}")
    }


    private fun tryWithResource() {
        val stream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.newInputStream(Paths.get("/Users/jerry/Documents/xmind/file.txt"))
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        stream.buffered().reader().use { reader ->
            println(reader.readText())
        }
    }

    private fun singleExpression() {
        transform("Red")
        transform("Pure")
    }


    private fun transform(color: String): Any {
        return when (color) {
            "Red" -> 0
            "Green" -> 1
            "Blue" -> 2
            else -> "unknow"
        }
    }

    private fun ifCheck() {
        val files = File("/Users/jerry/Documents/xmind").listFiles()
        //if-not-null 缩写 如果 files 不是 null，那么输出其大小size,否则返回 null
        println(files?.size)

        //if-not-null-else
        println(files?.size ?: "empty")

        //To calculate the fallback value in a code block, use `run`
        val size = files?.size ?: kotlin.run {
            5
        }
        println(size)
        //在可能会空的集合中取第一元素
        val emails = emptyList<String>()
        emails.firstOrNull() ?: "empty"


        var source = "abc"
        var value: Int? = parseInt(source)
        var mapped = value?.let {
            "hi $it"
        } ?: "$source is not a number"
        println(mapped)

        source = "123"
        value = parseInt(source)
        var mapped2 = value?.let {
            "hi $it"
        } ?: "$source is not a number"
        println(mapped2)

    }


    private fun typeCheckCast() {
        fun printLength(obj: Any) {
            if (obj !is String) {
                return
            }
            //obj is String
            println("obj length:${obj.length}")

            println("Getting the length of ${obj}, result:${getStringLength(obj) ?: "Error: the obj is not a string"} ")
        }
        printLength("Incomprehensibilities")
        printLength(1000)
        printLength(listOf(Any()))


        fun safeCast(x: Any) {
            val y: String? = x as? String?
            println(y)
        }
        safeCast(100)
        safeCast("hello")
    }


    private fun getStringLength(obj: Any): Int? {
        //`obj` 在 `&&` 右边自动转换成 `String` 类型
        if (obj is String && obj.length > 0) {
            // `obj` 在该条件分支内自动转换成 `String`
            return obj.length
        }
        return null
    }

    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }


    private fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)
        if (x != null && y != null) {
            println(x * y)
        } else {
            println("$arg1 or $arg2 is not a number")
        }
    }

    private fun printProduct2(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)
        if (x == null) {
            println("wrong number format in ${arg1}")
            return
        }
        if (y == null) {
            println("wrong number format in ${arg2}")
            return
        }
        println(x * y)
    }


    private fun testRange() {
        val x = 10
        val y = 9
        //in 是否处于
        if (x in 1..y + 1) {
            println("fit in range")
        }

        val list = listOf("a", "b", "c")
        if (-1 in 0..list.lastIndex) {
            println("-1 is out of range")
        }
        if (list.size !in list.indices) {
            println("list size is out of valid list indices range, too")
        }
        //左开右开
        for (i in 1..3) {
            println(i)
        }
        println("until --->")
        //左开右闭
        for (i in 1 until 3) {
            println(i)
        }
        println("step --->")
        for (i in 1..5 step 2) {
            println(i)
        }

        println("down to --->")
        for (i in 5 downTo 2 step 2) {
            println(i)
        }
    }

    private fun testWhen() {
        println(whenUsage(1))
        println(whenUsage("Hello"))
        println(whenUsage(1000L))
        println(whenUsage(2))
        println(whenUsage("other"))
    }

    fun whenUsage(obj: Any): String {
        return when (obj) {
            1 -> "one"
            "Hello" -> "Greeting"
            is Long -> "Long"
            !is String -> "Not a String"
            else -> "Unknow"
        }
    }

    private fun iterator() {
        val list = listOf("apple", "orange", "kiwifruit")
        println("in  ---->")
        for (item in list) {
            println(item)
        }
        println("indices  ---->")
        for (index in list.indices) {
            println(index)
        }
        println("withIndex  ---->")
        for ((index, item) in list.withIndex()) {
            println("index:$index, item:$item")
        }


        when {
            "orange" in list -> "juicy"
            "apple" in list -> "apple is fine too"
        }

        //遍历map
        val map = mutableMapOf("a" to 1, "b" to 2)
        println("key a: ${map["a"]}")
        map["a"] = 10
        println("key a: ${map["a"]}")

        for ((k, v) in map) {
            println("${k}: ${v}")

        }

    }


    private fun comUsage() {
        val duration = 342L
        val timeUnit = TimeUnit.SECONDS
        val milles = timeUnit.toMillis(duration)
        println("milles: ${timeUnit}")
    }


}