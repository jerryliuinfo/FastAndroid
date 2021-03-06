package com.apache.fastandroid.demo

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.databinding.FragmentCustomViewBinding
import com.tesla.framework.ui.activity.FragmentArgs
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2020/12/3.
 */
abstract class BaseListFragment: BaseFragment() {


    private lateinit var MODELS:ArrayList<ViewItemBean>

    abstract fun initDatas():ArrayList<ViewItemBean>

    override fun inflateContentView(): Int {
        return R.layout.fragment_custom_view
    }

    companion object{
        fun launch(from:Activity, MODELS:ArrayList<ViewItemBean> ){
            val fragmentArgs = FragmentArgs()
            fragmentArgs.add("models", MODELS)
            FragmentContainerActivity.launch(from,BaseListFragment::class.java,fragmentArgs)
        }
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        MODELS = initDatas()


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
                val args = FragmentArgs()
                args.add("title", viewItemBean.title)
                FragmentContainerActivity.launch(this@BaseListFragment.activity,viewItemBean.clazz,args)
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


    class ItemViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }

        val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        val txt_description: TextView = itemView.findViewById(R.id.txt_description)
    }
}