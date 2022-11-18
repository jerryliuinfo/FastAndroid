package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentCoroutineNetworkBinding
import com.apache.fastandroid.demo.kt.coroutine.vm.RetrofitViewModel
import com.apache.fastandroid.network.model.Status
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/10/27.
 */
class CoroutineNetworkDemoFragment:BaseVBFragment<FragmentCoroutineNetworkBinding>(FragmentCoroutineNetworkBinding::inflate) {


    private val mViewModel: RetrofitViewModel by viewModels { getViewModelFactory() }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnNetwork.setOnClickListener {
            mViewModel.listRepo()
        }

        mViewModel.mApiUserListLiveData.observe(this){
            val text =   if (it.isLoading()) "加载中..." else it.data?.get(0)?.name
            mBinding.btnResult.text = text
        }
    }
}