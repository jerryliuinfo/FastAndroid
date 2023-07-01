package com.apache.fastandroid.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.article.ArticleDetailActivity
import com.apache.fastandroid.databinding.FragmentHomeBinding
import com.apache.fastandroid.network.model.Article
import com.bumptech.glide.Glide
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.example.android.architecture.blueprints.todoapp.util.setupRefreshLayout
import com.kingja.loadsir.core.LoadService
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseDataBindingFragment

/**
 * Created by Jerry on 2022/4/23.
 */
class HomeFragment:BaseDataBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mViewModel:HomeViewModel by viewModels { getViewModelFactory() }


    private  var mAdapter: ArticleAdapter =  ArticleAdapter(emptyList(), listener = { view, index ->
        Logger.d("onClick Author index:$index")

    }).apply {
        animationEnable = true
        initLoadMore(this)
        setOnItemClickListener{ adapter,view,position ->
            val article = getItem(position)
            ArticleDetailActivity.launch(requireActivity(), article.title, article.link)

        }
    }

    private lateinit var loadService: LoadService<Any>


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.apply {
//            adapter = mAdapter
            vm = mViewModel
        }

        mBinding.recycleview.apply {
            LinearSnapHelper().attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            adapter = mAdapter

        }


        setupRefreshLayout(mBinding.swipeRefreshLayout,mBinding.recycleview)


        mViewModel.articleList.observe(this){
            if (it.isSuccess){
//                loadService.showSuccess()
                handleData(it.getOrNull()?: emptyList())
            }else{
//                loadService.showEmpty()
            }

        }
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

    companion object{
        fun newInstance():HomeFragment{
            return HomeFragment()
        }
    }


    override fun onLowMemory() {
        super.onLowMemory()

        Glide.get(requireContext()).clearMemory()
    }





}