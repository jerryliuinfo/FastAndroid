package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.apache.fastandroid.demo.temp.retention.Config

/**
 * Created by Jerry on 2021/3/1.
 */
class RetentionPolicyFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        setType(Config.LoginType.LEFT)
    }


    private fun setType(@Config.LoginType loginType: Int){

    }
}