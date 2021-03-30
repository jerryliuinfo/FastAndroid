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
import com.apache.fastandroid.DrawBasicDemoFragment
import com.apache.fastandroid.demo.basic.AndroidBasicDemoFragment
import com.apache.fastandroid.demo.databinding.DatabindingFragmentDemo
import com.apache.fastandroid.demo.jetpack.JetPackDemoFragment
import com.apache.fastandroid.demo.performance.PerformanceDemoFragment
import com.apache.fastandroid.demo.temp.TempDemoFragment
import com.hencoder.hencoderpracticedraw2.DrawPaintDemoFragment
import com.hencoder.hencoderpracticedraw3.DrawTextDemoFragment
import com.hencoder.hencoderpracticedraw4.ClipMatrixDemoFragment
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.ui.activity.BaseActivity
import com.tesla.framework.ui.activity.FragmentArgs
import com.tesla.framework.ui.activity.FragmentContainerActivity
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2020/10/31.
 */
class DemoListActivity : BaseActivity() {
    companion object {
        private val TAG = "DemoListActivity"
        private val MODELS = arrayListOf(
                ViewItemBean("JetPack", "JetPack", JetPackDemoFragment::class.java),

                ViewItemBean("Hencoder", "绘制基础", DrawBasicDemoFragment::class.java),
                ViewItemBean("Hencoder", "绘制Paint", DrawPaintDemoFragment::class.java),
                ViewItemBean("Hencoder", "绘制文字", DrawTextDemoFragment::class.java),
                ViewItemBean("Hencoder", "绘制辅助", ClipMatrixDemoFragment::class.java),


                ViewItemBean("CustomViewWidget", "第三方控件", CustomViewFragment::class.java),
                ViewItemBean("ConstraintLayout", "约束布局", ConstraintLayoutDemoFragment::class.java),
                ViewItemBean("性能优化", "性能优化", PerformanceDemoFragment::class.java),
                ViewItemBean("Doraemonkit", "Doraemonkit", DoraemonkitDemoFragment::class.java),
                ViewItemBean("临时验证", "临时验证", TempDemoFragment::class.java),
                ViewItemBean("Android基础", "Android基础", AndroidBasicDemoFragment::class.java)
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
            FastLog.d(TAG, "onCreateViewHolder itemView: %s",itemView)
            return ItemViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return MODELS.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val viewItemBean = MODELS[position]
            holder.txtTitle.text = viewItemBean.title
            holder.txtDescription.text = viewItemBean.description
            holder.itemView.setOnClickListener {
                if (viewItemBean.clazz == null) {
                    return@setOnClickListener
                }
                val args = FragmentArgs()
                args.add("title", viewItemBean.title)
                FragmentContainerActivity.launch(this@DemoListActivity, viewItemBean.clazz, args)
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
        val txtDescription: TextView = itemView.findViewById(R.id.txt_description)

    }
}