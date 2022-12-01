package com.apache.fastandroid.jetpack.flow.task.twotasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.apache.fastandroid.databinding.FragmentCompletionBinding
import com.apache.fastandroid.network.model.Status
import com.blankj.utilcode.util.ToastUtils
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_completion.*

/**
 * Created by Jerry on 2022/5/4.
 */
class TwoLongRunningTaskFragment:BaseBindingFragment<FragmentCompletionBinding>(FragmentCompletionBinding::inflate) {

    private val viewModel:TwoLongRunningTasksViewModel by viewModels { getViewModelFactory() }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewModel.getStatus().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    textView.text = it.data
                    textView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    textView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    ToastUtils.showShort(it.message)
                }
            }
        })
    }
}