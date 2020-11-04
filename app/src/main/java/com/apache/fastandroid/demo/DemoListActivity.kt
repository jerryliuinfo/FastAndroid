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
import com.apache.fastandroid.jetpack.lifecycle.LifeCycleFragment
import com.apache.fastandroid.jetpack.lifecycle.TraditionalLifeCycleFragment
import com.apache.fastandroid.jetpack.viewmodel.ViewModelFragment
import com.tesla.framework.ui.activity.BaseActivity
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.widget.ToastUtils
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2020/10/31.
 */
class DemoListActivity : BaseActivity() {
    companion object {
        val MODELS = arrayListOf(
                ViewItemBean("JetPack", "", null),
                //空出一行来
                ViewItemBean(),
                ViewItemBean("Normal", "传统生命周期监听", TraditionalLifeCycleFragment::class.java),
                ViewItemBean("JetPack", "LifeCycle", LifeCycleFragment::class.java),
                ViewItemBean("JetPack", "ViewModel", ViewModelFragment::class.java),
                ViewItemBean("JetPack", "LiveData", LifeCycleFragment::class.java)

        )
    }

    override fun inflateContentView(): Int {
        return R.layout.activity_demo_list
    }


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

//        mRecyclerView = findViewById<RecyclerView>(R.id.recycleview).apply {
//            layoutManager = GridLayoutManager(this@DemoListActivity, 2)
//        }
        recycleview.apply {
            layoutManager = GridLayoutManager(this@DemoListActivity, 2)
            adapter = ItemViewAdapter()

        }

    }


    inner class ItemViewAdapter : RecyclerView.Adapter<ItemViewHolder>() {
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
                //ToastUtils.showSingleToast(MODELS[index].title)
                FragmentContainerActivity.launch(this@DemoListActivity,viewItemBean.clazz,null)
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