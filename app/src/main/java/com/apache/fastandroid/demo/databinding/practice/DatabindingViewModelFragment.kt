package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentDatabindingViewmodel2Binding
import com.apache.fastandroid.databinding.FragmentDatabindingViewmodelBinding
import com.apache.fastandroid.demo.databinding.data.ProfileLiveDataViewModel
import com.apache.fastandroid.demo.databinding.data.ProfileViewModel
import com.tesla.framework.BR
import com.tesla.framework.ui.fragment.BaseDataBindingFragment
import com.tesla.framework.ui.fragment.BaseDataBindingVMFragment


class DatabindingViewModelFragment :
    BaseDataBindingVMFragment<FragmentDatabindingViewmodel2Binding,ProfileViewModel>(FragmentDatabindingViewmodel2Binding::inflate) {
    companion object {
        private const val TAG = "ViewModelFragment"
    }

    override fun getViewModelInstance(): ProfileViewModel {
        return ProfileViewModel()
    }

    override fun getViewModelClass(): Class<ProfileViewModel> {
        return ProfileViewModel::class.java
    }

    override fun getVariableId(): Int {
        return BR.viewModel
    }








}