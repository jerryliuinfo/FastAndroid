package com.apache.fastandroid.demo.blitz

import android.widget.TextView
import com.apache.fastandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tesla.framework.component.blitz.setTimeAgo
import com.tesla.framework.ui.fragment.recycleview.BaseBRVHFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Jerry on 2023/8/27.
 */
class BlitzDemoFragment : BaseBRVHFragment<Date, BaseViewHolder>() {
    override fun getDatas(): MutableList<Date> {
        return DATES.toMutableList()
    }

    override fun myAdapter(): BaseQuickAdapter<Date, BaseViewHolder> {
        return object : BaseQuickAdapter<Date, BaseViewHolder>(R.layout.item_blitz_main) {
            override fun convert(holder: BaseViewHolder, time: Date) {
                holder.getView<TextView>(R.id.item_date).text = time.format()
                holder.getView<TextView>(R.id.item_relative_time)
                    .setTimeAgo(time, showSeconds = true)
            }

        }
    }
}

private const val FORMAT = "yyyy-MM-dd HH:mm:ss"

internal fun Date.format(): String {
    return SimpleDateFormat(FORMAT, Locale.getDefault()).format(this)
}