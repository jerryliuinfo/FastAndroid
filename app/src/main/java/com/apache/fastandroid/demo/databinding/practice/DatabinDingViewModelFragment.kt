package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.BR
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableBinding
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableFieldBinding
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayEventBinding
import com.apache.fastandroid.databinding.FragmentDatabindingViewmodelBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.support.bean.DataBindingConfig
import com.tesla.framework.ui.fragment.ABaseDatabindingFragment
import com.tesla.framework.ui.fragment.BaseDatebindingFragment
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_databinding_twoeway_event.*
import kotlin.random.Random
import kotlin.random.nextInt


class DatabinDingViewModelFragment: BaseDatebindingFragment<FragmentDatabindingViewmodelBinding>() {

    companion object{
        private const val TAG = "DatabinDingViewModelFragment"
    }

    private lateinit var viewModel:UserViewModel

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        //要使用LiveData对象作为数据绑定来源，需要设置LifecycleOwner, 这样当livedata数据变化后，xml就能察觉到
        mBinding.lifecycleOwner = this


        mBinding.vm = viewModel

    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_databinding_viewmodel,BR.vm,viewModel);
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }
}