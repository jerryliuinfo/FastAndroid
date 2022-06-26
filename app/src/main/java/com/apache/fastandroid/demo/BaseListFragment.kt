package com.apache.fastandroid.demo

import android.app.Activity
import android.content.Intent
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
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.FragmentArgs
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2020/12/3.
 */
abstract class BaseListFragment: BaseVBFragment<FragmentCustomViewBinding>(FragmentCustomViewBinding::inflate) {


    private lateinit var MODELS:ArrayList<ViewItemBean>

    abstract fun initDatas():ArrayList<ViewItemBean>

    private lateinit var mAdapter:ItemViewAdapter


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
        setupLayoutManager()
        mAdapter = ItemViewAdapter()
        recycleview.adapter = mAdapter
    }

    open fun setupLayoutManager(){
        recycleview.layoutManager = GridLayoutManager(activity, 2)
    }



    private inner class ItemViewAdapter() : RecyclerView.Adapter<ItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(viewType, parent, false)
            return ItemViewHolder(itemView )
        }

        override fun getItemCount(): Int {
            return MODELS.size
        }



        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val viewItemBean = MODELS[position]
            holder.txtTitle.text = viewItemBean.title
            holder.txt_description.text = viewItemBean.description
           /* holder.itemView.setOnClickListener {
                if (viewItemBean.clazz == null && viewItemBean.activity == null){
                    return@setOnClickListener
                }
                if (viewItemBean.clazz != null){
                    val args = FragmentArgs.transToArgs(viewItemBean.args)
                    args.add("title", viewItemBean.title)
                    this@BaseListFragment.activity?.let { it1 ->
                        FragmentContainerActivity.launch(
                            it1,viewItemBean.clazz, args = args, addTitleBar = viewItemBean.addTitleBar)
                    }
                }else if (viewItemBean.activity != null){
                    val intent = Intent(requireActivity(), viewItemBean.activity)
                    startActivity(intent)
                }

            }*/
        }

        override fun getItemViewType(position: Int): Int {
            val viewItemBean = MODELS[position]
            return if (viewItemBean.clazz == null && viewItemBean.activity == null) {
                R.layout.layout_cell_bord_item_title
            } else {
                R.layout.layout_cell_bord_item
            }
        }

    }


    private inner class ItemViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                /**
                 * absoluteAdapterPosition 和 bidingAdapterPostion的区别：
                 * 在没有使用 MergeAdapter时，两者获取的值是一样的
                 * 如果使用了 MergeAdapter，bidingAdapterPostion 得到的是元素位于
                 * 当前绑定 Adapter 的位置，而 absoluteAdapterPosition 方法得到的是
                 * 在列表中的绝对位置
                 */
                val viewItemBean = MODELS[absoluteAdapterPosition]
                if (viewItemBean.clazz == null && viewItemBean.activity == null){
                    return@setOnClickListener
                }
                if (viewItemBean.clazz != null){
                    val args = FragmentArgs.transToArgs(viewItemBean.args)
                    args.add("title", viewItemBean.title)
                    this@BaseListFragment.activity?.let { it1 ->
                        FragmentContainerActivity.launch(
                            it1,viewItemBean.clazz, args = args, addTitleBar = viewItemBean.addTitleBar)
                    }
                }else if (viewItemBean.activity != null){
                    val intent = Intent(requireActivity(), viewItemBean.activity)
                    startActivity(intent)
                }

            }
        }

        val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        val txt_description: TextView = itemView.findViewById(R.id.txt_description)
    }
}