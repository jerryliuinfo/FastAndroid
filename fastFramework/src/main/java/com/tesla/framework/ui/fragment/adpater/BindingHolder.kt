package com.tesla.framework.ui.fragment.adpater

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BindingHolder<VH : ViewBinding>(
    val binding: VH
) : RecyclerView.ViewHolder(binding.root)
