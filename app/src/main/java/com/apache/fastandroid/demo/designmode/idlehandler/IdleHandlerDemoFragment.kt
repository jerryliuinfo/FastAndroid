package com.apache.fastandroid.demo.designmode.idlehandler

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/9/23.
 */
class IdleHandlerDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_common
    }
    companion object{
        private const val TAG = "FilterDemoFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



    }


}