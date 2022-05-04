package com.apache.fastandroid.jetpack.flow.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.databinding.ItemLayoutBinding
import com.apache.fastandroid.jetpack.flow.BaseFlowRecycleViewFragment
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import com.apache.fastandroid.network.model.Status
import com.apache.fastandroid.widget.SpaceItemDecoration
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseVBFragment



/**
 * Created by Jerry on 2022/5/3.
 * https://blog.mindorks.com/terminal-operators-in-kotlin-flow
 */
class SingleNetworkFragment:BaseFlowRecycleViewFragment(){

    private val mViewModel: SingleNetworkCallViewModel by viewModels{getViewModelFactory()}



    override fun getUserFlowData(): MutableLiveData<Resource<List<ApiUser>>> {
        return mViewModel.users
    }


}