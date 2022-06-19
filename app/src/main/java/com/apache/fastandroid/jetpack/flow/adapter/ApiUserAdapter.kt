package com.apache.fastandroid.jetpack.flow.adapter

import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ItemLayoutBinding
import com.apache.fastandroid.databinding.ItemLayoutUserBinding
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.network.model.ApiUser
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * Created by Jerry on 2022/5/12.
 */
class ApiUserAdapter:BaseQuickAdapter<ApiUser,BaseDataBindingHolder<ItemLayoutUserBinding>>(R.layout.item_layout_user) {
    override fun convert(holder: BaseDataBindingHolder<ItemLayoutUserBinding>, item: ApiUser) {
        holder.dataBinding?.apply {
            user = User(item.id,item.name,item.email,item.avatar)
        }
    }
}