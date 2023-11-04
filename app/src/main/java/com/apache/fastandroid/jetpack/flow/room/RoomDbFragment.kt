package com.apache.fastandroid.jetpack.flow.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.databinding.ItemLayoutUserBinding
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.network.model.Status
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.component.divider.SpaceItemDecoration
import com.tesla.framework.kt.dp
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Created by Jerry on 2022/5/3.
 * https://blog.mindorks.com/terminal-operators-in-kotlin-flow
 */
class RoomDbFragment:BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate){

    private val mViewModel: RoomDbViewModel by viewModels{getViewModelFactory()}

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val userAdapter = object: BaseQuickAdapter<User, BaseDataBindingHolder<ItemLayoutUserBinding>>(
            R.layout.item_layout_user){
            override fun convert(holder: BaseDataBindingHolder<ItemLayoutUserBinding>, item: User) {
                holder.dataBinding?.apply {
                    user = item
                    executePendingBindings()
                }
            }
        }
        mBinding.recyclerView.apply {

            adapter = userAdapter
            addItemDecoration(
                SpaceItemDecoration(
                    10.dp
                )
            )
        }

        mViewModel.users.observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.recyclerView.visibility = View.VISIBLE
                    userAdapter.setNewInstance(it.data?.toMutableList())

                }
                Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    mBinding.progressBar.visibility = View.GONE
                    ToastUtils.showShort(it.message)
                }
            }
        }
    }


}