package com.tesla.framework.ui.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.databinding.CommUiRecycleviewSwiperefreshNewBinding

/**
 * Created by Jerry on 2022/3/12.
 */
open abstract class ARecycleViewSwipeRefreshFragmentNew<T>:BaseVBFragment<CommUiRecycleviewSwiperefreshNewBinding>(CommUiRecycleviewSwiperefreshNewBinding::inflate),
    BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    protected lateinit var mAdapter:BaseQuickAdapter<T, BaseViewHolder>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mAdapter = createAdapter()

        viewBinding.recycleview.apply {
            setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                onScrollChange(v,scrollX,scrollY,oldScrollX,oldScrollY)
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    onRecycleViewScrollStateChanged(recyclerView, newState)
                }
            })
            adapter = mAdapter
        }
        mAdapter.run {
            setOnLoadMoreListener(this@ARecycleViewSwipeRefreshFragmentNew, viewBinding.recycleview)
            setOnItemClickListener { adapter, view, position ->
                onItemClick(adapter, view, data[position])
            }
            setOnItemChildClickListener { adapter, view, position ->
                onItemChildClick(adapter,view,data[position])
            }
        }

    }

    open fun onItemChildClick(
        adapter: BaseQuickAdapter<Any, BaseViewHolder>, view: View, t: T
    ) {

    }

    open fun onItemClick(adapter: BaseQuickAdapter<Any, BaseViewHolder>?, view: View?, t: T) {

    }

    abstract fun createAdapter():BaseQuickAdapter<T, BaseViewHolder>


    override fun onLoadMoreRequested() {

    }



    override fun onRefresh() {
    }

    open fun onScrollChange(
        v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int,
        oldScrollY: Int
    ) {

    }

    open fun onRecycleViewScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

    }


    /**
     * 默认是LinearLayoutManager
     *
     * @return
     */
    protected open fun configLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(activity)
    }

    /**
     * 获取第一个可见的position
     * @return
     */
    protected open fun getFirstVisiblePosition(): Int {
        if (viewBinding.recycleview.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = viewBinding.recycleview.layoutManager as LinearLayoutManager
            return linearLayoutManager.findFirstVisibleItemPosition()
        }
        return 0
    }

    protected fun setupSwipeRefreshLayout() {
        viewBinding.swipeRefreshLayout.apply {
            setOnRefreshListener(this@ARecycleViewSwipeRefreshFragmentNew)
            setColorSchemeResources(
                    R.color.holo_blue_bright,
                    R.color.holo_green_light,
                    R.color.holo_orange_light,
                    R.color.holo_red_light
                )
        }

    }



    open fun showRefreshing() {
        viewBinding.swipeRefreshLayout.run {
            if (!isRefreshing){
                isRefreshing = true
            }
        }

    }

    open fun dismissRefreshing() {
        viewBinding.swipeRefreshLayout.run {
            if (isRefreshing){
                isRefreshing = false
            }
        }
    }
}