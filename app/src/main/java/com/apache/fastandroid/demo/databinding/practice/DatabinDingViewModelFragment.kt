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
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableBinding
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableFieldBinding
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayEventBinding
import com.apache.fastandroid.databinding.FragmentDatabindingViewmodelBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseDatabindingFragment
import com.tesla.framework.ui.fragment.BaseTraceFragment
import kotlinx.android.synthetic.main.fragment_databinding_twoeway_event.*
import kotlin.random.Random
import kotlin.random.nextInt


class DatabinDingViewModelFragment: BaseTraceFragment<FragmentDatabindingViewmodelBinding>() {

    companion object{
        private const val TAG = "DatabinDingViewModelFragment"
    }

    private lateinit var viewModel:UserViewModel


    override fun inflateContentView(): Int {
        return R.layout.fragment_databinding_viewmodel
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        //要使用LiveData对象作为数据绑定来源，需要设置LifecycleOwner, 这样当livedata数据变化后，xml就能察觉到
        viewDataBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewDataBinding.vm = viewModel

    }
}