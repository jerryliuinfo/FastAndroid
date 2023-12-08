package com.apache.fastandroid.demo.recycleview.partyrefresh

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.ItemCommentBinding
import com.tesla.framework.ui.fragment.adpater.DefaultRecyclerAdapter
import com.tesla.framework.ui.fragment.recycleview.BaseRecycleViewFragment
import kotlin.random.Random

/**
 * Created by Jerry on 2023/11/26.
 */
class PartyRefreshDemoFragment: BaseRecycleViewFragment<String>() {
    override fun getRecycleViewAdapter(): DefaultRecyclerAdapter<String> {
        return MyAdapter(getItems())
    }

    private fun getItems():MutableList<String>{
        return IntArray(10){
            it
        }.map { "item:$it" }.toMutableList()
    }

    private class MyAdapter(datas: MutableList<String>) :DefaultRecyclerAdapter<String>(datas){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(this,ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as MyViewHolder).bind(datas[position])
        }

    }

    private class MyViewHolder(private val adapter:DefaultRecyclerAdapter<String>,val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.tvTitle.setOnClickListener {
                adapter.datas[bindingAdapterPosition] = "item:" + Random.nextInt(10,100)
                // adapter.notifyItemChanged(bindingAdapterPosition)
                adapter.notifyDataSetChanged()
            }
        }
        fun bind(item:String){
            binding.tvTitle.text = item
        }
    }

}