package com.apache.fastandroid.demo.paging.poster.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apache.fastandroid.demo.paging.poster.adapter.PosterListAdapter
import com.apache.fastandroid.network.api.ApiServiceFactory
import com.tesla.framework.databinding.FragmentRecycleview2Binding
import com.tesla.framework.ui.activity.BaseBindingActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PagingPosterDemoActivity : BaseBindingActivity<FragmentRecycleview2Binding>() {

     private val viewModel: PosterViewModel by lazy {
         ViewModelProvider(
             this,
             PosterViewModelFactory(ApiServiceFactory.apiService)
         ).get(PosterViewModel::class.java)
     }
    lateinit var mainListAdapter: PosterListAdapter
    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        setupList()
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collectLatest {
                mainListAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        mainListAdapter = PosterListAdapter()
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainListAdapter
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
    }


}