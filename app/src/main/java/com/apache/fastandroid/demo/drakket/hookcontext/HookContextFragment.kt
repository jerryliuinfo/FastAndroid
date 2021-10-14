package com.apache.fastandroid.demo.drakket.hookcontext

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/13.
 */
class HookContextFragment :BaseFragment(){
    override fun inflateContentView(): Int {
        return R.layout.fragment_hook_context
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }
}