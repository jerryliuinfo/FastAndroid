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

package com.android.example.github.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.github.ui.common.RepoListAdapter
import com.android.example.github.ui.common.RetryCallback
import com.android.example.github.util.autoCleared
import com.android.example.github.vo.Repo
import com.apache.fastandroid.databinding.SearchFragmentBinding
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.tesla.framework.ui.fragment.BaseBindingFragment

class SearchFragment : BaseBindingFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {


    var adapter by autoCleared<RepoListAdapter>()

    val searchViewModel: SearchViewModel by viewModels {
        getViewModelFactory()
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        initRecyclerView()
        val rvAdapter = RepoListAdapter(
            showFullName = true
        ) { repo ->
            findNavController().navigate(
                SearchFragmentDirections.showRepo(repo.owner.login, repo.name)


            )
        }
        mBinding.apply {
            query = searchViewModel.query
            repoList.adapter = rvAdapter
            callback = object : RetryCallback {
                override fun retry() {
                    searchViewModel.refresh()
                }
            }
        }
        adapter = rvAdapter

        initSearchInputListener()

    }




    private fun initSearchInputListener() {
        mBinding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        mBinding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = mBinding.input.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        searchViewModel.setQuery(query)
    }

    private fun initRecyclerView() {

        mBinding.repoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    searchViewModel.loadNextPage()
                }
            }
        })
        mBinding.searchResult = searchViewModel.results
        searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            adapter.setNewInstance(result?.data as MutableList<Repo>?)
        })

        searchViewModel.loadMoreStatus.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null) {
                mBinding.loadingMore = false
            } else {
                mBinding.loadingMore = loadingMore.isRunning
                val error = loadingMore.errorMessageIfNotHandled
                if (error != null) {
                    Snackbar.make(mBinding.loadMoreBar, error, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
