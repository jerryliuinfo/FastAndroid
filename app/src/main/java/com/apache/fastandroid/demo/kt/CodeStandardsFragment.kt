package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.databinding.FragmentCodeStandardsBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/8/12.
 * https://book.kotlincn.net/text/coding-conventions.html
 */
class CodeStandardsFragment :
    BaseBindingFragment<FragmentCodeStandardsBinding>(FragmentCodeStandardsBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnClassContentOrder.setOnClickListener {
            classContentOrder()
        }

        mBinding.btnBackField.setOnClickListener {
            backFieldUsage()
        }

        mBinding.btnClassHeader.setOnClickListener {
            classHeader()
        }
    }

    /**
     * 类头
     * 具有少数主构造函数参数的类可以写成一行：
     * 具有较长类头的类应该格式化，以使每个主构造函数参数都在带有缩进的独立的行中。
     * 另外，右括号应该位于一个新行上。如果使用了继承，那么超类的构造函数调用或者所实现接口的列表应该与右括号位于同一行：
     *
     */
    private fun classHeader() {
        val person1 = Person1(10, "xiaoming")
        val person2 = Person2(10, "xiaoming", "Li")
    }

    /**
     * 具有少数主构造函数参数的类可以写成一行：
     * @constructor
     */
    private open class Person1(id: Int, name: String)

    /**
     * 具有较长类头的类应该格式化，以使每个主构造函数参数都在带有缩进的独立的行中。
     * @constructor
     */
    private class Person2(
        id: Int,
        name: String,
        sureName: String
    ) : Person1(id, name),
        java.io.Serializable

    /**
     * 如果一个类有两个概念上相同的属性，一个是公共 API 的一部分，
     * 另一个是实现细节，那么使用下划线作为私有属性名称的前缀：
     */
    private fun backFieldUsage() {
        val users = BackField().users
        println("users:$users")

        val oneMillion = 1_00_000

        val a: Int? = 1




    }

    var count:Int = 0
        set(value) {
            if (value > 0){
                field = value
                //不能使用 count = value 的方式会死循环导致栈溢出
//                count = value
            }
        }


    private class BackField {
        private val _users = MutableLiveData<String>().apply {
            value = "Hello"
        }
        val users = _users


    }

    /**
     * 一个类的内容应按以下顺序排列：

    属性声明与初始化块
    次构造函数
    方法声明
    伴生对象
    不要按字母顺序或者可见性对方法声明排序，也不要将常规方法与扩展方法分开。而是要把相关的东西放在一起，这样从上到下阅读类的人就能够跟进所发生事情的逻辑。选择一个顺序（高级别优先，或者相反）并坚持下去。

    将嵌套类放在紧挨使用这些类的代码之后。如果打算在外部使用嵌套类，而且类中并没有引用这些类，那么把它们放到末尾，在伴生对象之后。
     */
    private fun classContentOrder() {
        val order = ClassOrder("jerry")
        order.doGreeting()
    }

    private class ClassOrder(val name: String) {
        private var greeting: String = "hello"

        init {
            println("$greeting:$name")
        }

        constructor(name: String, age: Int) : this(name)


        fun doGreeting() {
            println("Hi, ${name} ")
        }

        companion object {
            fun test() {
            }

        }
    }
}