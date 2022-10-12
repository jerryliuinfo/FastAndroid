package com.apache.fastandroid.demo.basic

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.RecycleviewItemDecorationBinding
import com.apache.fastandroid.demo.adapter.CommentAdapter
import com.apache.fastandroid.demo.decoration.CommentItemDecoration
import com.blankj.utilcode.util.ResourceUtils
import com.tesla.framework.kt.removeDecorations
import com.tesla.framework.ui.fragment.BaseVBFragment
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.recycleview_item_decoration.*


class RecycleViewItemDecorationFragment: BaseVBFragment<RecycleviewItemDecorationBinding>(RecycleviewItemDecorationBinding::inflate) {

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
//                it.addItemDecoration(CommentItemDecoration(it.context))
            }

        }
        tv_horizontal_divider.setOnClickListener {
            recycleview.apply {
                removeDecorations()
                addItemDecoration( HorizontalDividerItemDecoration.Builder(context)
                    .color(Color.RED)
                    .sizeResId(R.dimen.divider_height)
                    .marginResId(R.dimen.divider_height)
                    .build()
                )
//                    .addItemDecoration(
//                        HorizontalDividerItemDecoration.Builder(context)
//                            .color(Color.RED)
//                            .sizeResId(R.dimen.divider_height)
//                            .marginResId(R.dimen.divider_height)
//                            .build()
//                    )
            }
          /*  recycleview.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(context)
                    .color(Color.RED)
                    .sizeResId(R.dimen.divider_height)
                    .marginResId(R.dimen.divider_height)
                    .build()
            )*/
        }





    }








}