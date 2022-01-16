package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.BR
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableFieldBinding
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.support.bean.DataBindingConfig
import com.tesla.framework.ui.fragment.BaseDatebindingFragment


class DatabinDingObservableFieldDemoFragment: BaseDatebindingFragment<FragmentDatabindingOnewayBindObservableFieldBinding>() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_databinding_oneway_bind_observable_field
    }
    private lateinit var user:UserObservableFieldBean

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        user = UserObservableFieldBean().apply {
            name.set("zhangsan")
            age.set(19)
        }

        binding.user = user


        binding.tvModifyName.setOnClickListener {
            user!!.apply {
                name.set("lisi")
                age.set(0)
            }
        }

        binding.tvModifyAge.setOnClickListener {
            user!!.plus()
        }
    }


    private lateinit var viewModel: UserViewModel


    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_databinding_twoeway_bind, BR.vm,viewModel)
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }


}