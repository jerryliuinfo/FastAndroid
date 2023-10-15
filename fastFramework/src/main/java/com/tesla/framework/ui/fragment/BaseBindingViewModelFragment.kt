package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tesla.framework.BaseVM
import com.tesla.framework.component.viewbinding.inflateBinding


abstract class BaseBindingViewModelFragment<T : ViewDataBinding, M : BaseVM>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> T) :
    BaseFragment() {

    protected lateinit var mBinding: T

    protected lateinit var viewModel: M

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = inflateBinding<T>(layoutInflater).apply {
            lifecycleOwner = this@BaseBindingViewModelFragment
            setVariable(getVariableId(),viewModel)
        }
        return mBinding.root
    }


    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return getViewModelInstance() as T
            }
        })[getViewModelClass()]

    }

    abstract fun getViewModelInstance(): M

    abstract fun getViewModelClass(): Class<M>

    abstract fun getVariableId(): Int

}


