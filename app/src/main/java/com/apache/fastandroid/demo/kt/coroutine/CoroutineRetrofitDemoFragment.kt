package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.apache.fastandroid.demo.kt.coroutine.vm.RetrofitViewModel
import com.apache.fastandroid.jetpack.flow.basic.BaseFlowRecycleViewFragment
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory

/**
 * Created by Jerry on 2022/5/16.
 *https://github.com/MindorksOpenSource/Retrofit-Kotlin-Coroutines-Example
 *
 */
class CoroutineRetrofitDemoFragment: BaseFlowRecycleViewFragment() {

    private val mViewModel:RetrofitViewModel by viewModels { getViewModelFactory() }


    override fun getUserFlowData(): LiveData<Resource<List<ApiUser>>> {
        return mViewModel.getUsers()
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mViewModel.getUsers()
    }


}