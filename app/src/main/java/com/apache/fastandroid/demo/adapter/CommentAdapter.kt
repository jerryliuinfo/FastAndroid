package com.apache.fastandroid.demo.adapter

import com.apache.fastandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by Jerry on 2021/5/3.
 */
class CommentAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_comment, data) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_title, item)
    }
}


