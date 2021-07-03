package com.apache.fastandroid.demo.basic

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.demo.adapter.CommentAdapter
import com.apache.fastandroid.demo.adapter.CommentItemDecoration
import com.blankj.utilcode.util.ResourceUtils
import kotlinx.android.synthetic.main.android_basic_recycleview_adapter.recycleview
import kotlinx.android.synthetic.main.recycleview_item_decoration.*


class RecycleViewItemDecorationFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.recycleview_item_decoration
    }

    private lateinit var defaultDividerItemDecoration: DividerItemDecoration
    private lateinit var commentItemDecoration: CommentItemDecoration

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val adapter = CommentAdapter(listOf("aaa","bbb", "ccc","ddd"))
        recycleview.adapter = adapter
        recycleview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)



        defaultDividerItemDecoration = DividerItemDecoration(activity,DividerItemDecoration.VERTICAL)
        recycleview.addItemDecoration(defaultDividerItemDecoration)


        commentItemDecoration = CommentItemDecoration()


        tv_modify_divider.setOnClickListener {
            recycleview.removeItemDecoration(defaultDividerItemDecoration)
            defaultDividerItemDecoration.setDrawable(ResourceUtils.getDrawable(R.drawable.drawable_divider))
            recycleview.addItemDecoration(defaultDividerItemDecoration)
        }

        tv_custom_divider.setOnClickListener {
            recycleview.removeItemDecoration(defaultDividerItemDecoration)

            commentItemDecoration.let {
               recycleview.removeItemDecoration(commentItemDecoration)
           }
            recycleview.addItemDecoration(commentItemDecoration)
        }



    }






}