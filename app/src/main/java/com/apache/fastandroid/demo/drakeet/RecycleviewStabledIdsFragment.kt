package com.apache.fastandroid.demo.drakeet

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.DrakeetRecycleviewSetHasStableidsBinding
import com.microsoft.sample.stableids.Item
import com.microsoft.sample.stableids.StableIdAdapter
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/10/18.
 * https://t.zsxq.com/Fi2JYzN
 * 加上这一行代码，在 item change、remove、udpate以及item position改变的情况下，
 * 调用 adapter 的notifyDatasetChanged 自动会有动画效果的
 */
class RecycleviewStabledIdsFragment:BaseBindingFragment<DrakeetRecycleviewSetHasStableidsBinding>(DrakeetRecycleviewSetHasStableidsBinding::inflate) {
    private var items: MutableList<Item> = ArrayList()
    private lateinit var adapter: StableIdAdapter

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        adapter = StableIdAdapter(items)

        //加上这一行代码，在 item change、remove、udpate以及item position改变的情况下，调用 adapter 的notifyDatasetChanged 自动会有动画效果的
        adapter.setHasStableIds(true) // 💡💡💡
        recyclerView.adapter = adapter

        initRightActions()
    }


    private fun initRightActions() {
        with(findViewById<LinearLayout>(R.id.actions)) {
            addView(context.newActionTextView(
                """
        (0..20).forEach { items.add(Item()) }
        """.trimIndent()
            ) {
                (0..20).forEach {
                    items.add(Item("Subject $it", "Summary $it"))
                }
                adapter.notifyDataSetChanged()
            })

            addView(context.newActionTextView(
                """
        items.removeAll { it.id % 2 == 0L }
        """.trimIndent()
            ) {
                items.removeAll { it.id % 2 == 0L }
                adapter.notifyDataSetChanged()
            })

            addView(context.newActionTextView(
                """
        items.add(1, Item("Added Subject 999", "Summary 999"))
        """.trimIndent()
            ) {
                items.add(1, Item("Added Subject 999", "Summary 999"))
                adapter.notifyDataSetChanged()
            })

            addView(context.newActionTextView(
                """
        items[1].subject = "Updated"
        """.trimIndent()
            ) {
                items[1].subject = "Updated"
                adapter.notifyDataSetChanged()
            })

            addView(context.newActionTextView(
                """
        items.subList(0, 5).shuffle()
        """.trimIndent()
            ) {
                items.subList(0, 5).shuffle()
                adapter.notifyDataSetChanged()
            })

            addView(context.newActionTextView(
                """
        items.clear()
        """.trimIndent()
            ) {
                items.clear()
                adapter.notifyDataSetChanged()
            })
        }
    }


    fun Context.newActionTextView(text: String, onClick: () -> Unit): TextView {
        return AppCompatTextView(this)
            .apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                setRippleBackground()
                setPadding(6.dp, 6.dp, 6.dp, 6.dp)
                setOnClickListener {
                    try {
                        onClick()
                    } catch (e: Exception) {
                        Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                setHorizontallyScrolling(true)
                setText(text)
            }
    }

    fun View.setRippleBackground() = with(TypedValue()) {
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
        setBackgroundResource(resourceId)
    }

    val Number.dp get() = this.toInt() * 4

}