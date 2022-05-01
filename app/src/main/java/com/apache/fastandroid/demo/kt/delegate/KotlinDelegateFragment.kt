package com.apache.fastandroid.demo.kt.delegate

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.KtDelegateBinding
import com.apache.fastandroid.demo.kt.ByTest
import com.tesla.framework.kt.SPreference
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlin.properties.Delegates

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinDelegateFragment:BaseVBFragment<KtDelegateBinding>(KtDelegateBinding::inflate) {
    companion object{
        private const val TAG = "KotlinDelegateFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnSp.setOnClickListener {
            spByDelegate()
        }

        mBinding.btnKeywordBy.setOnClickListener {
            ByTest.Zoo(ByTest.Cat()).bark()

        }
        mBinding.btnByDelegate.setOnClickListener {
            ByTest.ZooBy(ByTest.Cat()).bark()
        }
        mBinding.btnByLazyProperty.setOnClickListener {
            byLazy()
        }
        mBinding.btnDelegateObservable.setOnClickListener {
            delegateObservable()
        }
        mBinding.btnVetoable.setOnClickListener {
            vetoableTest()
        }
        mBinding.btnByMap.setOnClickListener {
            savePropertyToMap()
        }



    }

    private fun spByDelegate() {
        val name:String by SPreference("name","zhangsan")
        println(name)
    }


    private class ByMap(val map:Map<String,Any?>){
        val name:String by map
        val age:Int by map
    }

    private fun savePropertyToMap() {
        val user = ByMap(mapOf(
            "name" to "西哥",
            "age" to 25
            ))
        println("name:${user.name}, age:${user.age}")
    }

    var vetoableProp:Int by Delegates.vetoable(0){
        property, oldValue,newValue ->
        println("property: $property: $oldValue -> $newValue ")
        newValue > oldValue
    }


    private fun vetoableTest() {
        println("vetoableProp: ${vetoableProp}")
        vetoableProp = 10
        println("vetoableProp: ${vetoableProp}")
        //这个赋值不会生效，因为 5 没有大于 10
        vetoableProp = 5
        println("vetoableProp: ${vetoableProp}")
        vetoableProp = 100
        println("vetoableProp: ${vetoableProp}")

    }

    var observableProp: String by Delegates.observable("默认值：xxx"){
            property, oldValue, newValue ->
        println("property: $property: $oldValue -> $newValue ")
    }


    /**
     * 如果你要观察一个属性的变化过程，那么可以将属性委托给Delegates.observable

     */
    private fun delegateObservable() {
        observableProp = "第1次修改值"
        observableProp = "第2次修改值"
    }



    val lazyProp:String by lazy {
        println("第一次调用才会执行")
        "Hello"
    }
    private fun byLazy() {
        //延迟属性
        println(lazyProp)
        println(lazyProp)
        println(lazyProp)
    }



    /**
     * https://juejin.cn/post/6844904038589267982
     */
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
        println("delegate name:${people.name}, updateCount:${people.updateCount}")


        //属性委托
        people.d2 = 10
        println("d1:${people.d1}, d2:${people.d2}")

    }


}