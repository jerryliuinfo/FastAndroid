package com.apache.fastandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apache.fastandroid.R
import com.apache.fastandroid.article.ArticleDetailActivity
import com.apache.fastandroid.databinding.FragmentHomeBinding
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.util.InjectorUtil
import com.tesla.framework.ui.fragment.BaseDBFragment

/**
 * Created by Jerry on 2022/4/23.
 */
class HomeFragmentNew:BaseDBFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mHomeViewModel:HomeViewModel by viewModels { InjectorUtil.getHomeModelFactory() }

    private lateinit var  swipeRefreshLayout: SwipeRefreshLayout

    private  var mAdapter: ArticleAdapter =  ArticleAdapter(emptyList()).apply {
        animationEnable = true
        initLoadMore(this)
        setOnItemClickListener{ adapter,view,position ->
            val article = getItem(position)
            ArticleDetailActivity.launch(requireActivity(), article.title, article.link)
        }
    }

    override fun bindUI(rootView: View?) {
        super.bindUI(rootView)
        swipeRefreshLayout = viewBinding.swipeRefreshLayout
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.apply {
            adapter = mAdapter
        }

        swipeRefreshLayout.apply {
//            setupRefreshLayout(this)
            setColorSchemeColors(
                ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
                ContextCompat.getColor(requireActivity(), R.color.colorAccent),
                ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
            )
            setOnRefreshListener {
                refresh()
            }
        }


        mHomeViewModel.articleList.observe(this){
            handleData(it)
        }
    }

    override fun onStart() {
        super.onStart()
        // 进入页面，刷新数据
        swipeRefreshLayout.isRefreshing = true
        refresh()
    }

    /**
     * 刷新
     */
    private fun refresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        // 下拉刷新，需要重置页数
        mHomeViewModel.onRefresh()
    }

    private fun handleData(data:List<Article>){
        mAdapter.loadMoreModule.isEnableLoadMore= true
        if (viewBinding.swipeRefreshLayout.isRefreshing){
            viewBinding.swipeRefreshLayout.isRefreshing = false
        }

        if (mHomeViewModel.pageInfo.isFirstPage()) {
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
        mHomeViewModel.pageInfo.nextPage()

    }

    private fun initLoadMore(adapter: ArticleAdapter) {
        adapter.loadMoreModule.apply {
            setOnLoadMoreListener{
                mHomeViewModel.loadMore()
            }
            isAutoLoadMore = true
            //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
            isEnableLoadMoreIfNotFullPage = false
        }

    }





}