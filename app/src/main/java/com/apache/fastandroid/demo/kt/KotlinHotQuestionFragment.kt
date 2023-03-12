package com.apache.fastandroid.demo.kt

/**
 * Created by Jerry on 2022/6/2.
 * nrnenrbrnf://mp.weixin.qq.com/s/K6sJqROCsb3ihESx70a85w
 */

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentHotQuestionBinding
import com.apache.fastandroid.demo.kt.staticusage.Foo
import com.apache.fastandroid.demo.kt.staticusage.ObjectClass
import com.apache.fastandroid.demo.kt.staticusage.topLevelFun1
import com.apache.fastandroid.demo.kt.staticusage.topLevelFun2
import com.tesla.framework.ui.fragment.BaseBindingFragment


class KotlinHotQuestionFragment:BaseBindingFragment<FragmentHotQuestionBinding>(FragmentHotQuestionBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnForCyclic.setOnClickListener {
            forCyclicUsage()
        }
        mBinding.btnIterable.setOnClickListener {
            iterableUsage()
        }

        mBinding.btnSequence.setOnClickListener {
            sequenceUsage()
        }

        mBinding.btnCallStatic.setOnClickListener {
            callStaticUsageInKt()
        }
        mBinding.btnCallStaticMember.setOnClickListener {
            callStaticMemberUsageInKt()
        }





    }

    private fun callStaticMemberUsageInKt() {
        Foo.name
        Foo.name2
        Foo.name3

    }

    private fun callStaticUsageInKt() {
        Foo.f()
        Foo.f2()

        ObjectClass.f()
        ObjectClass.f2()

        topLevelFun1()
        topLevelFun2()
    }



    private val peoples = IntArray(10){
        it * 2
    }

    /**
     * Iterable 对应 Java 的 java.lang.Iterable, Iterable 会立即处理输入的元素，
     * 并返回一个包含结果的新集合
     * 1.首先通过 filter 函数检查每个人的年龄，将结果放入到一个新的结果集中。
     * 2.通过 map 函数对上一步得到的结果进行名字映射，然后生成一个新的列表 list<String>。
     * 3.通过 take 函数返回前 5 个元素，得到最终的结果集。
     */
    private fun iterableUsage() {
        val results = peoples.filter {
            println("filter ${it}")
            it > 6
        }.map {
            println("map ${it}")
            "person:${it}"
        }.take(5)
        println("iterable results:${results}")
    }


    /**
     * Sequence 是 Kotlin 中一个新的概念，用来表示一个延迟计算的集合。Sequence 只存储操作过程，
     * 并不处理任何元素，直到遇到终端操作符才开始处理元素
     * 在这个例子中， toList() 表示终端操作符，filter 、 map 、 take 都是中间操作符，返回 Sequence
     * 实例，当 Sequence 遇到中间操作符时，只是存储操作过程，并不参与计算，直到遇到 toList()。
     * Sequence 的好处它不会生成中间结果集，直接对原始列表中的每一个人重复这个步骤，
     * 直到找到 5 个人，返回最终的结果集。
     */
    private fun sequenceUsage() {
        val results = peoples.asSequence().filter {
            println("filter ${it}")
            it > 6
        }.map {
            println("map ${it}")
            "person:${it}"
        }.take(5).toList()
        println("sequence results:${results}")
    }


    private var args = listOf(1,2,3)

    private fun forCyclicUsage() {
        val builder = StringBuilder()
        //方法 1
        for (i in 0..args.size -1){
            builder.append(args[i])
            if (i != args.size - 1){
                builder.append(",")
            }
        }
        println("way1 : $builder")
        builder.clear()
        //方法2
        for (i in 0..args.lastIndex){
            builder.append(args[i])
            if (i != args.lastIndex){
                builder.append(",")
            }
        }
        println("lastIndex result : $builder")

        builder.clear()
        //方法3
        for (i in 0 until args.size){
            builder.append(args[i])
            if (i != args.size - 1){
                builder.append(",")
            }
        }
        println("until result : $builder")

        builder.clear()
        //方法2
        for (i in args.indices){
            builder.append(args[i])
            if (i != args.indices.last){
                builder.append(",")
            }
        }
        println("indices result : $builder")

        builder.clear()
        for (arg in args){
            builder.append(arg)
            builder.append(",")
        }
        println("in result : $builder")

        builder.clear()
        args.forEach { arg ->
            builder.append(arg)
            builder.append(",")
        }
        println("forEach result : $builder")

        builder.clear()
        args.forEachIndexed() { index,arg ->
            builder.append(arg)
            if (index != args.lastIndex){
                builder.append(",")
            }
        }
        println("forEachIndexed result : $builder")

        builder.clear()
        for ((index, arg) in args.withIndex()){
            builder.append(arg)
            if (index != args.lastIndex){
                builder.append(",")
            }
        }
        println("withIndex result : $builder")

    }
}