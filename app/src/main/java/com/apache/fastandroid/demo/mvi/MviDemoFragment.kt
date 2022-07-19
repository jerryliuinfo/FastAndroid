package com.apache.fastandroid.demo.mvi

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.apache.fastandroid.databinding.FragmentMviBinding
import com.apache.fastandroid.home.ArticleAdapter
import com.apache.fastandroid.util.InjectorUtil
import com.apache.fastandroid.util.extensitons.FetchStatus
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.snackbar.Snackbar
import com.tesla.framework.component.mvicore.observeEvent
import com.tesla.framework.component.mvicore.observeState
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/1/20.
 * 参考:
 * https://github.com/shenzhen2017/android-architecture
 * https://proandroiddev.com/best-architecture-for-android-mvi-livedata-viewmodel-71a3a5ac7ee3
 * https://juejin.cn/post/6920427168749060110
 */
class MviDemoFragment: BaseVBFragment<FragmentMviBinding>(FragmentMviBinding::inflate) {

    private val viewModel: MviViewModel by viewModels{InjectorUtil.getMviModelFactory()}

    private val newsRvAdapter by lazy {
        ArticleAdapter(emptyList()){ view, position ->
            ToastUtils.showShort("onclick ${position}")
        }
    }



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.recyclerView.apply {
            adapter = newsRvAdapter
        }
        mBinding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.dispatch(MainViewAction.OnSwipeRefresh)
            }
        }

        mBinding.fabStar.setOnClickListener{
            viewModel.dispatch(MainViewAction.FabClicked)
        }


        viewModel.viewStates.run {
            println("viewStates : ${this}")

            observeState(activity as LifecycleOwner, MainViewState::newsList) {
                println("list: ${it.size}")
                newsRvAdapter.setList(it.toMutableList())
            }
            observeState(activity as LifecycleOwner, MainViewState::fetchStatus) {
                when (it) {
                    is FetchStatus.Fetched -> {
                        mBinding.swipeRefreshLayout.isRefreshing = false
                    }
                    is FetchStatus.NotFetched -> {
                        viewModel.dispatch(MainViewAction.FetchNews)
                        mBinding.swipeRefreshLayout.isRefreshing = false
                    }
                    is FetchStatus.Fetching -> {
                        mBinding.swipeRefreshLayout.isRefreshing = true
                    }
                }
            }

        }

        activity?.let {
            viewModel.viewEvents.observeEvent(it){
                renderEvent(it)
            }
        }
    }

    private fun renderEvent(viewEvent: MainViewEvent){
        when(viewEvent){
            is MainViewEvent.ShowToast -> toast(viewEvent.message)
            is MainViewEvent.ShowSnackbar -> {
                Snackbar.make(mBinding.coordinatorLayoutRoot, viewEvent.message, Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }



}