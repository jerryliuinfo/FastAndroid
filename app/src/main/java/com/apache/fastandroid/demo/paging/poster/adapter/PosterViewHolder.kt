/*
 * Copyright 2022 The Android Open Source Project
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

package com.apache.fastandroid.demo.paging.poster.adapter

import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.PosterListItemBinding
import com.apache.fastandroid.demo.paging.article.data.Article
import com.apache.fastandroid.network.model.ArticleApi

/**
 * View Holder for a [Article] RecyclerView list item.
 */
class PosterViewHolder(
    private val binding: PosterListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ArticleApi) {
        binding.apply {
            title.text = item.title
            description.text = item.author
        }
    }
}
