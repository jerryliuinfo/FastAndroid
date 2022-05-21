package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.math.MathUtils
import com.apache.fastandroid.databinding.FragmentKtCollection2Binding
import com.apache.fastandroid.databinding.FragmentMathutilBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.sealed.User
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlin.math.abs

/**
 * Created by Jerry on 2022/2/27.
 */
class MathUtilDemoFragment:BaseVBFragment<FragmentMathutilBinding>(FragmentMathutilBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnClamp.setOnClickListener {
            clampUsage()
        }



    }

    private fun clampUsage() {
        val value = 10
        val clampValue = MathUtils.clamp(value,1,4)
        println("clampValue:${clampValue}")
    }


}