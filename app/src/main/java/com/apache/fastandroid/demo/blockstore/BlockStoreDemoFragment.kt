package com.apache.fastandroid.demo.blockstore

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentBlockStoreBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/8/5.
 */
class BlockStoreDemoFragment:BaseBindingFragment<FragmentBlockStoreBinding>(FragmentBlockStoreBinding::inflate) {


    private val viewModel: BlockStoreViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        lifecycleScope.launch {
            viewModel.state
                .collect { state ->
                    mBinding.buttonSignOut.isVisible = state.areBytesStored
                    mBinding.buttonSignIn.isVisible = !state.areBytesStored
                    mBinding.nameTextView.text = state.bytes ?: ""
                }
        }

        mBinding.buttonSignIn.setOnClickListener {
            viewModel.storeBytes(mBinding.editTextView.text.toString())
        }

        mBinding.buttonSignOut.setOnClickListener {
            viewModel.clearBytes()
        }
    }

}