package com.tesla.framework.ui.fragment.recycleview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Jerry on 2023/11/26.
 */
interface IItemViewCreator<VH:RecyclerView.ViewHolder> {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    fun onBindViewHolder(holder: VH, position: Int)

}