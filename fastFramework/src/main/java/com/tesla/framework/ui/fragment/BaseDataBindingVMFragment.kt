package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.tesla.framework.BaseVM
import com.tesla.framework.component.viewbinding.FragmentBinding
import com.tesla.framework.component.viewbinding.FragmentBindingDelegate
import com.tesla.framework.kt.inflateBinding


abstract class BaseDataBindingVMFragment<T : ViewDataBinding, M : BaseVM>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> T) :
    BaseFragment() {

    protected lateinit var mBinding: T

    protected lateinit var viewModel: M

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = inflateBinding<T>(layoutInflater).apply {
            lifecycleOwner = this@BaseDataBindingVMFragment
            setVariable(getVariableId(),viewModel)
        }
        return mBinding.root
    }


    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return getViewModelInstance() as T
            }
        })[getViewModelClass()]

    }

    abstract fun getViewModelInstance(): M

    abstract fun getViewModelClass(): Class<M>

    abstract fun getVariableId(): Int

}


