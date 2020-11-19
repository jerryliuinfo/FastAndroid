package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean

import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2020/11/11.
 */
class CustomViewFragment:ABaseFragment() {

    companion object {
        private val MODELS = arrayListOf(
                ViewItemBean("第三方控件", "RoudImageView", RoudImageViewFragment::class.java)
        )
    }


    override fun inflateContentView(): Int {
        return R.layout.fragment_custom_view
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        recycleview.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = ItemViewAdapter()
        }

    }

    private inner class ItemViewAdapter : RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(viewType, parent, false)
            return ItemViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return MODELS.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val viewItemBean = MODELS[position]
            holder.txtTitle.text = viewItemBean.title
            holder.txt_description.text = viewItemBean.description
            holder.itemView.setOnClickListener {
                if (viewItemBean.clazz == null){
                    return@setOnClickListener
                }
                FragmentContainerActivity.launch(this@CustomViewFragment.activity,viewItemBean.clazz,null)
            }
        }

        override fun getItemViewType(position: Int): Int {
            val viewItemBean = MODELS[position]
            return if (viewItemBean.clazz == null) {
                R.layout.layout_cell_bord_item_title
            } else {
                R.layout.layout_cell_bord_item
            }
        }

    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        val txt_description: TextView = itemView.findViewById(R.id.txt_description)
    }

}