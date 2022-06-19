package com.apache.fastandroid.jetpack.flow.practice

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FlowFragmentRoomBinding
import com.apache.fastandroid.jetpack.flow.adapter.UserAdapter
import com.apache.fastandroid.jetpack.flow.vm.FlowUserViewModel
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/6/19.
 */
class FlowUserInfoFragment:BaseVBFragment<FlowFragmentRoomBinding>(FlowFragmentRoomBinding::inflate) {

    private val mViewModel by viewModels<FlowUserViewModel>()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.button.setOnClickListener {

            val id = mBinding.editText1.text?.toString()
            if (id.isNullOrEmpty()){
                showToast("id is null")
                return@setOnClickListener
            }
            id.toInt()?.let { editText1 ->
                mViewModel.insert(
                    editText1,
                    mBinding.editTextText2.text.toString(),
                )
            }
        }

        val myAdapter = UserAdapter()
        mBinding.recycleView.apply {
            setHasFixedSize(true)
            adapter = myAdapter
        }
        lifecycleScope.launchWhenCreated {
            mViewModel.getAll().collect{ users ->
                myAdapter.setNewInstance(users.toMutableList())
            }
        }
    }
}