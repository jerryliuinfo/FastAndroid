package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentDesignModeListBinding
import com.apache.fastandroid.demo.kt.singleton.SingleInstanceSync
import com.apache.fastandroid.demo.kt.singleton.SingleObject
import com.apache.fastandroid.demo.kt.singleton.SingletonByObject
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/3/11.
 */
class DesignModeDemoListFragment:BaseBindingFragment<FragmentDesignModeListBinding>(FragmentDesignModeListBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnSingleton.setOnClickListener {
            singletonMode()
        }
    }

    private fun singletonMode() {
        SingletonByObject.count()
        SingleInstanceSync.getInstance()
        SingleObject.getInstance()
    }
}