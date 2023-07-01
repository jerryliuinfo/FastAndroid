package com.tesla.framework.component.viewbinding

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BindingHolder<T : ViewBinding>(
    val binding: T
) : RecyclerView.ViewHolder(binding.root)
