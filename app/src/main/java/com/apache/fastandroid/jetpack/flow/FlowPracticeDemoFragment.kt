package com.apache.fastandroid.jetpack.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.jetpack.flow.adapter.ApiUserAdapter
import com.apache.fastandroid.jetpack.flow.datasource.FlowPracticeDataSource
import com.apache.fastandroid.jetpack.flow.datasource.FlowPracticeRepository
import com.apache.fastandroid.jetpack.flow.ui_state.PostsUiState
import com.apache.fastandroid.jetpack.flow.vm.PostViewModel
import com.apache.fastandroid.widget.SpaceItemDecoration
import com.blankj.utilcode.util.ToastUtils
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.cancel

/**
 * Created by Jerry on 2022/6/19.
 */
class FlowPracticeDemoFragment :
    BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {

    //    @Inject lateinit var postViewModel: PostViewModel
    private val mViewModel: PostViewModel by viewModels {
        getViewModelFactory()
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val userAdapter = ApiUserAdapter()
        mBinding.recyclerView.apply {
            adapter = userAdapter
            addItemDecoration(SpaceItemDecoration(10.dpInt))
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect { postState ->
                when (postState) {
                    is PostsUiState.Loading -> {
                        mBinding.progressBar.isVisible = true
                        mBinding.recyclerView.isVisible = false

                    }
                    is PostsUiState.Success -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.recyclerView.visibility = View.VISIBLE
                        userAdapter.setNewInstance(postState.postList?.toMutableList())
                    }
                    is PostsUiState.Error -> {
                        toast("load error")
                        mBinding.progressBar.visibility = View.GONE
                        ToastUtils.showShort(postState.exception.message)
                    }
                    is PostsUiState.ShowToast -> {
                        toast("Clicked Item is ${postState.id}")
                        ToastUtils.showShort(postState.id)
                    }
                }
            }
        }


        val dataSource = FlowPracticeDataSource()
        val repository = FlowPracticeRepository(dataSource)
        lifecycleScope.launchWhenStarted {
            repository.count.collect {
                Logger.d("count collect: $it")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
    }
}