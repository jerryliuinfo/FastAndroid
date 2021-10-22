package com.apache.fastandroid.jetpack.relearnandroid

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentViewmodelScopeBindingImpl
import com.apache.fastandroid.jetpack.relearnandroid.vm.ShareViewModel
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.ui.fragment.BaseLifecycleFragment
import kotlinx.android.synthetic.main.fragment_viewmodel_scope.*

/**
 * Created by Jerry on 2021/9/8.
 */
class ViewModelScopeDemoFragment:BaseLifecycleFragment<FragmentViewmodelScopeBindingImpl>() {

    private lateinit var shareViewModel: ShareViewModel
    override fun inflateContentView(): Int {
        return R.layout.fragment_viewmodel_scope
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn.setOnClickListener {
            shareViewModel.toggle()
        }



    }

    override fun initViewModel() {
        shareViewModel = getApplicationScopeViewModel(ShareViewModel::class.java)
    }
}