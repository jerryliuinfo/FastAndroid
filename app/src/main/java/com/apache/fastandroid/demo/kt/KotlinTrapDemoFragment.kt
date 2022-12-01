package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentKotlinTrapBinding
import com.apache.fastandroid.demo.bean.Person
import com.blankj.utilcode.util.GsonUtils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/6/28.
 */
class KotlinTrapDemoFragment :BaseBindingFragment<FragmentKotlinTrapBinding>(FragmentKotlinTrapBinding::inflate){
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnDataclass.setOnClickListener {
            dataClassTrap()
        }
    }

    private fun dataClassTrap() {
        val person = Person(name = "zhangsan")
        val deserialize = GsonUtils.toJson(person)
        val serialize = GsonUtils.fromJson(deserialize,Person::class.java)

        Logger.d("deserialize:${deserialize}, serialize:$serialize")



    }
}