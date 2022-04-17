package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayEventBinding
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.ui.fragment.BaseDBFragment
import kotlin.random.Random


class DatabinDingEventFragment: BaseDBFragment<FragmentDatabindingTwoewayEventBinding>(FragmentDatabindingTwoewayEventBinding::inflate) {

    private lateinit var user:UserObservableFieldBean

    companion object{
        private const val TAG = "DatabinDingEventFragment"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_databinding_twoeway_event
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        user = UserObservableFieldBean()
        viewBinding.user = user
        viewBinding.fragment = this


    }

    fun onClickEvent(){
        user.name.set("zhangsan${Random.nextInt(100)}")
    }

    private lateinit var viewModel: UserViewModel



    override fun initViewModel() {
        viewModel = getFragmentScopeViewModel(UserViewModel::class.java)
    }


}