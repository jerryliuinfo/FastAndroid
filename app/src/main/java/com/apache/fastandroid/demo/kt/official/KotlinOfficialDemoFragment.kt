package com.apache.fastandroid.demo.kt.official

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentKtOfficialDocBinding
import com.apache.fastandroid.demo.kt.hignorder.stringLengthFunc
import com.apache.fastandroid.demo.kt.hignorder.stringMapperFunc
import com.apache.fastandroid.jetpack.hit.Car
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/10/16.
 */
class KotlinOfficialDemoFragment:BaseVBFragment<FragmentKtOfficialDocBinding>(FragmentKtOfficialDocBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnAnonymousFunc.setOnClickListener {
            anoymousFunc()
        }

        mBinding.btnHighOrderFunc.setOnClickListener {
            highOrderFunc()
        }

        mBinding.btnGetSet.setOnClickListener {
            getSetUsage()
        }

        mBinding.btnExpression.setOnClickListener {
            expressionUsage("Hello")
        }

    }

    private fun expressionUsage(str:String) {
        //仅当整个表达式适合放在一行时，用作表达式的 if/else 条件语句才能省略大括号。
        val value = if (str.length > 3) "bigger than 3" else "smaller than 3"
        Logger.d("value:$value")
    }

    private fun getSetUsage() {
        val car = Car()
        car.gallonsOfFuleInTank
    }

    private fun highOrderFunc() {
        val  length = stringMapperFunc("Android"){
            it.length
        }
        Logger.d("length:$length")
    }

    private fun anoymousFunc() {
        stringLengthFunc("hello world").also {
            Logger.d("length:$it")

        }


    }


}

