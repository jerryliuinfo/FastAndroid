package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentJetpackLivedataWrongUsageBinding
import com.apache.fastandroid.jetpack.relearnandroid.vm.ShareViewModel
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.component.logger.Logger

import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataWrongUsageFragment : BaseVBFragment<FragmentJetpackLivedataWrongUsageBinding>(FragmentJetpackLivedataWrongUsageBinding::inflate){


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

       LiveDataWrongUsageActivity.launch(requireActivity())

    }

}