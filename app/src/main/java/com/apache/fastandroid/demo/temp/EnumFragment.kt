package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2021/3/1.
 */
class EnumFragment: BaseStatusFragmentNew() {
    companion object{
        val TAG = "EnumFragment"
    }
    override fun getLayoutId(): Int {
        return R.layout.gson_demo
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        var recoverTopMode = SilentMode.RECOVER_TOP_ACTIVITY

        NLog.d(TAG, "name: %s", recoverTopMode.name)
    }




}