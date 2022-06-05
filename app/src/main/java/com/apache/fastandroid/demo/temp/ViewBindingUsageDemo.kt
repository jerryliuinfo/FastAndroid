package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentViewbindingUsageBinding
import com.apache.fastandroid.databinding.ItemCommentBinding
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.kt.addOnItemClickListener
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Created by Jerry on 2022/6/4.
 */
class ViewBindingUsageDemo:BaseVBFragment<FragmentViewbindingUsageBinding>(FragmentViewbindingUsageBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val users = IntArray(10){
            it
        }.map {
            "user:$it"
        }

        //存在性能问题
        mBinding.btnKotlinAndroidExtensions.setOnClickListener {
            mBinding.recyclerView.apply {
                adapter = UserAdapter2(users)
            }
        }
        mBinding.btnViewBinding.setOnClickListener {
            mBinding.recyclerView.apply {
                adapter = UserAdapter(users)
            }
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

    private class UserAdapter2(val users:List<String>): RecyclerView.Adapter<UserAdapter2.ItemViewHolder>() {
        inner class ItemViewHolder(view:View):RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): UserAdapter2.ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            //反编译后变成, 每次刷新的时候都执行了 findViewById， 影响了性能
            /**
             *
             *  public void onBindViewHolder(@NotNull ViewBindingUsageDemo.UserAdapter2.ItemViewHolder holder, int position) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            View var10000 = holder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "holder.itemView");
            TextView var3 = (TextView)var10000.findViewById(id.tv_title);
            Intrinsics.checkNotNullExpressionValue(var3, "holder.itemView.tv_title");
            var3.setText((CharSequence)this.users.get(position));
            }
             *
             *
             *
             */

            holder.itemView.tv_title.text = users[position]
        }

        override fun getItemCount(): Int {
            return users.size
        }




    }
}