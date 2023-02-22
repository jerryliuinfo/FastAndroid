package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentJetpackLivedataSeniorUsageBinding
import com.apache.fastandroid.databinding.FragmentLivedataLoaderBinding
import com.apache.fastandroid.jetpack.viewmodel.MessageHeadersViewModel
import com.tesla.framework.component.loader.observeLoading
import com.tesla.framework.kt.hide
import com.tesla.framework.kt.show
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataLoaderFragment : BaseBindingFragment<FragmentLivedataLoaderBinding>(FragmentLivedataLoaderBinding::inflate){


    private val mViewModel: MessageHeadersViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        liveDataLoaderUsages()

    }

    private fun liveDataLoaderUsages() {
        val view = mRootView
        val messageHeaderView = view.findViewById<TextView>(R.id.message_source)
        val errorView = view.findViewById<TextView>(R.id.message_headers_error)

        mViewModel.loadHeaders().observeLoading(
            owner = this,
            loadingView = view.findViewById(R.id.message_headers_loading),
            errorView = errorView,
            dataView = view.findViewById(R.id.message_headers_data),
            onSuccess = {
                messageHeaderView.show()
                errorView.hide()
                messageHeaderView.text = it[0]
            },
            onError = {
                messageHeaderView.hide()
                errorView.show()
                errorView.text = "load error"
            }

        )
    }

}