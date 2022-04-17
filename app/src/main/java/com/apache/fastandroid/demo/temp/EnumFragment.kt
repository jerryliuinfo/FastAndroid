package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.GsonDemoBinding
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/3/1.
 */
class EnumFragment: BaseVBFragment<GsonDemoBinding>(GsonDemoBinding::inflate) {
    companion object{
        val TAG = "EnumFragment"
    }


    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        var recoverTopMode = SilentMode.RECOVER_TOP_ACTIVITY

        NLog.d(TAG, "name: %s", recoverTopMode.name)
    }




}