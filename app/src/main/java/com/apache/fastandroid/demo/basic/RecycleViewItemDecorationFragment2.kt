package com.apache.fastandroid.demo.basic

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.RecycleviewItemDecoration2Binding
import com.apache.fastandroid.demo.adapter.CommentAdapter
import com.tesla.framework.kt.dp
import com.tesla.framework.kt.removeDecorations
import com.tesla.framework.ui.fragment.BaseVBFragment
import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.recycleview_item_decoration.tv_horizontal_divider
import kotlinx.android.synthetic.main.recycleview_item_decoration2.*


class RecycleViewItemDecorationFragment2: BaseVBFragment<RecycleviewItemDecoration2Binding>(RecycleviewItemDecoration2Binding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val myAdapter = CommentAdapter(mockData())
        recycleview.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        }


        tv_horizontal_divider.setOnClickListener {
            recycleview.apply {
                removeDecorations()
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
                addItemDecoration( HorizontalDividerItemDecoration.Builder(context)
                    .color(Color.RED)
                    .sizeResId(R.dimen.divider_height)
                    .marginResId(R.dimen.divider_margin)
                    .build()
                )
            }

        }
        tv_paint_divider.setOnClickListener {
            recycleview.apply {
                removeDecorations()
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)

                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    strokeWidth = 2.dp
                    color = Color.BLUE
                    pathEffect = DashPathEffect(floatArrayOf(25f,0f,25f,0f),0f)
                }
                val paintDecoration = HorizontalDividerItemDecoration.Builder(context)
                    .paint(paint)
                    .margin(20.dp.toInt())
//                    .showLastDivider()
                    .build()
                addItemDecoration(paintDecoration)
            }
        }

        tv_drawable_divider.setOnClickListener {
            recycleview.apply {
                removeDecorations()
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
                val drawableDecoration = HorizontalDividerItemDecoration.Builder(context)
                    .drawable(R.drawable.drawable_divider)
                    .size(15)
                    .margin(20.dp.toInt())
                    .visibilityProvider(object :FlexibleDividerDecoration.VisibilityProvider{
                        override fun shouldHideDivider( position:Int, parent:RecyclerView ): Boolean {
                            return position == 1
                        }

                    })
                    .build()

                addItemDecoration(drawableDecoration)
            }
        }
        tv_grider_divider.setOnClickListener {
            recycleview.apply {
                removeDecorations()
                layoutManager = GridLayoutManager(context,3)
                var decoration = HorizontalDividerItemDecoration.Builder(context)
                    .color(Color.BLUE)
                    .size(5)
                    .build()
                addItemDecoration(decoration)
            }
        }

    }

    private fun mockData():List<String>{
        return arrayListOf(
            "aaa","bbb", "ccc","ddd", "eee","fff","ggg","hhh","aaa","bbb", "ccc","ddd"
        )

    }

}