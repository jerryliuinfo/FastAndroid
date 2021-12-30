package com.apache.fastandroid.demo.basic

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.apache.fastandroid.demo.adapter.CommentAdapter
import com.apache.fastandroid.demo.decoration.CommentItemDecoration
import com.blankj.utilcode.util.ResourceUtils
import com.tesla.framework.kt.removeDecorations
import kotlinx.android.synthetic.main.android_basic_recycleview_adapter.recycleview
import kotlinx.android.synthetic.main.recycleview_item_decoration.*


class RecycleViewItemDecorationFragment: BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.recycleview_item_decoration
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val myAdapter = CommentAdapter(listOf("aaa","bbb", "ccc","ddd"))

        recycleview.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
            addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        }
        tv_default_divider.setOnClickListener {
            recycleview.let {
                it.removeDecorations()
                it.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
            }
        }

        tv_modify_divider.setOnClickListener {
            recycleview.let {
                it.removeDecorations()
                it.addItemDecoration(DividerItemDecoration(context,RecyclerView.VERTICAL).apply {
                    setDrawable(ResourceUtils.getDrawable(R.drawable.drawable_divider))
                })
            }

        }

        tv_custom_divider.setOnClickListener {
            recycleview.let {
                it.removeDecorations()
                it.addItemDecoration(CommentItemDecoration())
            }

        }



    }








}