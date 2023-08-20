package com.apache.fastandroid.demo.kt.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentJuejinKtBinding
import com.apache.fastandroid.databinding.FragmentKotlinPracticeBinding
import com.apache.fastandroid.demo.kt.extends.PingMsg
import com.apache.fastandroid.demo.kt.func.JvmOverloadsDemo
import com.apache.fastandroid.demo.kt.hignorder.FuncAsParamDemo
import com.tesla.framework.component.logger.Logger

import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/10/18.
 * https://book.kotlincn.net/text/d-basics.html
 */
class KotlinPracticeDemoFragment :
    BaseBindingFragment<FragmentKotlinPracticeBinding>(FragmentKotlinPracticeBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnIntArray.setOnClickListener {
            initArrayUsage()
        }


        val customer = Customer("","")
        customer.component2()
        val map = mapOf("one" to "xiaoming")





    }

    class Rectangle(var height: Double, var length: Double) {
        var perimeter = (height + length) * 2



    }

    data class Customer(val name: String, val email: String)


    fun getStringLength(obj:Any):Int?{
        if (obj is String && obj.length > 0){
            return obj.length
        }
        return null
    }


    private fun initArrayUsage() {
        IntArray(10).apply {
            fill(-1)
        }

        val x = 10
        val y = 9
        if (x in 1..y+1) {
            println("fits in range")
        }
    }


}