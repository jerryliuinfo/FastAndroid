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

package com.apache.fastandroid.demo.paging.article.ui

import ArticleViewModelFactory
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.ActivityArticlesBinding
import com.apache.fastandroid.demo.paging.article.adapter.ArticleAdapter
import com.apache.fastandroid.demo.paging.article.data.ArticleRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingArticleDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityArticlesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Get the view model
        val viewModel by viewModels<ArticleViewModel>(
            factoryProducer = { ArticleViewModelFactory(this, ArticleRepository()) }
        )
        val items = viewModel.items
        val articleAdapter = ArticleAdapter()

        binding.bindAdapter(articleAdapter = articleAdapter)

        lifecycleScope.launch {
            items.collectLatest {
                articleAdapter.submitData(it)
            }
        }

        // Use the CombinedLoadStates provided by the loadStateFlow on the ArticleAdapter to
        // show progress bars when more data is being fetched
        lifecycleScope.launch {
                articleAdapter.loadStateFlow.collectLatest {
                    binding.prependProgress.isVisible = it.source.prepend is Loading
                    binding.appendProgress.isVisible = it.source.append is Loading
                }
        }
    }
}

/**
 * Sets up the [RecyclerView] and binds [ArticleAdapter] to it
 */
private fun ActivityArticlesBinding.bindAdapter(articleAdapter: ArticleAdapter) {
    list.adapter = articleAdapter
    list.layoutManager = LinearLayoutManager(list.context)
    val decoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
    list.addItemDecoration(decoration)
}
