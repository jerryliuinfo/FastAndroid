package com.apache.fastandroid.demo.snaphelper

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.widget.SpaceItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.ui.widget.snaphelper.SimpleSnaphelper
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2021/8/20.
 * 用于辅助RecyclerView在滚动结束时将Item对齐到某个位置。特别是列表横向滑动时，
 * 很多时候不会让列表滑到任意位置，而是会有一定的规则限制，这时候就可以通过SnapHelper来定义对齐规则了
 */
class SnapHelperDemoFragment:BaseFragment() {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val datas = arrayListOf("aaa", "bbb", "ccc", "aaa", "bbb", "ccc", "aaa", "bbb", "ccc", "aaa", "bbb", "ccc","aaa", "bbb", "ccc", "aaa", "bbb", "ccc", "aaa", "bbb", "ccc", "aaa", "bbb", "ccc");

        val quickAdapter:BaseQuickAdapter<String, BaseViewHolder> = object :BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_snap_helper, datas){
            override fun convert(helper: BaseViewHolder, item: String?) {
                helper.setText(R.id.tv_name,item)
            }
        }
        recycleview.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter = quickAdapter
            addItemDecoration(SpaceItemDecoration(10))
        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycleview)

    }




    override fun inflateContentView(): Int {
        return R.layout.snaphepler
    }
}