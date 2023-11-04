package com.apache.fastandroid.jetpack.flow.stateflow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.jetpack.flow.adapter.ApiUserAdapter
import com.apache.fastandroid.network.model.Status
import com.tesla.framework.component.divider.SpaceItemDecoration
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.kt.dp
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/6/20.
 */
class StateFlowDemoFragment2:BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {

//    private val mViewModel:MutableStateViewModel by viewModels {getViewModelFactory()}
    private val mViewModel:MutableStateViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        val userAdapter = ApiUserAdapter()
        mBinding.recyclerView.apply {
            adapter = userAdapter
            addItemDecoration(
                SpaceItemDecoration(
                    10.dp
                )
            )
        }
        lifecycleScope.launchWhenCreated {
            mViewModel.userState.collect{
                when(it.status){
                    Status.SUCCESS -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.recyclerView.visibility = View.VISIBLE
                        userAdapter.setNewInstance(it.data?.toMutableList())

                    }
                    Status.LOADING -> {
                        mBinding.progressBar.visibility = View.VISIBLE
                        mBinding.recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        mBinding.progressBar.visibility = View.GONE
                        ToastUtils.showShort(it.message)
                    }
                }
            }

        }


    }

}