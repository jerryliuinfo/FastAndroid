package com.apache.fastandroid.demo.unitest

import android.os.Bundle
import com.apache.fastandroid.databinding.ActivityUnitTestBinding
import com.tesla.framework.ui.activity.BaseVmActivity
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by Jerry on 2022/1/24.
 */
class UnitTestActivity: BaseVmActivityNew<ActivityUnitTestBinding>(ActivityUnitTestBinding::inflate) {


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        
    }
}