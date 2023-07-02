package com.apache.fastandroid.demo.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentComRecycleviewBinding
import com.apache.fastandroid.demo.widget.listadapter.AlbumListAdapter
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.jetpack.flow.ui_state.PostsUiState
import com.apache.fastandroid.jetpack.flow.vm.PostViewModel
import com.tesla.framework.component.divider.SpaceItemDecoration
import com.blankj.utilcode.util.ToastUtils
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/7/19.
 */
class ListAdapterFragment:BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate) {

    private val userAdapter = AlbumListAdapter{
        toast("onClick ${it.name}")
    }
    private val mViewModel: PostViewModel by viewModels {
        getViewModelFactory()
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.recyclerView.apply {
            adapter = userAdapter
            addItemDecoration(
                SpaceItemDecoration(
                    10.dpInt
                )
            )
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect{ postState ->
                when(postState){
                    is PostsUiState.Loading -> {
                        mBinding.progressBar.isVisible = true
                        mBinding.recyclerView.isVisible = false

                    }
                    is PostsUiState.Success -> {
                        mBinding.progressBar.visibility = View.GONE
                        mBinding.recyclerView.visibility = View.VISIBLE
                        userAdapter.submitList(postState.postList.toMutableList()?.map {
                            User(it.id,it.name,it.email,it.avatar)
                        })
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

    }


}