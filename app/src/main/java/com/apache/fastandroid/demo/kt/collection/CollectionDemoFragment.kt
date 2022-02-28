package com.apache.fastandroid.demo.kt.collection

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentKtCollectionBinding
import com.tesla.framework.ui.fragment.BaseVMFragment

/**
 * Created by Jerry on 2022/2/27.
 */
class CollectionDemoFragment:BaseVMFragment<FragmentKtCollectionBinding>(FragmentKtCollectionBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnIntRange.setOnClickListener {
            intRangeUsage()
        }
        viewBinding.btnPartition.setOnClickListener {
            partition()
        }
        viewBinding.btnCreatePair.setOnClickListener {
            createPair()
        }
        viewBinding.btnTriple.setOnClickListener {
            triple()
        }
        viewBinding.btnDestructPairTriple.setOnClickListener {
            destructPairTriple()
        }
        viewBinding.btnSum.setOnClickListener {
            sumUsage()
        }

        viewBinding.btnHashMap.setOnClickListener {
            hashMapUsage()
        }
    }

    private val numberMap = hashMapOf("one" to 1, "two" to 2, "three" to 3)

    private fun hashMapUsage() {
        val defualt = numberMap.getOrDefault("four", "sorry,I don't know")
        val getOrElse = numberMap.getOrElse("four"){
            println("")
            "no contains value for this"
        }
        numberMap.getOrPut("five"){
            println("not exisit key = five, put an element to map")
            5
        }
        println("default: ${defualt}, getOrElse:${getOrElse}, numberMap:${numberMap}")

    }

    private fun sumUsage() {
        val list = mutableListOf(1,3,2,4)
        println(list.sum())

        val list2 = mutableListOf("a","bb","ccc")
        //用一个 lambda 表达式来作为参数 替代 java 接口
        val sumOf = list2.sumOf {
            it.length
        }
        println(sumOf)
    }

    private fun destructPairTriple() {
        val pair = "fish net" to "cating fish"
        val (tool, use) = pair
        println("$tool is used for $use")

        val elements = Triple(1,2,"hello")
        val (element1, element2, element3) = elements
        println("$element1 $element2 $element3")
    }

    private fun triple() {
        val triple = Triple("A","B","C")
        val list = triple.toList()
        println("${triple.toString()}")
        println(list)
    }

    private fun createPair() {
        val equipment = "one" to "apple"
        println("${equipment.first} used for ${equipment.second}")

        val equipment2 = ("fish net" to "catching fish") to "equipment"
        println("${equipment2.first} is ${equipment2.second}\n")
        println("${equipment2.first.second}")
    }

    private fun intRangeUsage() {
        val random = (1..6).random()
        println(random)
    }

    private fun partition(){
        val fruits = mutableListOf("apple","pear", "banana", "aaa","bbb")
        //将一个集合返回两个list,可用于一个函数返回两个对象的场景
        val pair = fruits.partition {
            it.length <= 4
        }
        println("first:${pair.first}, second:${pair.second}")
    }

}