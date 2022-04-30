package com.apache.fastandroid.demo.tablayout

import androidx.fragment.app.Fragment
import com.apache.fastandroid.databinding.BottomFragment1Binding
import com.apache.fastandroid.databinding.DatabindingComBindAdapterBinding
import com.tesla.framework.ui.fragment.BaseDBFragment

/**
 * Created by Jerry on 2022/4/30.
 */
class BottomFragment1:BaseDBFragment<BottomFragment1Binding>(BottomFragment1Binding::inflate) {

    companion object{
        @JvmStatic
        fun newInstance():Fragment{
            return BottomFragment1()
        }
    }
}