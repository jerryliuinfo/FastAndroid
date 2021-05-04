package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.apache.fastandroid.demo.adapter.PulltoRefreshAdapter
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_recycleview_pulltorefresh.*
import java.util.*

/**
 * Created by Jerry on 2021/5/3.
 */
class RecycleViewPullToRefreshFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.activity_recycleview_pulltorefresh
    }
    //这里不能用listOf 的形式，否则调用add方法会报List.add java.lang.UnsupportedOperationException
    private val datas = arrayListOf<String>("aaa","bbb","ccc","ddd","eee","fff","ggg","hhh","iii")

    private lateinit var mAdapter: BaseQuickAdapter<String,BaseViewHolder>


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        initSwipeRefresh()
        initAdapter()
        onRefresh()
    }

    private fun initAdapter(){
        recycleview.layoutManager = LinearLayoutManager(activity!!,RecyclerView.VERTICAL,false)
        mAdapter = PulltoRefreshAdapter(Collections.emptyList())
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
        mAdapter.setOnLoadMoreListener({
            loadMore()
        })
        recycleview.adapter = mAdapter
    }

    private fun initSwipeRefresh(){
        swipeLayout.setOnRefreshListener {
            onRefresh()
        }
        swipeLayout.isRefreshing = true
    }

    private fun onRefresh(){
        mAdapter.setEnableLoadMore(false) //这里的作用是防止下拉刷新的时候还可以上拉加载
        Handler().postDelayed({
            setData(true,datas)
            mAdapter.setEnableLoadMore(true)
            swipeLayout.isRefreshing = false
        },1000)
    }

    private fun loadMore(){
        Handler().postDelayed({
            /**
             * fix https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/2400
             */
            val isRefresh = mNextRequestPage == 1
            //这里

            setData(isRefresh, listOf("111","222","333","111","222","333","111","222","333"))
        },1000)
    }

    private val PAGE_SIZE = 6

    private var mNextRequestPage = 1

    private fun setData(isRefresh: Boolean, data: List<String>?) {
        mNextRequestPage++
        val size = data?.size ?: 0
        if (isRefresh) {
            mAdapter.setNewData(data)
        } else {
            if (size > 0) {
                mAdapter.addData(data!!)
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh)
            ToastUtils.showShort("no more data")
        } else {
            mAdapter.loadMoreComplete()
        }
    }





}