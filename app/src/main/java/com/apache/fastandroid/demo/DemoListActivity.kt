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
import com.apache.fastandroid.demo.constraint.ConstraintLayoutDemoFragment
import com.apache.fastandroid.demo.doraemonkit.DoraemonkitDemoFragment
import com.apache.fastandroid.hencoder.basic.DrawBasicDemoFragment
import com.hencoder.hencoderpracticedraw2.DrawPaintDemoFragment
import com.apache.fastandroid.jetpack.lifecycle.JetPackLifeCycleFragment
import com.apache.fastandroid.jetpack.lifecycle.TraditionalLifeCycleFragment
import com.apache.fastandroid.jetpack.livedata.LiveDataFragment
import com.apache.fastandroid.jetpack.livedataviewmodel.LiveDataViewModelFragment
import com.apache.fastandroid.jetpack.viewmodel.ViewModelFragment
import com.tesla.framework.ui.activity.BaseActivity
import com.tesla.framework.ui.activity.FragmentContainerActivity
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2020/10/31.
 */
class DemoListActivity : BaseActivity() {
    companion object {
        private val MODELS = arrayListOf(
                ViewItemBean("JetPack", "", null),
                //空出一行来
                ViewItemBean(),
                ViewItemBean("Normal", "传统生命周期监听", TraditionalLifeCycleFragment::class.java),
                ViewItemBean("JetPack", "LifeCycle", JetPackLifeCycleFragment::class.java),
                ViewItemBean("JetPack", "ViewModel", ViewModelFragment::class.java),
                ViewItemBean("JetPack", "LiveData", LiveDataFragment::class.java),
                ViewItemBean("JetPack", "LiveDataViewModel", LiveDataViewModelFragment::class.java),
                ViewItemBean("JetPack", "LiveDataViewModel", LiveDataViewModelFragment::class.java),


                ViewItemBean("Hencoder自定义View", "", null),
                //空出一行来
                ViewItemBean(),
                ViewItemBean("Hencoder", "绘制基础", DrawBasicDemoFragment::class.java),
                ViewItemBean("Hencoder", "绘制Paint", DrawPaintDemoFragment::class.java),
                ViewItemBean("CustomViewWidget", "第三方控件", CustomViewFragment::class.java),
                ViewItemBean("ConstraintLayout", "约束布局", ConstraintLayoutDemoFragment::class.java),
                ViewItemBean("Doraemonkit", "Doraemonkit", DoraemonkitDemoFragment::class.java)


        )


    }


    override fun inflateContentView(): Int {
        return R.layout.activity_demo_list
    }


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        recycleview.apply {
            layoutManager = GridLayoutManager(this@DemoListActivity, 2)
            adapter = ItemViewAdapter()
        }

//        FragmentContainerActivity.launch(this,CustomViewFragment::class.java,null)

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
                if (viewItemBean.clazz == null) {
                    return@setOnClickListener
                }
                FragmentContainerActivity.launch(this@DemoListActivity, viewItemBean.clazz, null)
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

        val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        val txt_description: TextView = itemView.findViewById(R.id.txt_description)
    }
}