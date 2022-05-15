package com.apache.fastandroid.jetpack.flow.retry

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
class RetryFragment: BaseVBFragment<FragmentCompletionBinding>(FragmentCompletionBinding::inflate) {

    private val viewModel: RetryViewModel by viewModels{getFlowViewModelFactory()}
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
                        setOnClickListener(null)
                    }

                }
                Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.textView.visibility = View.GONE
                    mBinding.textView.setOnClickListener(null)
                }
                Status.ERROR -> {
                    //Handle Error
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.textView.apply {
                        text = it.message
                        visibility = View.VISIBLE
                        setOnClickListener {
                            viewModel.startTask()
                        }
                    }
                }
            }
        })
    }
}