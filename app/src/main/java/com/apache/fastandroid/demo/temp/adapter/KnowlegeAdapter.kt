package com.apache.fastandroid.demo.temp.adapter

import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by Jerry on 2021/10/21.
 */
class KnowlegeAdapter: BaseQuickAdapter<ViewItemBean, BaseViewHolder>(R.layout.layout_cell_bord_item_title) {
    override fun convert(helper: BaseViewHolder, item: ViewItemBean) {

    }
}