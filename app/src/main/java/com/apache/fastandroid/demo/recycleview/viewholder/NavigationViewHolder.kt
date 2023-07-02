/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apache.fastandroid.demo.recycleview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.NavDividerItemLayoutBinding
import com.apache.fastandroid.databinding.NavEmailFolderItemLayoutBinding
import com.apache.fastandroid.databinding.NavMenuItemLayoutBinding
import com.apache.fastandroid.demo.recycleview.viewtype.NavigationAdapter
import com.apache.fastandroid.demo.recycleview.viewtype.NavigationModelItem

sealed class NavigationViewHolder<T : NavigationModelItem>(
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(navItem : T)

    class NavMenuItemViewHolder(
        private val binding: NavMenuItemLayoutBinding,
        private val listener: NavigationAdapter.NavigationAdapterListener
    ) : NavigationViewHolder<NavigationModelItem.NavMenuItem>(binding.root) {


        companion object{
            fun from(parent:ViewGroup,listener: NavigationAdapter.NavigationAdapterListener): NavMenuItemViewHolder {
                return NavMenuItemViewHolder(
                    NavMenuItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),listener)
            }
        }


        override fun bind(navItem: NavigationModelItem.NavMenuItem) {
            binding.run {
                navMenuItem = navItem
                navListener = listener

                executePendingBindings()
            }
        }
    }

    class NavDividerViewHolder(
        private val binding: NavDividerItemLayoutBinding,


    ) : NavigationViewHolder<NavigationModelItem.NavDivider>(binding.root) {

        companion object{
            fun from(parent:ViewGroup,listener: NavigationAdapter.NavigationAdapterListener): NavDividerViewHolder {
                return NavDividerViewHolder(
                    NavDividerItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ))
            }
        }


        override fun bind(navItem: NavigationModelItem.NavDivider) {
            binding.navDivider = navItem
            binding.executePendingBindings()
        }
    }

    class EmailFolderViewHolder(
        private val binding: NavEmailFolderItemLayoutBinding,
        private val listener: NavigationAdapter.NavigationAdapterListener
    ) : NavigationViewHolder<NavigationModelItem.NavEmailFolder>(binding.root) {


        companion object{
            fun from(parent: ViewGroup,listener: NavigationAdapter.NavigationAdapterListener): EmailFolderViewHolder {
                return EmailFolderViewHolder(
                    NavEmailFolderItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    listener
                )
            }
        }

        override fun bind(navItem: NavigationModelItem.NavEmailFolder) {
            binding.run {
                navEmailFolder = navItem
                navListener = listener
                executePendingBindings()
            }
        }
    }
}