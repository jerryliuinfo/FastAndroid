package com.apache.fastandroid.demo.recycleview.multiitemtype.item

import android.view.View
import android.widget.Toast
import androidx.annotation.Keep
import com.apache.fastandroid.databinding.Item00Binding
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ItemBean
import com.tencent.lib.multi.core.SimpleItemType

/**

 * Author：岑胜德 on 2021/12/5 20:31

 * 说明：

 */
class ItemType00 : SimpleItemType<ItemBean, Item00Binding>() {

    init {
        bind(this)
    }

    override fun isMatched(bean: Any?, position: Int): Boolean {
        return bean is ItemBean && bean.viewType() == ItemBean.TYPE_00
    }


    override fun onBindView(vb: Item00Binding, bean: ItemBean, position: Int) {
        vb.tvA.text = bean.text
    }

    /**
     *item点击事件
     */
    @Keep
    private fun onClickItem(view: View, itemBean: ItemBean, position: Int) {
        Toast.makeText(
            view.context,
            "点击事件：ItemBean:${itemBean.text},position:$position",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * item 长点击事件
     */
    @Keep
    private fun onLongClick(view: View, itemBean: ItemBean, position: Int): Boolean {
        Toast.makeText(
            view.context,
            "长点击事件：ItemBean:${itemBean.text},position:$position",
            Toast.LENGTH_SHORT
        ).show()
        return true
    }

}