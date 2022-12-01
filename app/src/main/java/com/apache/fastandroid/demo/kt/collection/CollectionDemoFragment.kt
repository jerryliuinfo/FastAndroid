package com.apache.fastandroid.demo.kt.collection

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentKtCollectionBinding
import com.apache.fastandroid.demo.bean.Animal
import com.apache.fastandroid.demo.bean.Cat
import com.apache.fastandroid.demo.bean.Dog
import com.apache.fastandroid.demo.bean.UserBean
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/2/27.
 */
class CollectionDemoFragment :
    BaseBindingFragment<FragmentKtCollectionBinding>(FragmentKtCollectionBinding::inflate) {

    private val numberMap = hashMapOf("one" to 1, "two" to 2, "three" to 3)
    private val list = mutableListOf(1, 10, 5, 7, 5)
    private val users = listOf(
        UserBean(null, 9),
        UserBean("user2", 20),
        UserBean("user3", 30),
        UserBean("user4", 10),
        UserBean("user4", 16),
        UserBean("user5", 10)
    )


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



        mBinding.btnAllAny.setOnClickListener {
            allAnyNoneUsage()
        }

        mBinding.btnAverage.setOnClickListener {
            averageUsage()
        }

        mBinding.btnChunk.setOnClickListener {
            chunkUsage()
        }

        mBinding.btnComponent.setOnClickListener {
            componentUsage()
        }

        mBinding.btnCount.setOnClickListener {
            countUsage()
        }

        mBinding.btnDistinct.setOnClickListener {
            distinctUsage()
        }

        mBinding.btnDrop.setOnClickListener {
            dropUsage()
        }
        mBinding.btnElement.setOnClickListener {
            elementUsage()
        }
        mBinding.btnFilter.setOnClickListener {
            filterUsage()
        }

        mBinding.btnFind.setOnClickListener {
            findUsage()
        }

        mBinding.btnMap.setOnClickListener {
            mapUsage()
        }

        mBinding.btnFlatmap.setOnClickListener {
            flatMapUsage()
        }

        mBinding.btnForEach.setOnClickListener {
            forEachUsage()
        }
        mBinding.btnGroupby.setOnClickListener {
            groupByUsage()
        }

        mBinding.btnGroupby.setOnClickListener {
            groupByUsage()
        }

        mBinding.btnIndexOf.setOnClickListener {
            indexOfUsage()
        }

        mBinding.btnJoinTo.setOnClickListener {
            joinToUsage()
        }

        mBinding.btnMinmax.setOnClickListener {
            minMaxUsage()
        }

        mBinding.btnMinus.setOnClickListener {
            minusUsage()
        }

        mBinding.btnPartition.setOnClickListener {
            partitionUsage()
        }

        mBinding.btnRandom.setOnClickListener {
            randomUsage()
        }

        mBinding.btnRequireNotNull.setOnClickListener {
            requireNotNullUsage()
        }

        mBinding.btnSingle.setOnClickListener {
            singleUsage()
        }

        mBinding.btnSlice.setOnClickListener {
            sliceUsage()
        }

        mBinding.btnSortBy.setOnClickListener {
            sortByUsage()
        }

        mBinding.btnSum.setOnClickListener {
            sumUsage()
        }

        mBinding.btnTake.setOnClickListener {
            takeUsage()
        }

        mBinding.btnZip.setOnClickListener {
            zipUsage()
        }
    }

    private fun partitionUsage() {
        val evenOdd = list.partition {
            it % 2 == 0
        }
        Logger.d("even:${evenOdd.first}, odd:${evenOdd.second}")
    }

    private fun zipUsage() {
        val zip = list.zip(users)
        for (item in zip) {
            Logger.d("zip item:${item.first}:${item.second}")
        }
        val zipWithNext = list.zipWithNext()
        for (item in zipWithNext) {
            Logger.d("zipWithNext item:${item.first}:${item.second}")
        }

        val zipWithNextTransform = list.zipWithNext { a, b ->
            a + b
        }
        for (item in zipWithNextTransform) {
            Logger.d("zipWithNextTransform item:${item}")
        }

    }

    private fun takeUsage() {
        //返回前 n 个元素
        val take = users.take(2)
        //返回满足给定条件前的所有元素，一旦满足了给定条件，则后面的元素都不返回了
        val takeWhile = users.takeWhile {
            it.age > 15
        }


        val takeLast = users.takeLast(2)
        val takeLastWhile = users.takeLastWhile {
            it.age > 15
        }
        Logger.d("take:$take, takeWhile:$takeWhile, takeLast:$takeLast, takeLastWhile:$takeLastWhile")


        //如果满足条件，则返回对象本身，否则返回 null
        val takeIf1 = list.takeIf {
            list.contains(5)
        }
        val takeIf2 = list.takeIf {
            list.contains(100)
        }
        Logger.d("takeIf1:$takeIf1, takeIf2:$takeIf2")
    }

    private fun sumUsage() {
        val sum = list.sum()
        val sumOf = users.sumOf {
            it.age
        }
        Logger.d("sum:$sum, sumOf:$sumOf")
    }

    private fun sortByUsage() {
        val sortedBy = users.sortedBy {
            it.age
        }

        val sortedBy2 = users.sortedBy {
            -it.age
        }


        val sortedByDescending = users.sortedByDescending {
            it.age
        }
        val comparator = object : Comparator<UserBean> {
            override fun compare(o1: UserBean?, o2: UserBean?): Int {
                if (o1 != null && o2 != null) {
                    return o1.age - o2.age
                } else if (o1 != null) {
                    return 1
                } else if (o2 != null) {
                    return -1
                }
                return 0
            }

        }
        val sortedWith = users.sortedWith(comparator)
        Logger.d("sortedBy:$sortedBy,sortedBy2:$sortedBy2, sortedByDescending:$sortedByDescending, sortedWith:$sortedWith")
    }

    private fun sliceUsage() {
        //slice()返回具有给定索引的集合元素列表。索引可以作为范围或整数值的集合传递。
        val slice = users.slice(1..3)
        val slice2 = users.slice(0..4 step 2)
        Logger.d("slice:$slice, slice2:$slice2")
    }

    private fun singleUsage() {
        //返回满足条件的的单个元素，如果没有满足条件的元素或者不止一个元素满足，则会抛出异常
        val single = users.single {
            it.age > 16
        }
        //返回满足条件的的单个元素，如果没有满足条件的元素则返回 null，不会抛出异常
        val singleOrNull = users.singleOrNull() {
            it.age > 16
        }
        Logger.d("single:$single, singleOrNull:$singleOrNull")
    }

    private fun requireNotNullUsage() {
        //
        users.requireNoNulls()
    }

    private fun randomUsage() {
        //返回一个随机的元素,如果集合为空，将会抛出异常
        val random = users.random()
        val randomOrNull = users.randomOrNull()
        Logger.d("random:$random, randomOrNull:$randomOrNull")
    }

    private fun minusUsage() {
        //5,7,5
        val minus = list.minus(arrayOf(1, 10))

        val plus = list.plus(arrayOf(3, 4))
        Logger.d("minus:$minus, plus:$plus")

    }

    private fun minMaxUsage() {
        //可能会抛出异常
        val min = users.minOf {
            it.age
        }
        //不会抛出异常
        val minOfOrNull = users.minOfOrNull {
            it.age
        }


        val comparator = object : Comparator<String> {
            override fun compare(o1: String?, o2: String?): Int {
                if (o1 != null && o2 != null) {
                    return o1.length - o2.length
                } else if (o1 != null) {
                    return 1
                } else if (o2 != null) {
                    return -1
                }
                return 0
            }
        }
        //根据 comparator 找出最小值,如果没有最小值，则抛出异常
        val minOfWith = users.minOfWith(comparator) {
            it.name ?: "default"
        }
        //根据 comparator 找出最小值,如果没有最小值，则返回 null 并且不会会抛出异常
        val minOfWithOrNull = users.minOfWithOrNull(comparator) {
            it.name ?: "default"
        }
        Logger.d("min:$min, minOfOrNull:$minOfOrNull, minOfWith:$minOfWith, minOfWithOrNull:$minOfWithOrNull")


        val maxOf = users.maxOf {
            it.age
        }

        val maxOfOrNull = users.maxOfOrNull {
            it.age
        }

        val maxOfWith = users.maxOfWith(comparator) {
            it.name ?: "default"
        }

        val maxOfWithOrNull = users.maxOfWithOrNull(comparator) {
            it.name ?: "default"
        }
        Logger.d("maxOf:$maxOf, maxOfOrNull:$maxOfOrNull, maxOfWith:$maxOfWith, maxOfWithOrNull:$maxOfWithOrNull")


    }

    private fun joinToUsage() {
        val joinToString = users.joinToString(separator = ",", prefix = "[", postfix = "]") {
            it.name ?: "default name"
        }
        Logger.d("joinToString:$joinToString")
    }

    private fun indexOfUsage() {
        val indexOfFirst = users.indexOfFirst {
            it.age == 7
        }

        val indexOfLast = users.indexOfLast {
            it.age == 7
        }
        Logger.d("indexOfFirst:$indexOfFirst, indexOfLast:$indexOfLast")
    }

    private fun mapUsage() {
        val mapAges = users.map {
            it.age
        }
        Logger.d("mapAges:$mapAges")
    }

    private fun groupByUsage() {
        //返回一个根据给定函数分组后的map
        val groupBy = users.groupBy(UserBean::name, UserBean::age)
        Logger.d("groupBy:$groupBy")


        val associateBy = users.associateBy {
            it.age
        }
        Logger.d("groupBy:$groupBy, associateBy:$associateBy")

    }

    private fun forEachUsage() {
        //对每一个元素做处理
        users.forEachIndexed { index, item ->
            item.age = (item.age + index)
        }
        Logger.d("users:$users")


    }


    /**
     * 返回对原始数组的每个元素调用转换函数的结果产生的所有元素的单个列表
     * 遍历所有元素，为每一个元素创建集合，最后把所有的集合放在一个集合中
     *
     */
    private fun flatMapUsage() {
        val fruitsBag = listOf("apple", "orange", "banana", "grapes")  // 1
        val clothesBag = listOf("shirts", "pants", "jeans")
        val cart = listOf(fruitsBag, clothesBag)
        println("cart: $cart")
        //flatMap 将集合中的每个元素转换为一个迭代对象，并构建转换结果的单个列表
        val flatMapList: List<UserBean> = cart.flatMap { fruitList ->
            fruitList.map {
                UserBean(it, it.length)
            }
        }
        val flatMapIndexList: List<UserBean> = cart.flatMapIndexed { index, fruitList ->
            fruitList.map {
                UserBean(it + "$index", it.length)
            }
        }
        println("flatMapBag:$flatMapList")
        println("flatMapIndexList:$flatMapIndexList")
    }

    private fun findUsage() {
        val find = users.find {
            it.age > 15
        }
        val findLast = users.findLast {
            it.age > 15
        }
        val firstOrNull = users.firstOrNull()
        val firstOrNullPredicate = users.firstOrNull {
            it.age > 20
        }
        val firstNotNullOfOrNull = users.firstNotNullOfOrNull {
            it.age > 40
        }

        val lastOrNull = users.lastOrNull()

        val getOrNull = users.getOrNull(10)
        val getOrElse = users.getOrElse(10) { index ->
            index * 2
        }

        Logger.d(
            "find:$find, findLast:$findLast, firstOrNull:$firstOrNull," +
                    "firstOrNullPredicate:$firstOrNullPredicate, firstNotNullOfOrNull:$firstNotNullOfOrNull, lastOrNull:$lastOrNull," +
                    "getOrNull:$getOrNull, getOrElse:$getOrElse"
        )

    }

    private fun filterUsage() {
        val filterList = list.filter {
            it > 6
        }

        val filterIndexed = list.filterIndexed { index, item ->
            item > 6 && index > 2
        }

        val destinationList = mutableListOf(100)
        list.filterIndexedTo(destinationList) { index, item ->
            item > 6 && index > 2
        }

        val animals: List<Animal> = listOf(Cat("Scratchy"), Dog("Poochie"))
        val filterIsInstanceList = animals.filterIsInstance<Cat>()


        val destinationAnimalList = mutableListOf(Cat("Hoho"))
        val filterIsInstanceToList =
            animals.filterIsInstanceTo(destinationAnimalList, Cat::class.java)


        val filterNotList = list.filterNot {
            it > 6
        }

        val destFilterToList = mutableListOf(40)
        val filterToList = list.filterTo(destFilterToList) {
            it > 9
        }
        Logger.d(
            "filterList:$filterList, filterIndexed:$filterIndexed,destinationList:$destinationList,filterIsInstanceList" +
                    ":$filterIsInstanceList,filterIsInstanceToList:$filterIsInstanceToList,filterNotList:$filterNotList,filterToList:$filterToList"
        )
    }

    private fun dropUsage() {
        //返回一个列表，不包括前 2 个元素  5,7,5
        val drop = list.drop(2)
        //返回一个列表，不包括最后 2 个元素 1,10,5
        val dropLast = list.dropLast(2)

        //返回一个列表，从前面往后遍历，不包括第一个满足条件的元素以外
        val dropWhileList = users.dropWhile {
            it.age > 13
        }
        //返回一个列表，从后面往前遍历，不包括第一个满足条件的元素以外
        val dropLastWhileList = users.dropLastWhile {
            it.age > 13
        }
        Logger.d("drop:$drop,dropLast:$dropLast, dropWhileList:$dropWhileList,dropLastWhileList:$dropLastWhileList")
    }

    private fun distinctUsage() {
        //10,20,30,16
        val ages = users.map {
            it.age
        }.distinct()
        //
        Logger.d("distinct ages:$ages")

        //UserBean(null,10),UserBean("user2",20),UserBean("user3",30)
        val users = users.distinctBy {
            it.age
        }
        Logger.d("distinctBy users:$users")

    }

    private fun countUsage() {
        val count = users.count()
        val countPredicate = users.count { user ->
            user.age > 15
        }
        Logger.d("count:$count, countPredicate:$countPredicate")
    }

    private fun componentUsage() {
        Logger.d("component1:${list.component1()},component5:${list.component5()},")
    }

    private fun chunkUsage() {
        val words = "one two three four five six seven eight nine ten".split(' ')
        val chunks = words.chunked(3)

        //[ [one, two, three], [four, five, six], [seven, eight, nine], [ten]]
        Logger.d("chunked:$chunks")


        //CharSequence 的扩展函数
        val codonTable = mapOf(
            "ATT" to "Isoleucine",
            "CAA" to "Glutamine",
            "CGC" to "Arginine",
            "GGC" to "Glycine"
        )
        val dnaFragment = "ATTCGCGGCCGCCAA"

        val proteins = dnaFragment.chunked(3) { codon: CharSequence ->
            codonTable[codon.toString()] ?: error("Unknown codon")
        }
        Logger.d("proteins:$proteins")


    }

    private fun averageUsage() {
        val averageAge = users.map {
            it.age
        }.average()
        Logger.d("averageAge:$averageAge")

    }


    private fun allAnyNoneUsage() {
        //判断集合是否至少含有一个元素
        println("any:${list.any()}")

        //判断集合中是否存在满足条件的元素。若存在则返回true,反之返回false
        println("any predicate:" + list.any {
            it > 3
        })

        //和any()函数的作用相反,判断是空集合
        println("none:" + list.none())
        println("none predicate" + list.none { it > 3 })

        //判断集合中的所有元素是否都满足条件。若是则返回true,反之则返回false
        println("all:" + list.all {
            it > 3
        })

    }

    /**
     * 元素操作符
     */
    private fun elementUsage() {
        val elementAt = list.elementAt(1)
        val elementAtOrElse = list.elementAtOrElse(10) {
            100
        }
        val elementAtOrNull = list.elementAtOrNull(9)
        Logger.d("elementAt:$elementAt, elementAtOrElse:$elementAtOrElse,elementAtOrNull:$elementAtOrNull")
    }


}