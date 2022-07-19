package com.apache.fastandroid.jetpack.flow.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.jetpack.flow.adapter.UserAdapter
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.network.model.Status
import com.example.android.architecture.blueprints.todoapp.util.getFlowViewModelFactory
import com.tesla.framework.ui.fragment.BaseVBFragment
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration

/**
 * Created by Jerry on 2022/5/12.
 */
class MapFragment:BaseVBFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {

    private val viewModel:MapViewModel by viewModels { getFlowViewModelFactory() }
    private lateinit var mAdapter: UserAdapter

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mAdapter = UserAdapter()
        mBinding.recyclerView.apply {
            adapter = mAdapter
            addItemDecoration( HorizontalDividerItemDecoration.Builder(context)
                .color(Color.RED)
                .sizeResId(R.dimen.divider_height)
                .marginResId(R.dimen.divider_margin)
                .build()
            )
        }


    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    mBinding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    mBinding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    mBinding.progressBar.visibility = View.GONE
                    toast(it.message)
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        mAdapter.apply {
            setNewInstance(users.toMutableList())
            notifyDataSetChanged()
        }
    }
}