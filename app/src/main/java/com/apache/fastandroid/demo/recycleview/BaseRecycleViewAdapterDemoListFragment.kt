package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.adapter.CommentAdapter
import com.apache.fastandroid.demo.adapter.CommentAdapterNew
import kotlinx.android.synthetic.main.android_basic_recycleview_adapter.*

/**
 * Created by Jerry on 2021/5/3.
 */
class BaseRecycleViewAdapterDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("基本用法","基本用法",RecycleViewBasicFragment::class.java)
                ,ViewItemBean("Header","Header",RecycleViewHeaderFootFragment::class.java)
        )
    }

}