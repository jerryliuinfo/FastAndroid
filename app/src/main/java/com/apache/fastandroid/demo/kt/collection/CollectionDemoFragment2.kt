package com.apache.fastandroid.demo.kt.collection

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentKtCollection2Binding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.sealed.User
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlin.math.abs

/**
 * Created by Jerry on 2022/2/27.
 */
class CollectionDemoFragment2:BaseVBFragment<FragmentKtCollection2Binding>(FragmentKtCollection2Binding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnList.setOnClickListener {
            listUsage()
        }

        mBinding.btnSet.setOnClickListener {
            setUsage()
        }
        mBinding.btnMap.setOnClickListener {
            MapUsage()
        }
        mBinding.btnFilter.setOnClickListener {
            filterUsage()
        }
        mBinding.btnOperatorMap.setOnClickListener {
            mapOperatorUsage()
        }
        mBinding.btnAnyAllNone.setOnClickListener {
            anyAllNoneUsage()
        }
        mBinding.btnFind.setOnClickListener {
            findUsage()
        }
        mBinding.btnFirstLast.setOnClickListener {
            firstLastUsage()
        }
        mBinding.btnCount.setOnClickListener {
            countUsage()
        }
        mBinding.btnAssociateGroupBy.setOnClickListener {
            assocaiteByAndGroupBy()
        }
        mBinding.btnPartition.setOnClickListener {
            partitionUsage()
        }
        mBinding.btnFlatMap.setOnClickListener {
            flatMapUsage()
        }
        mBinding.btnMinMaxOrNull.setOnClickListener {
            minMaxOrNull()
        }
        mBinding.btnSorted.setOnClickListener {
            sortedUsage()
        }
        mBinding.btnMapElementAccess.setOnClickListener {
            mapElementAccessUsage()
        }
        mBinding.btnZip.setOnClickListener {
            zipUsage()
        }
        mBinding.btnGetOrElse.setOnClickListener {
            getOrElseUsage()
        }

    }

    private fun getOrElseUsage() {
        val list = listOf(0, 10, 20)
        println("list.getOrElse:" + list.getOrElse(1){})
        println("list.getOrElse:" + list.getOrElse(10){40})

        val map =  mutableMapOf<String,Int?>()
        println("map getOrElse:" + map.getOrElse("key1"){ 30 })
        println("map getOrDefault:" +map.getOrDefault("key1",40))

    }

    private fun zipUsage() {
        val A = listOf("a","b","c","d")
        val B = listOf(1,2,3,4,5)

        val resultPairs = A zip B
        println("resultPairs:$resultPairs")

        val resultReduce = A.zip(B){ a, b ->
            "$a:$b"
        }
        println("resultReduce:$resultReduce")
    }

    private fun mapElementAccessUsage() {
        val map = mapOf("key" to 42)
        val value1 = map.getOrDefault("key2", "default")

        val mapWithDefault = map.withDefault { k ->
            k.length
        }
        val valueDefault = mapWithDefault.getValue("key2")
        println("value1:$value1, valueDefault:$valueDefault")


    }

    private fun sortedUsage() {
        val shuffled = listOf(UserBean("one",1),UserBean("two",3),UserBean("three",2),UserBean("four",4),UserBean("twotwo",-10))
        val natural = shuffled.sortedBy {
            it.age
        }
        val negative = shuffled.sortedBy {
            -it.age
        }

        val descending = shuffled.sortedByDescending{
            it.age
        }

        val descendingBy = shuffled.sortedByDescending{
            abs(it.age)
        }
        println("natural:$natural")
        println("negative:$negative")
        println("descending:$descending")
        println("descendingBy:$descendingBy")

    }

    private fun minMaxOrNull() {
        val numbers = listOf(1, 2,3)
        val min = numbers.minOrNull()
        val max = numbers.maxOrNull()
        numbers.sorted()
        println("numbers:$numbers, min:$min, max:${max}")

        val empty = emptyList<Int>()
        println("empty:$empty, min:$min, max:$${max}")

        val only = listOf(3)
        println("only:$only, min:$min, max:$${max}")

    }

    private fun flatMapUsage() {
        val fruitsBag = listOf("apple","orange","banana","grapes")  // 1
        val clothesBag = listOf("shirts","pants","jeans")
        val cart = listOf(fruitsBag,clothesBag)
        println("cart: $cart")
        val mapBag = cart.map {
            it
        }
        println("mapBag:$mapBag")

        //flatMap 将集合中的每个元素转换为一个迭代对象，并构建转换结果的单个列表
        val flatMapBag = cart.flatMap {
            it
        }
        println("flatMapBag:$flatMapBag")

    }

    val numbers = listOf(1, -2, 3, -4, 5, -6)

    /**
     * 将集合按指定的条件放到一个 Pair 中
     */
    private fun partitionUsage() {
        val evenOdd = numbers.partition {
            it % 2 == 0
        }
        println("Even nums :${evenOdd.first}, odd numbs:${evenOdd.second}")
        val (positive,negatives) = numbers.partition {
            it > 0
        }
        println("positive:$positive, negatives:$negatives")

    }


    private fun assocaiteByAndGroupBy() {
        val users = listOf(UserBean("one",1),UserBean("two",2),UserBean("three",3),UserBean("four",4),UserBean("twotwo",2))

        //通过指定的条件，把list转换成map 第一种转换map的key
       val idMap =  users.associateBy {
            it.age
        }
        //通过指定的条件，把list转换成map key 和 value 都替换
        val nameMap = users.associateBy(UserBean::name,UserBean::age)

        //返回一个根据给定函数分组后的map
        val groupBy = users.groupBy(UserBean::name,UserBean::age)
        println("idMap:$idMap, nameMap:$nameMap, groupBy:$groupBy")
    }

    private fun countUsage() {
        val numbers = listOf(1, -2, 3, -4, 5, -6)
        val count = numbers.count()
        val evenCount = numbers.count{
            it % 2 == 0
        }
        println("count:$count, evenCount:$evenCount")
    }

    private fun firstLastUsage() {
        val numbers = listOf(1, -2, 3, -4, 5, -6)            // 1

        val first = numbers.firstOrNull()                          // 2
        val last = numbers.lastOrNull()                            // 3
    
        val firstEven = numbers.firstOrNull {
            it % 2 == 0
        }
        val lastOdd = numbers.lastOrNull {
            it % 2 != 0
        }
        println("first:$first, last:$last, firstEven:$firstEven,lastOdd:$lastOdd")
    }

    private fun findUsage() {
        val words = listOf("Lets", "find", "something", "in", "collection", "somehow")  // 1
        val first = words.find {
            it.startsWith("some")
        }
        val last = words.findLast {
            it.startsWith("some")
        }
        val findOrDefault = words.find {
            it.startsWith("hello")
        }?: "Unknow"
        println("first:$first, last:$last, findOrDefault:$findOrDefault")
    }

    private fun anyAllNoneUsage() {
        val list = listOf(1, -2, 3, -4, 5, -6)

        //Any 任意一个元素满足条件，则返回true
        val anyNegative = list.any {
            it < 0
        }
        val anyGt6 = list.any{
            it > 6
        }
        println("anyNegative:${anyNegative}, anyGt6:${anyGt6}")

        //All 所有元素满足条件，则返回true
        val allEven = list.all {
            it % 2 == 0
        }
        val allLess6 = list.all {
            it < 6
        }
        println("allEven:${allEven}, allLess6:${allLess6}")


        //none 没有任何一个元素满足条件则返回true
        val allEven2 = list.none {
            it % 2 == 1
        }
        val allLess62 = list.none {
            it > 6
        }
        println("allEven2:${allEven2}, allLess62:${allLess62}")

    }

    private fun mapOperatorUsage() {
        val list = listOf(User(1,"one"),User(2,"two"),User(3,"three"))
        val double = list.map {
            it.id
        }.map {
            it * 2
        }
        println("double:$double")

        val mapIndexed = list.map {
            it.id
        }.mapIndexed { index, i ->
            if (index >= 2) i * 2 else i
        }
        println("mapIndexed:$mapIndexed")

    }

    private fun filterUsage() {
        val list = listOf(1, -2, 3, -4, 5, -6)
        val positives = list.filter {
            it > 0
        }
        val negatives = list.filter {
            it < 0
        }

        val filterNot = list.filterNot {
            it >= 3
        }

        val filterIndexed = list.filterIndexed{ index, value ->
            value >= 3 && index > 2
        }
        println("positives:$positives, negatives:$negatives, filterNot:${filterNot}, filterIndexed:${filterIndexed}")

    }

    private fun MapUsage() {
        val passAccounts = mutableMapOf(1 to 100, 2 to 200, 3 to 300)
        val accountReports = passAccounts
        val DELTA = 15
        fun updateAccount(accountId:Int){
            if (passAccounts.containsKey(accountId)){
                println("updating ${accountId}")
                passAccounts[accountId] = passAccounts.getValue(accountId) + DELTA
            }else{
                println("Trying to update a non-exsiting account id:${accountId}")
            }
        }

        fun reportAccounts(){
            println("passAccounts report ")
            accountReports.forEach { k, v ->
                println("Id: $k, credit:$v")
            }
        }

        reportAccounts()
        updateAccount(1)
        updateAccount(1)
        updateAccount(5)
        reportAccounts()
    }

    private fun setUsage() {
        val openIssues = mutableSetOf("aa","bb","cc")
        fun addIssue(item:String):Boolean{
            return openIssues.add(item)
        }

        fun getStatusLog(isAdded:Boolean):String{
            return if (isAdded) "registered correctly" else "marked as duplicate and rejected"
        }

        val aNewIssue = "dd"
        val anIssueAlreadyIn = "cc"

        println("Issue $aNewIssue ${getStatusLog(addIssue(aNewIssue))}")                              // 4
        println("Issue $anIssueAlreadyIn ${getStatusLog(addIssue(anIssueAlreadyIn))}")
    }

    private fun listUsage() {
        //可变List
        val mutableList = mutableListOf(1,2,3)

        //不可变list
        val list:List<Int> = mutableList

        fun addItem(item:Int){
            mutableList.add(item)
        }
        addItem(4)
    }


}