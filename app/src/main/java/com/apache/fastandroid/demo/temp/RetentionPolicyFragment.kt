package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.apache.fastandroid.demo.temp.retention.Config
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/3/1.
 */
class RetentionPolicyFragment: BaseVBFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        setType(Config.LoginType.LEFT)
    }


    private fun setType(@Config.LoginType loginType: Int){

    }
}