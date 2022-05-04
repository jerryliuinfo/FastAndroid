package com.apache.fastandroid.jetpack.flow.errorhandling.catch

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.jetpack.flow.BaseFlowRecycleViewFragment
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory


/**
 * Created by Jerry on 2022/5/3.
 * https://blog.mindorks.com/terminal-operators-in-kotlin-flow
 */
class CatchFragment:BaseFlowRecycleViewFragment() {

    private val mViewModel: CatchViewModel by viewModels{getViewModelFactory()}
    override fun getUserFlowData(): LiveData<Resource<List<ApiUser>>> {
        return mViewModel.getUsers()
    }


}