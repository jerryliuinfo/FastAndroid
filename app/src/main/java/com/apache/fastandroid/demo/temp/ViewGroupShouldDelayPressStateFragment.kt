package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.databinding.TempShouldDelayPressStateBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Created by Jerry on 2021/3/1.
 */
class ViewGroupShouldDelayPressStateFragment: BaseBindingFragment<TempShouldDelayPressStateBinding>(TempShouldDelayPressStateBinding::inflate) {
    companion object{
        val TAG = "ViewGroupShouldDelayPressStateFragment"
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)



       var view: View





    }




}