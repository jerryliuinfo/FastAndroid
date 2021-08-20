package com.apache.fastandroid.demo.snaphelper

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.recycleview.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.ui.widget.GallerySnapHelper
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2021/8/20.
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
        }
        val snapHelper = GallerySnapHelper()
        snapHelper.attachToRecyclerView(recycleview)

    }




    override fun inflateContentView(): Int {
        return R.layout.snaphepler
    }
}