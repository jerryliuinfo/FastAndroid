package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.demo.adapter.CommentAdapterNew
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.base_recycleview_adapter_demo.*

/**
 * Created by Jerry on 2021/5/3.
 */
class RecycleViewBasicFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.base_recycleview_adapter_demo
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        val adapter = CommentAdapterNew(listOf("aaa","bbb", "ccc","eee"))
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        adapter.setOnItemClickListener() { adapter, view, position ->
            var item = adapter.getItem(position)
            ToastUtils.showShort(item as String)
        }
    }
}