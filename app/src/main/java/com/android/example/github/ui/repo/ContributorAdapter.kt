/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github.ui.repo

import android.widget.ImageView
import com.android.example.github.AppExecutors
import com.android.example.github.vo.Contributor
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ContributorItemBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class ContributorAdapter(
    private val callback: ((Contributor, ImageView) -> Unit)?
) : BaseQuickAdapter<Contributor, BaseDataBindingHolder<ContributorItemBinding>>(R.layout.contributor_item){

    override fun convert(holder: BaseDataBindingHolder<ContributorItemBinding>, item: Contributor) {
        holder.dataBinding?.apply {
            contributor = item
            executePendingBindings()
        }
        holder.itemView.setOnClickListener {
            callback?.invoke(item, holder.dataBinding!!.imageView)
        }
    }

}
