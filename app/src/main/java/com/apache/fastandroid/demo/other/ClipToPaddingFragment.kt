package com.apache.fastandroid.demo.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.clip_to_padding_layout_item.view.*
import kotlinx.android.synthetic.main.custom_view_clip_to_padding.*

/**
 * Created by Jerry on 2020/12/2.
 */
class ClipToPaddingFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.custom_view_clip_to_padding
    }

    companion object{
        private val list = arrayListOf(
                                                  "A","B","C","D","E",
                                                  "A","B","C","D","E",
                                                  "A","B","C","D","E",
                                                  "A","B","C","D","E",
                                                    "A","B","C","D","E",
                                                    "A","B","C","D","E",
                                                    "A","B","C","D","E",
                                                    "A","B","C","D","E"
        )
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        recycleview.apply {
            adapter = MyAdapter(list)
            layoutManager = LinearLayoutManager(this@ClipToPaddingFragment.context,RecyclerView.VERTICAL,false)
        }
    }

    private class MyAdapter(val data:List<String>) : RecyclerView.Adapter<ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.clip_to_padding_layout_item, parent, false)
            return ItemViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.view.tv_name.text = data[position]
        }

    }

    private class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view)


}