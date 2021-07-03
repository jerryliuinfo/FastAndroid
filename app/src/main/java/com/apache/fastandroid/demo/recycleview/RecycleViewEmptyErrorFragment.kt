package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.demo.adapter.EmptyAdapter
import com.apache.fastandroid.demo.recycleview.empty.EmptyView
import com.apache.fastandroid.demo.recycleview.fail.ErrorView
import com.apache.fastandroid.demo.recycleview.loading.LoadingView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.base_recycleview_adapter_demo.*
import java.util.*

/**
 * Created by Jerry on 2021/5/3.
 */
class RecycleViewEmptyErrorFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.base_recycleview_adapter_demo
    }

    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    private lateinit var loadingView: LoadingView
    private var notDataView:EmptyView ?= null
    private var errorView:ErrorView ?= null

    private var mError = true
    private var mNoData = true

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        adapter = EmptyAdapter(Collections.emptyList())

        recycleview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        recycleview.adapter = adapter

        //设置点击事件
        adapter.setOnItemClickListener { adapter, view, position ->
            var item = adapter.getItem(position) as String
            ToastUtils.showShort("$item:$position")
        }
        loadingView = LoadingView(activity!!)

        onRefresh()

    }

    private fun onRefresh(){
        adapter.emptyView = loadingView
        Handler().postDelayed({
            if (mError) {
                if (errorView == null){
                    errorView = ErrorView(activity!!)
                    errorView!!.setOnClickListener {
                        onRefresh()
                    }
                }
                adapter.emptyView = errorView
                mError = false
            } else {
                if (mNoData) {
                    if (notDataView == null){
                        notDataView = EmptyView(activity!!)
                        notDataView!!.setOnClickListener {
                            onRefresh()
                        }
                    }
                    adapter.emptyView = notDataView
                    mNoData = false
                } else {
                    adapter.setNewData(listOf("aaa","bbb","ccc"))
                }
            }
        }, 1000)

    }






}