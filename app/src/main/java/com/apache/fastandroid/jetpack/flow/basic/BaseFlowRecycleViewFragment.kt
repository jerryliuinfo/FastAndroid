package com.apache.fastandroid.jetpack.flow.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.jetpack.flow.adapter.ApiUserAdapter
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import com.apache.fastandroid.network.model.Status
import com.apache.fastandroid.widget.SpaceItemDecoration
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/5/3.
 */
abstract class BaseFlowRecycleViewFragment:BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {

    val users = MutableLiveData<Resource<List<ApiUser>>>()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val userAdapter = ApiUserAdapter()
        mBinding.recyclerView.apply {
            adapter = userAdapter
            addItemDecoration(SpaceItemDecoration(10.dpInt))
        }

        getUserFlowData().observe(this){
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

    abstract fun getUserFlowData():LiveData<Resource<List<ApiUser>>>
}