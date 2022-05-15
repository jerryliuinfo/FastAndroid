package com.apache.fastandroid.home

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.apache.fastandroid.article.ArticleDetailActivity
import com.apache.fastandroid.databinding.FragmentHomeBinding
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.apache.fastandroid.util.extensitons.showEmpty
import com.apache.fastandroid.util.extensitons.showLoading
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.example.android.architecture.blueprints.todoapp.util.setupRefreshLayout
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseDBFragment
import kotlinx.android.synthetic.main.activity_loadsir_constraintlayout.*

/**
 * Created by Jerry on 2022/4/23.
 */
class HomeFragmentNew:BaseDBFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mViewModel:HomeViewModel by viewModels { getViewModelFactory() }


    private  var mAdapter: ArticleAdapter =  ArticleAdapter(emptyList()).apply {
        animationEnable = true
        initLoadMore(this)
        setOnItemClickListener{ adapter,view,position ->
            val article = getItem(position)
            ArticleDetailActivity.launch(requireActivity(), article.title, article.link)
        }
    }

    private lateinit var loadService: LoadService<Any>

    override fun bindUI(rootView: View?) {
        super.bindUI(rootView)

//        loadService = LoadSir.getDefault().register(this,object : Callback.OnReloadListener{
//            override fun onReload(v: View?) {
//                mViewModel.refresh()
//            }
//
//        })

    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.apply {
            adapter = mAdapter
            vm = mViewModel
        }


        setupRefreshLayout(viewBinding.swipeRefreshLayout,viewBinding.recycleview)


        mViewModel.articleList.observe(this){
            if (it.isSuccess){
//                loadService.showSuccess()
                handleData(it.getOrNull()?: emptyList())
            }else{
//                loadService.showEmpty()
            }

        }
    }



    /**
     * 刷新
     */
    private fun refresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        // 下拉刷新，需要重置页数
//        mHomeViewModel.onRefresh()
    }

    private fun handleData(data:List<Article>){
        mAdapter.loadMoreModule.isEnableLoadMore= true
      /*  if (viewBinding.swipeRefreshLayout.isRefreshing){
            viewBinding.swipeRefreshLayout.isRefreshing = false
        }*/

        if (mViewModel.pageInfo.isFirstPage()) {
            //如果是加载的第一页数据，用 setData()
            mAdapter.setList(data)
        } else {
            //不是第一页，则用add
            mAdapter.addData(data)
        }

        if (data.isEmpty()) {
            //如果不够一页,显示没有更多数据布局
            mAdapter.loadMoreModule.loadMoreEnd()
//            Tips.show("no modelre data")
        } else {
            mAdapter.loadMoreModule.loadMoreComplete()
        }
        // page加1
        mViewModel.pageInfo.nextPage()

    }

    private fun initLoadMore(adapter: ArticleAdapter) {
        adapter.loadMoreModule.apply {
            setOnLoadMoreListener{
                mViewModel.loadMore()
            }
            isAutoLoadMore = true
            //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
            isEnableLoadMoreIfNotFullPage = false
        }

    }





}