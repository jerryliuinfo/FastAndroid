package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlin.random.Random

/**
 * Created by Jerry on 2023/4/21.
 */
class AutoScrollViewDemoFragment :
    BaseBindingFragment<com.apache.fastandroid.databinding.FragmentRecycleviewAutoScrollBinding>(com.apache.fastandroid.databinding.FragmentRecycleviewAutoScrollBinding::inflate) {


    private lateinit var mAdapter: BaseQuickAdapter<String,BaseViewHolder>
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MyAdapter().also {
                mAdapter = it
            }

        }
        mBinding.btnScroll.setOnClickListener {
            mBinding.recyclerView.autoScroll(Random.nextInt(15,30))
        }
        refreshData()
    }

    private fun refreshData(){
        val array = Array(50){
            "Hello world:${it}"
        }
        mAdapter.setNewInstance(array.toMutableList())
        mBinding.recyclerView.autoScroll(20)

    }

    private inner class MyAdapter :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_auto_scrollview) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tv_title, item)

            holder.getView<TextView>(R.id.tv_title).apply {
                setOnFocusChangeListener { v, hasFocus ->
                    println("onFocuse change item:${item}, hasFocus:${hasFocus}")
                }
                setOnClickListener {
                    showToast(item)
                }

            }

        }

    }


    fun RecyclerView.autoScroll(position:Int){
        postDelayed({

            smoothScrollToPosition(position)
            val layoutManager = layoutManager as LinearLayoutManager
            val item: View? = layoutManager.findViewByPosition(position)?.findViewById(R.id.tv_title)
            if (item != null) {
                item.requestFocus()
                item.performClick()
            } else {
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        // 当滑动停止后
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            removeOnScrollListener(this)
                            // 获取第10个位置的条目视图
                            val item: View? = layoutManager.findViewByPosition(position)?.findViewById(R.id.tv_title)
                            item?.requestFocus()
                            item?.performClick()

                        }
                    }
                })
                // 如果第10个位置的条目视图为空，则滚动到第10个位置后再获取它
                smoothScrollToPosition(position)
            }
        },200)

    }


}