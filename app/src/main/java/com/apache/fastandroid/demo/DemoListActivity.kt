package com.apache.fastandroid.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.constraint.ConstraintLayoutDemoFragment
import com.apache.fastandroid.DrawBasicDemoFragment
import com.apache.fastandroid.demo.basic.AndroidBasicDemoFragment
import com.apache.fastandroid.demo.blacktech.BlackTechDemoFragment
import com.apache.fastandroid.demo.designmode.DesignModeDemoFragment
import com.apache.fastandroid.demo.drakeet.DrakeetDemoListFragment
import com.apache.fastandroid.demo.jetpack.JetPackDemoFragment
import com.apache.fastandroid.demo.performance.PerformanceDemoFragment
import com.apache.fastandroid.demo.temp.TempDemoFragment
import com.apache.fastandroid.demo.widget.WidgetDemoFragment
import com.apache.fastandroid.jetpack.relearnandroid.RelearnAndroidDemoFragment
import com.apache.fastandroid.jetpack.relearnandroid.vm.ShareViewModel
import com.hencoder.hencoderpracticedraw2.DrawPaintDemoFragment
import com.hencoder.hencoderpracticedraw3.DrawTextDemoFragment
import com.hencoder.hencoderpracticedraw4.MatrixDemoFragment
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.common.util.log.NLog
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
                ViewItemBean("Hencoder", "范围裁切", MatrixDemoFragment::class.java),


                ViewItemBean("CustomViewWidget", "自定义控件", CustomViewFragment::class.java),
                ViewItemBean("ConstraintLayout", "约束布局", ConstraintLayoutDemoFragment::class.java),
                ViewItemBean("性能优化", "性能优化", PerformanceDemoFragment::class.java),
                ViewItemBean("临时验证", "临时验证", TempDemoFragment::class.java),
                ViewItemBean("Android基础", "Android基础", AndroidBasicDemoFragment::class.java),
                ViewItemBean("开源UI控件", "开源UI控件", WidgetDemoFragment::class.java),
                ViewItemBean("开源框架", "开源框架", OpenSourceDemoFragment::class.java),
                ViewItemBean("设计模式", "设计模式实战", DesignModeDemoFragment::class.java),
                ViewItemBean("重学Android", "重学Android", RelearnAndroidDemoFragment::class.java),
                ViewItemBean("黑科技", "黑科技", BlackTechDemoFragment::class.java),
                ViewItemBean("Drakeet", "Drakeet", DrakeetDemoListFragment::class.java)


        )
        @JvmStatic
        fun launch(from:Activity){
            from.startActivity(Intent(from,DemoListActivity::class.java))
        }
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
        getApplicationScopeViewModel(ShareViewModel::class.java).name1.observe(this,object : Observer<String>{
            override fun onChanged(t: String?) {
                NLog.d(TAG, "name1 onChanged: %s",t)

            }

        })
        getApplicationScopeViewModel(ShareViewModel::class.java).name2.observe(this,object : Observer<String>{
            override fun onChanged(t: String?) {
               NLog.d(TAG, "name2 onChanged: %s",t)

            }

        })


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

        override fun onViewAttachedToWindow(holder: ItemViewHolder) {
            super.onViewAttachedToWindow(holder)
            NLog.d(TAG, "onViewAttachedToWindow holder: %s, tvTitle: %s, text: %s",holder,holder.txtTitle, holder.txtTitle.text)
            var tvTitle = holder.txtTitle
        }

        override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
            super.onViewDetachedFromWindow(holder)
            NLog.d(TAG, "onViewDetachedFromWindow holder: %s, tvTitle: %s, text: %s",holder,holder.txtTitle, holder.txtTitle.text)
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