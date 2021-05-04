package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.apache.fastandroid.demo.adapter.CommentAdapterNew
import com.apache.fastandroid.demo.recycleview.footer.FootView
import com.apache.fastandroid.demo.recycleview.header.HeaderView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.base_recycleview_adapter_demo.*

/**
 * Created by Jerry on 2021/5/3.
 */
class RecycleViewHeaderFootFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.base_recycleview_adapter_demo
    }

    private lateinit var headerAndFooterAdapter:BaseQuickAdapter<String,BaseViewHolder>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        headerAndFooterAdapter = CommentAdapterNew(listOf("aaa","bbb", "ccc","eee"))


        val headerView = HeaderView(context!!,0)
        headerView.setListener {
            addNewHeaderView()
        }
        //添加headerView
        headerAndFooterAdapter.addHeaderView(headerView)


        //添加footView
        val footView = FootView(context!!,0)
        headerAndFooterAdapter.addFooterView(footView)
        footView.setOnClickListener {
           addNewFootView()
        }

        recycleview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        recycleview.adapter = headerAndFooterAdapter

        //设置点击事件
        headerAndFooterAdapter.setOnItemClickListener { adapter, view, position ->
            var item = adapter.getItem(position) as String
            ToastUtils.showShort("$item:$position")
        }
    }





    private fun addNewHeaderView(){
        val newHeaderView = HeaderView(context!!,1)
        newHeaderView.setOnClickListener {
            headerAndFooterAdapter.removeHeaderView(it)
        }
        headerAndFooterAdapter.addHeaderView(newHeaderView)
    }

    private fun addNewFootView(){
        val newFootView = FootView(context!!,1)
        newFootView.setOnClickListener {
            headerAndFooterAdapter.removeFooterView(newFootView)
        }
        headerAndFooterAdapter.addFooterView(newFootView)
    }
}