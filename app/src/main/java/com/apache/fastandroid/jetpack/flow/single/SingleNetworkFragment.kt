package com.apache.fastandroid.jetpack.flow.single

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.jetpack.flow.basic.BaseFlowRecycleViewFragment
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory


/**
 * Created by Jerry on 2022/5/3.
 * https://blog.mindorks.com/terminal-operators-in-kotlin-flow
 */
class SingleNetworkFragment: BaseFlowRecycleViewFragment(){

    private val mViewModel: SingleNetworkCallViewModel by viewModels{getViewModelFactory()}



    override fun getUserFlowData(): MutableLiveData<Resource<List<ApiUser>>> {
        return mViewModel.users
    }


}