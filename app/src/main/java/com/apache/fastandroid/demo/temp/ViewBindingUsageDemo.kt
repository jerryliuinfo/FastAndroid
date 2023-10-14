package com.apache.fastandroid.demo.temp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentViewbindingUsageBinding
import com.apache.fastandroid.databinding.ItemCommentBinding
import com.apache.fastandroid.demo.viewbinding.ViewBindingDemoActivity
import com.apache.fastandroid.demo.viewbinding.ViewBindingDemoFragment
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.kt.addOnItemClickListener
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.kt.launchFragment
import com.tesla.framework.ui.fragment.BaseBindingFragment
import com.tesla.framework.ui.fragment.adpater.BindingHolder
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Created by Jerry on 2022/6/4.
 */
class ViewBindingUsageDemo:BaseBindingFragment<FragmentViewbindingUsageBinding>(FragmentViewbindingUsageBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val users = IntArray(10){
            it
        }.map {
            "user:$it"
        }


        mBinding.btnViewBinding.setOnClickListener {
            mBinding.recyclerView.apply {
//                adapter = UserAdapter(users)
                adapter = UserAdapter2(users)
            }
        }
        mBinding.btnViewBindingActivity.setOnClickListener {
            launchActivity<ViewBindingDemoActivity>(requireContext())
        }
        mBinding.btnViewBindingFragment.setOnClickListener {
            requireActivity().launchFragment<ViewBindingDemoFragment>()
        }

        mBinding.recyclerView.addOnItemClickListener { view, position ->
            ToastUtils.showShort("onclick item:$position")
        }


    }


    private class UserAdapter(val users:List<String>): RecyclerView.Adapter<UserAdapter.ItemViewHolder>() {


        inner class ItemViewHolder(binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
            val tvTitle = binding.tvTitle
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
            return ItemViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val data = users[position]
            holder.tvTitle.text = data

        }

        override fun getItemCount(): Int {
            return users.size
        }
    }

    private class UserAdapter2(val users:List<String>): RecyclerView.Adapter<BindingHolder<ItemCommentBinding>>() {

        override fun onBindViewHolder(holder: BindingHolder<ItemCommentBinding>, position: Int) {
            holder.itemView.tv_title.text = users[position]
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BindingHolder<ItemCommentBinding> {
            return BindingHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }


        override fun getItemCount(): Int {
            return users.size
        }




    }
}