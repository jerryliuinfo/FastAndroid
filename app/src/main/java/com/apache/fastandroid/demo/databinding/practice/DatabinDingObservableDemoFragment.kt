package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.ui.fragment.BaseDBFragment


class DatabinDingObservableDemoFragment: BaseDBFragment<FragmentDatabindingOnewayBindObservableBinding>(FragmentDatabindingOnewayBindObservableBinding::inflate) {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }

    override fun getLayoutId(): Int {
       return R.layout.fragment_databinding_oneway_bind_observable
    }

    private lateinit var user:UserObservableBean

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        user = UserObservableBean().apply {
            name = "zhangsan"
            age = 10
        }

        viewBinding.user = user

        viewBinding.tvModifyName.setOnClickListener {
            user.apply {
                name = "lisi"
                age =  20
            }
        }

        viewBinding.tvModifyAge.setOnClickListener {
            user.plus()
        }
    }

    private lateinit var viewModel: UserViewModel


    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }



}