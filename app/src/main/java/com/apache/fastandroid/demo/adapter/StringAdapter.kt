package com.apache.fastandroid.demo.adapter

import com.apache.fastandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by Jerry on 2021/5/3.
 */
class StringAdapter(data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_comment, data.toMutableList()) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_title, item)
    }
}


