package com.apache.fastandroid.jetpack.flow.practice

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FlowFragmentRetrofitBinding
import com.apache.fastandroid.home.ArticleAdapter
import com.apache.fastandroid.jetpack.flow.vm.FlowUserViewModel
import com.tesla.framework.common.util.log.Logger
import com.tesla.framework.kt.textViewFlow
import com.tesla.framework.ui.fragment.BaseVBFragment
import com.zj.mvi.core.observeState
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Jerry on 2022/6/19.
 */
class FlowRetrofitFragment:BaseVBFragment<FlowFragmentRetrofitBinding>(FlowFragmentRetrofitBinding::inflate) {

    private val mViewModel by viewModels<FlowUserViewModel>()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        lifecycleScope.launchWhenCreated {
            mBinding.editQuery.textViewFlow().collectLatest {
                com.tesla.framework.component.logger.Logger.d("query: $it")

                mViewModel.loadTopArticle()
            }
        }
        val mAdapter =  ArticleAdapter(emptyList())
        mBinding.recycleView.apply {
            adapter = mAdapter

        }

        mViewModel.mArticleState.observe(this){
            com.tesla.framework.component.logger.Logger.d("articles size :${it.size}")
            mAdapter.setNewInstance(it.toMutableList())
        }
    }
}