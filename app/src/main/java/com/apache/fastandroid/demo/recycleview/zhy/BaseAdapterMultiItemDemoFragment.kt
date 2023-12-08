package com.apache.fastandroid.demo.recycleview.zhy

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ChatMessage
import com.apache.fastandroid.demo.recycleview.zhy.MultiItemTypeAdapter.OnItemClickListener
import com.apache.fastandroid.demo.recycleview.zhy.adapter.ChatAdapterForRv
import com.apache.fastandroid.demo.recycleview.zhy.wrapper.LoadmoreWrapper
import com.tesla.framework.databinding.FragmentComRecycleviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/5/18.
 */
class BaseAdapterMultiItemDemoFragment:BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate) {


    private val mDatas: ArrayList<ChatMessage> = ArrayList<ChatMessage>()


    private var mLoadMoreWrapper: LoadmoreWrapper<ChatMessage>? = null


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        initDatas()
        var chatAdapter = ChatAdapterForRv(requireContext(), mDatas)
        mLoadMoreWrapper = LoadmoreWrapper(chatAdapter)
        mLoadMoreWrapper!!.setLoadMoreView(
            LayoutInflater.from(requireContext()).inflate(R.layout.base_adapter_default_loading, mBinding.recyclerView, false)
        )
        mLoadMoreWrapper!!.setOnLoadMoreListener {
            Handler().postDelayed({
                val coming = Math.random() > 0.5
                var msg: ChatMessage? = null
                msg = ChatMessage(
                    if (coming) R.drawable.renma else R.drawable.xiaohei,
                    if (coming) "人马" else "xiaohei",
                    "where are you " + mDatas.size,
                    null,
                    coming
                )
                mDatas.add(msg)
                mLoadMoreWrapper!!.notifyDataSetChanged()
            }, 3000)
        }
        chatAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                Toast.makeText(requireContext(), "Click:$position", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onItemLongClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int
            ): Boolean {
                Toast.makeText(requireContext(), "LongClick:$position", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        })


        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mLoadMoreWrapper
        }

    }

    private fun initDatas() {
        mDatas.addAll(ChatMessage.MOCK_DATAS)

    }
}