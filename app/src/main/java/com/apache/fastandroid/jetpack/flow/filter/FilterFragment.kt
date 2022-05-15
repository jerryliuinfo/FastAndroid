package com.apache.fastandroid.jetpack.flow.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.apache.fastandroid.databinding.FragmentCompletionBinding
import com.apache.fastandroid.network.model.Status
import com.example.android.architecture.blueprints.todoapp.util.getFlowViewModelFactory
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/5/12.
 */
class FilterFragment: BaseVBFragment<FragmentCompletionBinding>(FragmentCompletionBinding::inflate) {

    private val viewModel: FilterViewModel by viewModels{getFlowViewModelFactory()}
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        setupLongRunningTask()
    }

    private fun setupLongRunningTask() {
        viewModel.getStatus().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.textView.apply {
                        text = it.data
                        visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.textView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    mBinding.progressBar.visibility = View.GONE
                    showToast(it.message)
                }
            }
        })
        viewModel.startFilterTask()
    }
}