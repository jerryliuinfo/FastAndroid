package com.apache.fastandroid.demo.kt.collection

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentKtCollectionBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.ui.fragment.BaseVMFragment
import okhttp3.internal.filterList

/**
 * Created by Jerry on 2022/2/27.
 */
class CollectionDemoFragment:BaseVMFragment<FragmentKtCollectionBinding>(FragmentKtCollectionBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnTransform.setOnClickListener {
            transoform()
        }
        viewBinding.btnElementOperator.setOnClickListener {
            elementOperator()
        }

        viewBinding.btnOrderOperator.setOnClickListener {
            orderTransform()
        }

        viewBinding.btnMapOperator.setOnClickListener {
            mapOperator()
        }

        viewBinding.btnProduceOperator.setOnClickListener {
            produceOperator()
        }

        viewBinding.btnStatics.setOnClickListener {
            staticsUsage()
        }


        viewBinding.btnFilter.setOnClickListener {
            filter()
        }



        viewBinding.btnIntRange.setOnClickListener {
            intRangeUsage()
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


        viewBinding.btnHashMap.setOnClickListener {
            hashMapUsage()
        }
    }

    private fun mapOperator() {
        println("map:"+ users.map {
            it.name
        })
        println("mapNotNull:"+ users.mapNotNull {
            it.name
        })
        //根据条件合并两个集合，组成一个新的集合
        val flatMap = users.flatMap {
            listOf(it.name, "plus-${it.name}")
        }
        println("flatMap:$flatMap")


        val groupBy = users.groupBy {
            if (it.age  != null && it.age!! > 10){
                "group1"
            }else{
                "group2"
            }
        }
        println("groupBy:$groupBy")
    }

    private fun staticsUsage(){
        //判断是不是一个集合，若是，则在判断集合是否为空，若为空则返回false,反之返回true,若不是集合，则返回hasNext
        println("any():${list.any()}")

        //判断集合中是否存在满足条件的元素。若存在则返回true,反之返回false
        println("any{}:"+list.any {
            it > 3
        })

        //判断集合中的所有元素是否都满足条件。若是则返回true,反之则返回false
        println("all:"+list.all {
            it > 3
        })
        //和any()函数的作用相反
        println("none:" + list.none())
        println("none{...}" + list.none{ it > 3})

        println("max1:"+list.maxOfOrNull {
            12
        } + ", max2:"+list.maxOfOrNull {
            4
        })
        //获取方法处理后返回结果最大值对应那个元素的初始值，如果没有则返回null
        println("maxByOrNull:"+list.maxByOrNull {
            2
        })
        //获取集合中最小的元素，若为空元素集合，则返回null
        println("minOfOrNull:"+list.minOfOrNull {
            3
        })
        println("minByOrNull:"+list.minByOrNull {
            3
        })

        val users = listOf(UserBean("user1",1),UserBean("user2",2),UserBean("user3",3))
        val mapList:List<Int> = users.map {
            it.age!!
        }
        println("reduceOrNull"+mapList.reduceOrNull { acc, i ->
            acc + i
        })


        val list = listOf(1,2,3,4,5)
        //从集合中的第一项到最后一项的累计操作
        println("reduce:"+list.reduce { acc, i ->
            acc + i
        })
        //从集合中的最后一项到第一项的累计操作
        println("reduceRight:"+list.reduceRight { i, acc ->
            i + acc
        })

        //和reduce{}作用相同，只是其可以操作元素的下标(index)
        println(list.reduceIndexed { index, acc, item ->
            acc + item
        })


        //和reduce{}类似，但是fold{}有一个初始值
        println(list.fold(1) {result, next -> result  + next})

        //foldRight{...} : 和reduceRight{}类似，但是foldRight{}有一个初始值
        println(list.foldRight(2){item, acc ->
            acc + item
        })

        println("sum:"+list.sum())

        val list2 = mutableListOf("a","bb","ccc")
        //用一个 lambda 表达式来作为参数 替代 java 接口
        println("sumOf"+list2.sumOf {
            it.length
        })

        println("average:"+list.average())

    }

    private fun orderTransform() {
        list.reverse()
        //反序。即和初始化的顺序反过来。
        println("reverse: $list")

        list.sort()
        //自然升序
        println("sort: $list")

        //根据条件升序，即把不满足条件的放在前面，满足条件的放在后面
        list.sortBy {
            it % 2 ==0
        }
        println("sortBy: $list")

        list.sortedDescending()
        //自然降序
        println("sortedDescending: $list")
        //根据条件降序，和`sortedBy{}`相反

        list.sortByDescending {
            it % 2 == 0
        }
        println("sortByDescending: $list")
    }

    /**
     * 元素操作符
     */
    private fun elementOperator() {
        println("elementAtOrElse"+list.elementAtOrElse(10){
            println("defalut value is ${it}")
        })
        println("elementAtOrNull"+list.elementAtOrNull(9))
        println("elementAtOrNull"+list.firstOrNull{
            it > 4
        })

    }

    /**
     * 把集合根据调用集合类相应的高阶函数来转换成相应的数
     */
    private fun transoform(){
        println(list.toIntArray())
        println(list.javaClass.toString())
        println(list.toIntArray().javaClass.toString())
    }


    private val numberMap = hashMapOf("one" to 1, "two" to 2, "three" to 3)
    val list = mutableListOf(1,3,2,4)


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




    /**
     * 生产操作符
     */
    private fun produceOperator(){
        val list1 = listOf(1,2,3,4)
        val list2 = listOf("kotlin","Android","Java","PHP","JavaScript")

        // 合并两个集合中的元素，组成一个新的集合。也可以使用符号 plus() 和 `+`一样
        println("plus:"+ list1.plus(list2))
        println("+:$list1$list2")

        // zip 由两个集合按照相同的下标组成一个新集合。该新集合的类型是：List<Pair>
        println("zip1:"+ list1.zip(list2))
        println("zip2:"+list1.zip(list2){       // 组成的新集合由元素少的原集合决定
                it1,it2-> it1.toString().plus("-").plus(it2)
        })

        // unzip
        val newList = listOf(Pair(1,"Kotlin"),Pair(2,"Android"),Pair(3,"Java"),Pair(4,"PHP"))
        println("unzip:"+newList.unzip())

// partition   将一个集合返回两个list,可用于一个函数返回两个对象的场景

        val partion = list2.partition { it.startsWith("Ja") }
        println(partion.first.plus("-").plus(partion.second))

    }

    private fun filter(){
        val list = listOf(1,2,6,4,5,3,4)
        //把不满足条件的元素过滤掉
        println("filter:${list.filter {
            it > 4
        }}")

        //和filter{}函数的作用相反
        println("filterNot:${list.filterNot {
            it > 4
        }}")

        val filterList2 = list.filterList {
            this > 4
        }
        println("filterList2:${filterList2}")

        //返回集合中前num个元素组成的集合

        println("take:"+ list.take(2))
        //循环遍历集合，从第一个元素开始遍历集合，当第一个出现不满足条件元素的时候，
        // 退出遍历。然后把满足条件所有元素组成的集合返回。
        println("takeWhile:"+ list.takeWhile {
            it < 4
        })

        println("takeLast:"+list.takeLast(2))
        println("takeLastWhile:"+list.takeLastWhile { it < 4 })

        //过滤集合中前num个元素
        println("dropLast:"+list.dropLast(2))

        //相同条件下，和执行takeLastWhile{...}函数后得到的结果相反
        println("dropLastWhile:"+ list.dropLastWhile {
            it > 4
        })
        //去除重复元素
        println("distinct:"+list.distinct())
        //根据操作元素后的结果去除重复元素
        println("distinctBy:"+list.distinctBy {
            if (it % 2 == 0){
                10
            } else{
                it
            }
        })
        println("slice:"+list.slice(IntRange(1,3)))

    }



    val users = listOf(UserBean(null,1),UserBean("user2",2),UserBean("user3",3))



}