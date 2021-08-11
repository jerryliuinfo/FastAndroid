package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.temp_bit_or_and_demo.*


/**
 * Created by Jerry on 2021/3/1.
 */
class ViewGroupShouldDelayPressStateFragment: BaseFragment() {
    companion object{
        val TAG = "ViewGroupShouldDelayPressStateFragment"
    }


    override fun inflateContentView(): Int {
        return R.layout.temp_should_delay_press_state
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)



       var view: View





    }




}