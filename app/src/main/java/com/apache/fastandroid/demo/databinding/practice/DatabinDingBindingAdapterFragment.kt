package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.BR
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingBindingAdapterBinding
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.support.bean.DataBindingConfig
import com.tesla.framework.ui.fragment.BaseDatebindingFragment


class DatabinDingBindingAdapterFragment: BaseDatebindingFragment<FragmentDatabindingBindingAdapterBinding>() {
    companion object{
        private const val TAG = "DatabinDingBindingAdapterFragment"
    }
    private lateinit var viewModel: UserViewModel


    override fun getLayoutId(): Int {
        return R.layout.fragment_databinding_binding_adapter
    }
    private lateinit var user:UserObservableFieldBean

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        user = UserObservableFieldBean().apply {
            name.set("zhangsan")
            age.set(19)
            avator.set("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg1.gtimg.com%2Fln%2Fpics%2Fhv1%2F103%2F148%2F1558%2F101346793.jpg&refer=http%3A%2F%2Fimg1.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613900000&t=ae8a5f08768bfca867b1f483f036b274")
        }
        binding.user = user

    }

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_databinding_twoeway_bind, BR.vm,viewModel)
    }

    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }


}