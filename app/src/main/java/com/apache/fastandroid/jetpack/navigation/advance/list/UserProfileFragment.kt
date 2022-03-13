package com.apache.fastandroid.jetpack.navigation.advance.list

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentUserProfileBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/12.
 */
class UserProfileFragment:BaseVBFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val username = arguments?.getString("username") ?: "Ali Corners"
        viewBinding.profileUserName.text = username
    }
}