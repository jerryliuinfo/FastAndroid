package com.apache.fastandroid.demo.basic

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.demo.adapter.CommentAdapter
import kotlinx.android.synthetic.main.android_basic_recycleview_adapter.*


class RecycleViewAdaoterFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.android_basic_recycleview_adapter
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val adapter = CommentAdapter()
        adapter.setDatas(listOf("aaa","bbb", "ccc","ddd"))
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
    }
}