package com.apache.fastandroid.demo.coorinator

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.widget.SpaceItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.coordinator_basic.*

/**
 * Created by Jerry on 2020/11/19.
 */
class CoordinatorBasicFragment: BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.coordinator_basic
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val itemAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_text) {
            override fun convert(helper: BaseViewHolder, item: String?) {
                helper.getView<TextView>(R.id.tv_name).text = item
            }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
            adapter = itemAdapter
            addItemDecoration(SpaceItemDecoration(10))
        }

        itemAdapter.setNewData(getDatas())
    }
    private fun getDatas():List<String>{
        var list = mutableListOf<String>()
        for( i in 0..40){
            list.add("item:${i}")
        }
        return list
    }
}