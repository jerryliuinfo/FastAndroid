package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.BR
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayEventBinding
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.support.bean.DataBindingConfig
import com.tesla.framework.ui.fragment.BaseDatebindingFragment
import kotlin.random.Random


class DatabinDingEventFragment: BaseDatebindingFragment<FragmentDatabindingTwoewayEventBinding>() {

    private lateinit var user:UserObservableFieldBean

    companion object{
        private const val TAG = "DatabinDingEventFragment"
    }

    override fun inflateContentView(): Int {
        return R.layout.fragment_databinding_twoeway_event
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        user = UserObservableFieldBean()
        binding.user = user
        binding.fragment = this


    }

    fun onClickEvent(){
        user.name.set("zhangsan${Random.nextInt(100)}")
    }

    private lateinit var viewModel: UserViewModel


    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_databinding_twoeway_bind, BR.vm,viewModel)
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }


}