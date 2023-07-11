package com.apache.fastandroid.demo.widget.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.ItemLayoutUser2Binding
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.bumptech.glide.Glide

/**
 * Created by Jerry on 2022/7/19.
 */

typealias onAlbumItemClickListener = (User) -> Unit

class AlbumListAdapter(private val listener: (User) -> Unit):ListAdapter<User,UserViewHolder>(User.diffCallback) {

    var onItemClickListener: onAlbumItemClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemLayoutUser2Binding.inflate(LayoutInflater.from(parent.context)),listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.item = user

        holder.apply {
            Glide.with(holder.itemView.context)
                .load(user.avatar)
                .into(imageViewAvatar)
            textViewUserName.text = user.name
            textViewUserEmail.text = user.email
        }

        holder.binding.imageViewAvatar.setOnClickListener {
            onItemClickListener?.invoke(user)
        }


    }
}

class UserViewHolder(val binding:ItemLayoutUser2Binding, listener: (User) -> Unit):RecyclerView.ViewHolder(binding.root){

    val imageViewAvatar = binding.imageViewAvatar
    val textViewUserName = binding.textViewUserName
    val textViewUserEmail = binding.textViewUserEmail

    var item:User ?= null
    init {
        binding.root.setOnClickListener {
            item?.let {
                listener(it)


            }
        }
    }
}
